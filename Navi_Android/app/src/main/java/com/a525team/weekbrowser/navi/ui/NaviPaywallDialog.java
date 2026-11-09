package com.a525team.weekbrowser.navi.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.a525team.weekbrowser.navi.core.NaviBillingManager;
import com.a525team.weekbrowser.navi.core.NaviDataStore;
import com.android.billingclient.api.ProductDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** Android layout matching Navi_iOS PaywallView, opened from automation settings. */
public final class NaviPaywallDialog {
    private static final int BG_TOP = Color.rgb(20, 20, 20);
    private static final int BG_BOTTOM = Color.rgb(35, 35, 35);
    private static final int CARD = Color.rgb(48, 48, 48);
    private static final int WHITE = Color.WHITE;
    private static final int SECONDARY = Color.rgb(185, 185, 185);
    private static final int BLUE = Color.rgb(55, 120, 245);
    private static final int PURPLE = Color.rgb(133, 70, 225);
    private static final int GREEN = Color.rgb(52, 199, 89);
    private static final int ORANGE = Color.rgb(255, 159, 10);
    private NaviPaywallDialog() {}

    public static void show(Context context) {
        if (!(context instanceof Activity)) return;
        Activity activity = (Activity) context;
        int d = (int) (context.getResources().getDisplayMetrics().density + .5f);
        int pad = 20 * d;
        NaviBillingManager billing = NaviBillingManager.get(context);
        NaviDataStore data = new NaviDataStore(context);
        String[] selectedProductId = {NaviBillingManager.ANNUAL_ID};

        Dialog dialog = new Dialog(context);
        LinearLayout screen = new LinearLayout(context);
        screen.setOrientation(LinearLayout.VERTICAL);
        screen.setBackground(gradient(BG_TOP, BG_BOTTOM, 0, 0, 0));

        FrameLayout topBar = new FrameLayout(context);
        topBar.setPadding(pad, 8 * d, pad, 4 * d);
        TextView close = text(context, "×", 30, SECONDARY, false);
        close.setGravity(Gravity.CENTER);
        close.setContentDescription("Fechar assinatura");
        FrameLayout.LayoutParams closeParams = new FrameLayout.LayoutParams(44 * d, 44 * d, Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        topBar.addView(close, closeParams);
        close.setOnClickListener(v -> dialog.dismiss());
        screen.addView(topBar, new LinearLayout.LayoutParams(-1, 52 * d));

        ScrollView scroll = new ScrollView(context);
        scroll.setFillViewport(false);
        LinearLayout content = new LinearLayout(context);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(pad, 8 * d, pad, 24 * d);
        scroll.addView(content);
        screen.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        LinearLayout header = new LinearLayout(context);
        header.setGravity(Gravity.CENTER_HORIZONTAL);
        header.setOrientation(LinearLayout.VERTICAL);
        header.setPadding(0, 8 * d, 0, 0);
        FrameLayout badge = new FrameLayout(context);
        badge.setBackground(gradient(data.isSubscribed() ? GREEN : BLUE, data.isSubscribed() ? BLUE : PURPLE, 36 * d, 0, 0));
        badge.setElevation(10 * d);
        TextView badgeIcon = text(context, data.isSubscribed() ? "✓" : "✦", 32, WHITE, true);
        badgeIcon.setGravity(Gravity.CENTER);
        badge.addView(badgeIcon, new FrameLayout.LayoutParams(-1, -1));
        header.addView(badge, new LinearLayout.LayoutParams(72 * d, 72 * d));
        TextView title = text(context, "Navi Premium", 28, WHITE, true);
        title.setPadding(0, 12 * d, 0, 0);
        header.addView(title);
        TextView subtitle = text(context, data.isSubscribed() ? "✓  Assinatura Ativa" : "Automatize sem interrupções", 14,
                data.isSubscribed() ? GREEN : SECONDARY, data.isSubscribed());
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(14 * d, 6 * d, 14 * d, 6 * d);
        if (data.isSubscribed()) subtitle.setBackground(rounded(Color.argb(30, 52, 199, 89), 24 * d, 0, 0));
        LinearLayout.LayoutParams subtitleLp = new LinearLayout.LayoutParams(-2, -2);
        subtitleLp.topMargin = 8 * d;
        header.addView(subtitle, subtitleLp);
        addSection(content, header, 24 * d);

        LinearLayout limitCard = makeCard(context, d, data.isSubscribed() ? GREEN : ORANGE);
        boolean subscribedAtOpen = data.isSubscribed();
        if (subscribedAtOpen) {
            addCardHeader(context, limitCard, "✦", "Sua Assinatura está Ativa", "Plano Premium", GREEN, d);
            addDivider(context, limitCard, d);
            addIconLine(context, limitCard, "⚡", "Processamentos e envios ilimitados liberados", BLUE, 13, d);
            addIconLine(context, limitCard, "⟳", "Renovação automática via Google Play", SECONDARY, 12, d);
        } else {
            boolean limitReached = data.getDailyCount() >= NaviDataStore.DAILY_LIMIT;
            addCardHeader(context, limitCard, "!", limitReached ? "Limite atingido para hoje" : "Tudo ok por hoje",
                    data.getDailyCount() + " de " + NaviDataStore.DAILY_LIMIT + " processamentos utilizados",
                    limitReached ? ORANGE : BLUE, d);
            addDivider(context, limitCard, d);
            addIconLine(context, limitCard, "◷", "O seu limite diário será zerado amanhã e permitirá novos processamentos, ou assine um plano abaixo para liberar agora mesmo.", BLUE, 13, d);
        }
        addSection(content, limitCard, 24 * d);
        LinearLayout plans = new LinearLayout(context);
        plans.setOrientation(LinearLayout.VERTICAL);
        addSection(content, plans, 0);

        LinearLayout messages = new LinearLayout(context);
        messages.setOrientation(LinearLayout.VERTICAL);
        addSection(content, messages, 12 * d);

        LinearLayout actions = new LinearLayout(context);
        actions.setOrientation(LinearLayout.VERTICAL);
        addSection(content, actions, 0);

        TextView footer = text(context, "", 11, Color.rgb(145, 145, 145), false);
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(0, 4 * d, 0, 0);
        addSection(content, footer, 4 * d);

        Runnable[] render = new Runnable[1];
        render[0] = () -> {
            boolean active = data.isSubscribed();
            List<ProductDetails> details = new ArrayList<>(billing.getProducts());
            Collections.sort(details, Comparator.comparing(p -> !NaviBillingManager.ANNUAL_ID.equals(p.getProductId())));
            plans.removeAllViews();
            String[] planIds = {NaviBillingManager.ANNUAL_ID, NaviBillingManager.MONTHLY_ID};
            for (String id : planIds) {
                ProductDetails product = null;
                for (ProductDetails candidate : details) if (id.equals(candidate.getProductId())) product = candidate;
                String productTitle = NaviBillingManager.ANNUAL_ID.equals(id) ? "Plano Anual" : "Plano Mensal";
                String price = product == null ? "Preço indisponível" : "Preço carregando";
                String period = NaviBillingManager.ANNUAL_ID.equals(id) ? "/ ano" : "/ mês";
                List<ProductDetails.SubscriptionOfferDetails> offers = product == null ? null : product.getSubscriptionOfferDetails();
                if (offers != null && !offers.isEmpty()) {
                    List<ProductDetails.PricingPhase> phases = offers.get(0).getPricingPhases().getPricingPhaseList();
                    if (!phases.isEmpty()) {
                        ProductDetails.PricingPhase phase = phases.get(phases.size() - 1);
                        price = phase.getFormattedPrice();
                        period = periodFromIso(phase.getBillingPeriod(), period);
                    }
                }
                boolean selected = id.equals(selectedProductId[0]);
                boolean current = active && id.equals(billing.getActiveProductId());
                View plan = planCard(context, productTitle, price, period, current ? "Plano Atual" :
                        (NaviBillingManager.ANNUAL_ID.equals(id) ? "16% off" : null), selected, current, d);
                if (product != null) plan.setOnClickListener(v -> { selectedProductId[0] = id; render[0].run(); });
                else plan.setAlpha(.65f);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
                lp.bottomMargin = 14 * d;
                plans.addView(plan, lp);
            }

            messages.removeAllViews();
            String message = billing.getMessage();
            if (message != null && !message.trim().isEmpty() && !message.contains("Conectando")
                    && !message.contains("Selecione um plano")) {
                int tint = message.toLowerCase().contains("falha") || message.toLowerCase().contains("não foi") ? Color.RED : GREEN;
                TextView msg = text(context, message, 13, tint, false);
                msg.setPadding(10 * d, 10 * d, 10 * d, 10 * d);
                msg.setBackground(rounded(Color.argb(22, Color.red(tint), Color.green(tint), Color.blue(tint)), 10 * d, 0, 0));
                messages.addView(msg);
            }

            actions.removeAllViews();
            if (active) {
                Button manage = actionButton(context, "▣  Gerenciar Assinatura", GREEN, BLUE, d);
                manage.setOnClickListener(v -> openSubscriptions(context));
                actions.addView(manage);
            } else {
                Button subscribe = actionButton(context, billing.isLoading() ? "Carregando…" : "Assinar", BLUE, PURPLE, d);
                subscribe.setEnabled(!billing.isLoading() && !details.isEmpty());
                subscribe.setAlpha(subscribe.isEnabled() ? 1f : .55f);
                subscribe.setOnClickListener(v -> {
                    for (ProductDetails product : billing.getProducts()) {
                        if (selectedProductId[0].equals(product.getProductId())) { billing.purchase(activity, product); break; }
                    }
                });
                actions.addView(subscribe);
            }
            Button restore = new Button(context);
            restore.setText("Restaurar Compras");
            restore.setAllCaps(false);
            restore.setTextSize(14);
            restore.setTextColor(SECONDARY);
            restore.setBackgroundColor(Color.TRANSPARENT);
            restore.setOnClickListener(v -> billing.restore());
            LinearLayout.LayoutParams restoreLp = new LinearLayout.LayoutParams(-1, 44 * d);
            restoreLp.topMargin = 12 * d;
            actions.addView(restore, restoreLp);

            footer.setText(active
                    ? "Assinatura gerenciada pela sua conta da Google Play. Você pode alterar ou cancelar a qualquer momento nas configurações do Google Play."
                    : "Renovado automaticamente. Cancele a qualquer momento nas configurações do Google Play.");
        };

        dialog.setContentView(screen);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams attrs = window.getAttributes();
            attrs.width = WindowManager.LayoutParams.MATCH_PARENT;
            attrs.height = WindowManager.LayoutParams.MATCH_PARENT;
            attrs.dimAmount = .45f;
            window.setAttributes(attrs);
        }
        dialog.setOnDismissListener(ignored -> billing.disconnectListener());
        dialog.show();
        if (dialog.getWindow() != null) dialog.getWindow().setLayout(-1, -1);
        render[0].run();
        billing.connect(() -> activity.runOnUiThread(render[0]));
    }

