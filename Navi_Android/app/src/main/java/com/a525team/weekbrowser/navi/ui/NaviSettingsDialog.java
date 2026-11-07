package com.a525team.weekbrowser.navi.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.Gravity;
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

    public static void show(Context context, NaviDataStore dataStore, OnSettingsSavedListener listener) {
        int p = (int) (14 * context.getResources().getDisplayMetrics().density);

        ScrollView scrollView = new ScrollView(context);
        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(p, p / 2, p, p);
        root.setBackgroundColor(Color.parseColor("#1E1E1E"));

        TextView header = new TextView(context);
        header.setText("Intervalos de Automação (segundos)");
        header.setTextSize(15);
        header.setTypeface(null, Typeface.BOLD);
        header.setTextColor(Color.parseColor("#4CAF50"));
        header.setPadding(0, 0, 0, p / 2);
        root.addView(header);

        EditText edtMonitor = createSettingRow(context, root,
                "Monitora URL (MONITOR)",
                "Tempo entre leituras do endereço informado na automação.",
                String.valueOf(dataStore.getMonitorInterval()));

        EditText edtIdsWait = createSettingRow(context, root,
                "Processa Códigos Validados (FLOW)",
                "Espera antes de processar novos códigos validados na etapa MONITOR.",
                String.valueOf(dataStore.getIdsWaitInterval()));

        EditText edtLikeWait = createSettingRow(context, root,
                "Processa Tela (ACTION)",
                "Intervalo de espera entre tentativas de conferir mudanças na tela e executar ações.",
                String.valueOf(dataStore.getLikeWaitInterval()));

        EditText edtPageWait = createSettingRow(context, root,
                "Espera Carregamento Página",
                "Tempo máximo de espera para os elementos dinâmicos da página carregarem.",
                String.valueOf(dataStore.getPageWaitInterval()));

        EditText edtOnline = createSettingRow(context, root,
                "Verifica Online",
                "Tempo entre verificações para confirmar se a página monitorada está ativa.",
                String.valueOf(dataStore.getMonitorLiveOnlineInterval()));

        scrollView.addView(root);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Configurações NAVI")
                .setView(scrollView)
                .setPositiveButton("Salvar", (d, which) -> {
                    try {
                        dataStore.setMonitorInterval(Double.parseDouble(edtMonitor.getText().toString().trim()));
                        dataStore.setIdsWaitInterval(Double.parseDouble(edtIdsWait.getText().toString().trim()));
                        dataStore.setLikeWaitInterval(Double.parseDouble(edtLikeWait.getText().toString().trim()));
                        dataStore.setPageWaitInterval(Double.parseDouble(edtPageWait.getText().toString().trim()));
                        dataStore.setMonitorLiveOnlineInterval(Double.parseDouble(edtOnline.getText().toString().trim()));

                        Toast.makeText(context, "Intervalos salvos com sucesso!", Toast.LENGTH_SHORT).show();
                        if (listener != null) {
                            listener.onSettingsSaved();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Valores inválidos informados.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();

        dialog.show();
    }

    private static EditText createSettingRow(Context context, LinearLayout parent, String title, String subtitle, String initialValue) {
        int p = (int) (8 * context.getResources().getDisplayMetrics().density);

        LinearLayout row = new LinearLayout(context);
        row.setOrientation(LinearLayout.VERTICAL);
        row.setPadding(0, p / 2, 0, p);

        TextView tvTitle = new TextView(context);
        tvTitle.setText(title);
        tvTitle.setTextSize(13);
        tvTitle.setTypeface(null, Typeface.BOLD);
        tvTitle.setTextColor(Color.parseColor("#FFFFFF"));
        row.addView(tvTitle);

        TextView tvSub = new TextView(context);
        tvSub.setText(subtitle);
        tvSub.setTextSize(11);
        tvSub.setTextColor(Color.parseColor("#9E9E9E"));
        tvSub.setPadding(0, 2, 0, 4);
        row.addView(tvSub);

        EditText edt = new EditText(context);
        edt.setText(initialValue);
        edt.setTextSize(13);
        edt.setTextColor(Color.parseColor("#2196F3"));
        edt.setBackgroundColor(Color.parseColor("#2A2A2A"));
        edt.setPadding(p, p / 2, p, p / 2);
        edt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        row.addView(edt);

        parent.addView(row);
        return edt;
    }
}
