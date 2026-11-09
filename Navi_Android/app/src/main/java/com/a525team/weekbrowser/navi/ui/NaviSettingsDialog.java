package com.a525team.weekbrowser.navi.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.a525team.weekbrowser.navi.core.NaviDataStore;

public class NaviSettingsDialog {

    public interface OnSettingsSavedListener {
        void onSettingsSaved();
    }

    private static final int BACKGROUND = Color.rgb(30, 30, 30);
    private static final int CARD = Color.rgb(44, 44, 46);
    private static final int PRIMARY = Color.WHITE;
    private static final int SECONDARY = Color.rgb(154, 154, 160);

    public static void show(Context context, NaviDataStore dataStore, OnSettingsSavedListener listener) {
        int dp = (int) context.getResources().getDisplayMetrics().density;
        int horizontal = 24 * dp;

        ScrollView scrollView = new ScrollView(context);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(BACKGROUND);

        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(horizontal, 8 * dp, horizontal, 16 * dp);

        TextView subscriptionHeader = sectionHeader(context, "Assinatura & IAP", dp);
        root.addView(subscriptionHeader, marginParams(-1, -2, 0, 0, 0, 16 * dp));

        LinearLayout premium = new LinearLayout(context);
        premium.setGravity(Gravity.CENTER_VERTICAL);
        premium.setPadding(20 * dp, 16 * dp, 20 * dp, 16 * dp);
        premium.setBackground(roundedBackground(CARD, 32 * dp));
        TextView sparkles = new TextView(context);
        sparkles.setText("✦");
        sparkles.setTextSize(30);
        sparkles.setTextColor(Color.rgb(210, 55, 235));
        premium.addView(sparkles, marginParams(48 * dp, -2, 0, 0, 20 * dp, 0));

        LinearLayout premiumLabels = new LinearLayout(context);
        premiumLabels.setOrientation(LinearLayout.VERTICAL);
        TextView premiumTitle = bodyText(context, "Assinatura Navi Premium", 17, PRIMARY);
        TextView premiumAction = bodyText(context,
                dataStore.isSubscribed() ? "Assinatura ativa  ›" : "Ver planos   ›", 15, SECONDARY);
        premiumAction.setPadding(0, 2 * dp, 0, 0);
        premiumLabels.addView(premiumTitle);
        premiumLabels.addView(premiumAction);
        premium.addView(premiumLabels, new LinearLayout.LayoutParams(0, -2, 1));
        premium.setOnClickListener(v -> NaviPaywallDialog.show(context));
        root.addView(premium, marginParams(-1, -2, 0, 0, 0, 44 * dp));

        root.addView(sectionHeader(context, "Intervalos de Automação (segundos)", dp),
                marginParams(-1, -2, 0, 0, 0, 14 * dp));

        LinearLayout intervalCard = new LinearLayout(context);
        intervalCard.setOrientation(LinearLayout.VERTICAL);
        intervalCard.setPadding(20 * dp, 0, 20 * dp, 0);
        intervalCard.setBackground(roundedBackground(CARD, 32 * dp));

        EditText edtOnline = createSettingRow(context, intervalCard,
                "Verifica Online",
                "Tempo entre verificações para confirmar se a pagina monitorada está ativa.",
                String.valueOf(dataStore.getMonitorLiveOnlineInterval()), dp);
        EditText edtMonitor = createSettingRow(context, intervalCard,
                "Monitora URL (MONITOR)",
                "Tempo entre leituras do endereço informado durante a automação.",
                String.valueOf(dataStore.getMonitorInterval()), dp);
        EditText edtIdsWait = createSettingRow(context, intervalCard,
                "Processa Codigos Validados (FLOW)",
                "Espera antes de processar novos códigos que já foram validados na etapa MONITOR.",
                String.valueOf(dataStore.getIdsWaitInterval()), dp);
        EditText edtLikeWait = createSettingRow(context, intervalCard,
                "Processa Tela (ACTION)",
                "Intervalo de espera antes de conferir mudanças na tela e executar as ações.",
                String.valueOf(dataStore.getLikeWaitInterval()), dp);
        EditText edtPageWait = createSettingRow(context, intervalCard,
                "Espera Carregamento Página",
                "Tempo máximo de espera para a pagina carregar.",
                String.valueOf(dataStore.getPageWaitInterval()), dp);
        root.addView(intervalCard, marginParams(-1, -2, 0, 0, 0, 0));
        scrollView.addView(root);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(scrollView)
                .setPositiveButton("Salvar", (d, which) -> {
                    try {
                        dataStore.setMonitorLiveOnlineInterval(parse(edtOnline));
                        dataStore.setMonitorInterval(parse(edtMonitor));
                        dataStore.setIdsWaitInterval(parse(edtIdsWait));
                        dataStore.setLikeWaitInterval(parse(edtLikeWait));
                        dataStore.setPageWaitInterval(parse(edtPageWait));
                        Toast.makeText(context, "Intervalos salvos com sucesso!", Toast.LENGTH_SHORT).show();
                        if (listener != null) listener.onSettingsSaved();
                    } catch (Exception e) {
                        Toast.makeText(context, "Valores inválidos informados.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(BACKGROUND));
            dialog.getWindow().setLayout((int) (context.getResources().getDisplayMetrics().widthPixels * 0.94), -2);
        }
    }

    private static double parse(EditText field) {
        return Double.parseDouble(field.getText().toString().trim());
    }

    private static TextView sectionHeader(Context context, String title, int dp) {
        TextView header = bodyText(context, title, 15, SECONDARY);
        header.setTypeface(null, Typeface.BOLD);
        return header;
    }

    private static EditText createSettingRow(Context context, LinearLayout parent, String title,
                                              String subtitle, String initialValue, int dp) {
        LinearLayout row = new LinearLayout(context);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(0, 18 * dp, 0, 18 * dp);

        LinearLayout labels = new LinearLayout(context);
        labels.setOrientation(LinearLayout.VERTICAL);
        TextView tvTitle = bodyText(context, title, 16, PRIMARY);
        TextView tvSub = bodyText(context, subtitle, 12, SECONDARY);
        tvSub.setPadding(0, 3 * dp, 8 * dp, 0);
        labels.addView(tvTitle);
        labels.addView(tvSub);
        row.addView(labels, new LinearLayout.LayoutParams(0, -2, 1));

        EditText value = new EditText(context);
        value.setText(initialValue);
        value.setTextSize(16);
        value.setTextColor(PRIMARY);
        value.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        value.setSingleLine(true);
        value.setSelectAllOnFocus(true);
        value.setBackgroundColor(Color.TRANSPARENT);
        value.setPadding(4 * dp, 0, 0, 0);
        value.setMinWidth(52 * dp);
        value.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        row.addView(value, new LinearLayout.LayoutParams(-2, -2));

        parent.addView(row, new LinearLayout.LayoutParams(-1, -2));
        if (parent.getChildCount() > 1) {
            View divider = new View(context);
            divider.setBackgroundColor(Color.rgb(68, 68, 70));
            parent.addView(divider, new LinearLayout.LayoutParams(-1, Math.max(1, dp)));
        }
        return value;
    }

    private static TextView bodyText(Context context, String text, int size, int color) {
        TextView view = new TextView(context);
        view.setText(text);
        view.setTextSize(size);
        view.setTextColor(color);
        return view;
    }

    private static GradientDrawable roundedBackground(int color, int radius) {
        GradientDrawable background = new GradientDrawable();
        background.setColor(color);
        background.setCornerRadius(radius);
        return background;
    }

    private static LinearLayout.LayoutParams marginParams(int width, int height,
                                                           int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.setMargins(left, top, right, bottom);
        return params;
    }
}