    private static View planCard(Context c, String title, String price, String period, String badge,
                                 boolean selected, boolean active, int d) {
        LinearLayout row = new LinearLayout(c);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(16 * d, 16 * d, 16 * d, 16 * d);
        int stroke = active ? GREEN : selected ? BLUE : Color.TRANSPARENT;
        int fill = active ? Color.argb(20, 52, 199, 89) : selected ? Color.argb(20, 55, 120, 245) : CARD;
        row.setBackground(rounded(fill, 16 * d, stroke, 2 * d));
        TextView radio = text(c, active || selected ? "●" : "○", 20, active ? GREEN : selected ? BLUE : SECONDARY, false);
        row.addView(radio);
        LinearLayout details = new LinearLayout(c);
        details.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams detailsLp = new LinearLayout.LayoutParams(0, -2, 1);
        detailsLp.leftMargin = 16 * d;
        row.addView(details, detailsLp);
        LinearLayout titleRow = new LinearLayout(c);
        titleRow.setGravity(Gravity.CENTER_VERTICAL);
        titleRow.addView(text(c, title, 16, WHITE, true));
        if (badge != null) {
            TextView tag = text(c, badge, 10, GREEN, true);
            tag.setPadding(8 * d, 3 * d, 8 * d, 3 * d);
            tag.setBackground(rounded(Color.argb(40, 52, 199, 89), 20 * d, 0, 0));
            LinearLayout.LayoutParams tagLp = new LinearLayout.LayoutParams(-2, -2);
            tagLp.leftMargin = 8 * d;
            titleRow.addView(tag, tagLp);
        }
        details.addView(titleRow);
        TextView benefit = text(c, "Processamentos ilimitados", 12, SECONDARY, false);
        LinearLayout.LayoutParams benefitLp = new LinearLayout.LayoutParams(-2, -2);
        benefitLp.topMargin = 4 * d;
        details.addView(benefit, benefitLp);
        LinearLayout priceBox = new LinearLayout(c);
        priceBox.setGravity(Gravity.RIGHT);
        priceBox.setOrientation(LinearLayout.VERTICAL);
        priceBox.addView(text(c, price, 18, WHITE, true));
        TextView periodText = text(c, period, 11, SECONDARY, false);
        periodText.setGravity(Gravity.RIGHT);
        priceBox.addView(periodText);
        row.addView(priceBox);
        return row;
    }

