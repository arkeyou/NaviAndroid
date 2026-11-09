package com.a525team.weekbrowser.navi.core;

import android.app.Activity;
import android.content.Context;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.PendingPurchasesParams;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Google Play subscription access. Local entitlement is refreshed from Play purchases. */
public final class NaviBillingManager implements PurchasesUpdatedListener {
    public static final String MONTHLY_ID = "navipay01";
    public static final String ANNUAL_ID = "navipay02";
    private static volatile NaviBillingManager instance;

    public interface Listener { void onBillingStateChanged(); }

    private final BillingClient client;
    private final NaviDataStore dataStore;
    private Listener listener;
    private List<ProductDetails> products = new ArrayList<>();
    private String activeProductId;
    private String message = "Conectando à Google Play…";
    private boolean loading;

    public static NaviBillingManager get(Context context) {
        if (instance == null) synchronized (NaviBillingManager.class) {
            if (instance == null) instance = new NaviBillingManager(context.getApplicationContext());
        }
        return instance;
    }

    private NaviBillingManager(Context context) {
        dataStore = new NaviDataStore(context);
        client = BillingClient.newBuilder(context).setListener(this)
                .enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build())
                .build();
    }

    public void connect(Listener listener) {
        if (listener != null) this.listener = listener;
        loading = true;
        notifyChanged();
        if (client.isReady()) { refresh(); return; }
        client.startConnection(new BillingClientStateListener() {
            @Override public void onBillingSetupFinished(BillingResult result) {
                if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) refresh();
                else fail("Google Play indisponível: " + result.getDebugMessage());
            }
            @Override public void onBillingServiceDisconnected() { fail("Conexão com Google Play perdida. Tente novamente."); }
        });
    }

    public void disconnectListener() { listener = null; }
    public List<ProductDetails> getProducts() { return Collections.unmodifiableList(products); }
    public String getMessage() { return message; }
    public boolean isLoading() { return loading; }
    public boolean isSubscribed() { return dataStore.isSubscribed(); }
    public String getActiveProductId() { return activeProductId; }

    public void refresh() {
        if (!client.isReady()) return;
        loading = true; notifyChanged();
        List<QueryProductDetailsParams.Product> ids = Arrays.asList(
                QueryProductDetailsParams.Product.newBuilder().setProductId(MONTHLY_ID).setProductType(BillingClient.ProductType.SUBS).build(),
                QueryProductDetailsParams.Product.newBuilder().setProductId(ANNUAL_ID).setProductType(BillingClient.ProductType.SUBS).build());
        client.queryProductDetailsAsync(QueryProductDetailsParams.newBuilder().setProductList(ids).build(), (result, response) -> {
            if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                products = new ArrayList<>(response.getProductDetailsList());
                message = products.isEmpty() ? "Os planos ainda não estão disponíveis na Google Play." : "Selecione um plano Navi Premium.";
            } else message = "Não foi possível carregar os planos: " + result.getDebugMessage();
            client.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build(), (purchaseResult, purchases) -> {
                if (purchaseResult.getResponseCode() == BillingClient.BillingResponseCode.OK) applyPurchases(purchases);
                else dataStore.setSubscribed(false);
                loading = false; notifyChanged();
            });
        });
    }

    public void purchase(Activity activity, ProductDetails product) {
        List<ProductDetails.SubscriptionOfferDetails> offers = product.getSubscriptionOfferDetails();
        if (offers == null || offers.isEmpty()) { message = "Não há uma oferta disponível para este plano."; notifyChanged(); return; }
        BillingFlowParams.ProductDetailsParams params = BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(product).setOfferToken(offers.get(0).getOfferToken()).build();
        BillingResult result = client.launchBillingFlow(activity, BillingFlowParams.newBuilder().setProductDetailsParamsList(Collections.singletonList(params)).build());
        if (result.getResponseCode() != BillingClient.BillingResponseCode.OK) { message = result.getDebugMessage(); notifyChanged(); }
    }

    public void restore() { refresh(); }

    @Override public void onPurchasesUpdated(BillingResult result, List<Purchase> purchases) {
        if (result.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            applyPurchases(purchases); message = dataStore.isSubscribed() ? "Assinatura ativa. Acesso ilimitado habilitado." : "Compra pendente de confirmação pela Google Play.";
        } else if (result.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) message = "Compra cancelada.";
        else message = "Falha na compra: " + result.getDebugMessage();
        notifyChanged();
    }

    private void applyPurchases(List<Purchase> purchases) {
        boolean active = false;
        activeProductId = null;
        for (Purchase purchase : purchases) {
            if (purchase.getPurchaseState() != Purchase.PurchaseState.PURCHASED) continue;
            if (!purchase.getProducts().contains(MONTHLY_ID) && !purchase.getProducts().contains(ANNUAL_ID)) continue;
            active = true;
            if (purchase.getProducts().contains(ANNUAL_ID)) activeProductId = ANNUAL_ID;
            else if (activeProductId == null) activeProductId = MONTHLY_ID;
            if (!purchase.isAcknowledged()) client.acknowledgePurchase(AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build(), result -> {
                if (result.getResponseCode() != BillingClient.BillingResponseCode.OK) { message = "Compra válida; confirmação da Google Play será repetida."; notifyChanged(); }
            });
        }
        dataStore.setSubscribed(active);
    }

    private void fail(String text) { loading = false; message = text; notifyChanged(); }
    private void notifyChanged() { if (listener != null) listener.onBillingStateChanged(); }
}