    private static LinearLayout makeCard(Context c, int d, int border) {
        LinearLayout card = new LinearLayout(c);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(16 * d, 14 * d, 16 * d, 14 * d);
        card.setBackground(rounded(CARD, 16 * d, Color.argb(110, Color.red(border), Color.green(border), Color.blue(border)), d));
        return card;
    }

    private static void addCardHeader(Context c, LinearLayout card, String icon, String title, String subtitle, int color, int d) {
        LinearLayout row = new LinearLayout(c);
        row.setGravity(Gravity.CENTER_VERTICAL);
        TextView iconView = text(c, icon, 20, color, true);
        iconView.setGravity(Gravity.CENTER);
        iconView.setBackground(rounded(Color.argb(35, Color.red(color), Color.green(color), Color.blue(color)), 24 * d, 0, 0));
        row.addView(iconView, new LinearLayout.LayoutParams(40 * d, 40 * d));
        LinearLayout copy = new LinearLayout(c);
        copy.setOrientation(LinearLayout.VERTICAL);
        copy.setPadding(12 * d, 0, 0, 0);
        copy.addView(text(c, title, 16, WHITE, true));
        TextView sub = text(c, subtitle, 12, SECONDARY, false);
        LinearLayout.LayoutParams subLp = new LinearLayout.LayoutParams(-2, -2); subLp.topMargin = 2 * d;
        copy.addView(sub, subLp);
        row.addView(copy);
        card.addView(row);
    }

    private static void addDivider(Context c, LinearLayout parent, int d) {
        View divider = new View(c); divider.setBackgroundColor(Color.argb(45, 255, 255, 255));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, d);
        lp.topMargin = 14 * d; lp.bottomMargin = 12 * d; parent.addView(divider, lp);
    }

    private static void addIconLine(Context c, LinearLayout parent, String icon, String message, int color, int size, int d) {
        LinearLayout row = new LinearLayout(c); row.setGravity(Gravity.TOP);
        TextView iv = text(c, icon, size + 1, color, true); row.addView(iv);
        TextView tv = text(c, message, size, size < 13 ? SECONDARY : WHITE, false);
        tv.setPadding(8 * d, 0, 0, 0); tv.setLineSpacing(2 * d, 1f);
        row.addView(tv, new LinearLayout.LayoutParams(0, -2, 1));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2); lp.topMargin = 5 * d; parent.addView(row, lp);
    }

    private static void addSection(LinearLayout parent, View child, int bottomMargin) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
        lp.bottomMargin = bottomMargin;
        parent.addView(child, lp);
    }

    private static Button actionButton(Context c, String label, int left, int right, int d) {
        Button button = new Button(c); button.setText(label); button.setTextColor(WHITE); button.setTextSize(16);
        button.setTypeface(null, Typeface.BOLD); button.setAllCaps(false);
        button.setBackground(gradient(left, right, 16 * d, 0, 0));
        button.setElevation(5 * d);
        button.setLayoutParams(new LinearLayout.LayoutParams(-1, 52 * d));
        return button;
    }

    private static TextView text(Context c, String value, int sp, int color, boolean bold) {
        TextView tv = new TextView(c); tv.setText(value); tv.setTextSize(sp); tv.setTextColor(color);
        if (bold) tv.setTypeface(null, Typeface.BOLD);
        return tv;
    }

    private static GradientDrawable rounded(int fill, int radius, int stroke, int strokeWidth) {
        GradientDrawable drawable = new GradientDrawable(); drawable.setColor(fill); drawable.setCornerRadius(radius);
        if (strokeWidth > 0) drawable.setStroke(strokeWidth, stroke);
        return drawable;
    }

    private static GradientDrawable gradient(int a, int b, int radius, int stroke, int strokeWidth) {
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{a, b});
        drawable.setCornerRadius(radius);
        if (strokeWidth > 0) drawable.setStroke(strokeWidth, stroke);
        return drawable;
    }

    private static String periodFromIso(String iso, String fallback) {
        if (iso == null) return fallback;
        if (iso.endsWith("P1M")) return "/ mês";
        if (iso.endsWith("P1Y")) return "/ ano";
        if (iso.endsWith("P1W")) return "/ semana";
        if (iso.endsWith("P1D")) return "/ dia";
        return fallback;
    }

    private static void openSubscriptions(Context context) {
        try { context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/account/subscriptions"))); }
        catch (Exception ignored) { }
    }
}
