package com.a525team.weekbrowser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.a525team.weekbrowser.navi.browser.NaviBrowserSession;
import com.a525team.weekbrowser.navi.browser.WeekBrowserWebViewAdapter;
import com.a525team.weekbrowser.navi.core.AutomationManager;
import com.a525team.weekbrowser.navi.core.NaviDataStore;
import com.a525team.weekbrowser.navi.model.ActionEvent;

import java.util.LinkedList;
import java.util.Queue;

public class NaviActivity extends Activity {
    private static final String EXTRA_TAB_TAG = "navi.tab_tag";

    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private NaviBrowserSession browserSession;
    private NaviDataStore dataStore;
    private AutomationManager automationManager;

    private TextView statusView;
    private EditText configEditor;
    private TextView logView;
    private ScrollView logScrollView;
    private Button startStopButton;
    private Button clearLogButton;

    private final Queue<ActionEvent.EnqueuePage> pageQueue = new LinkedList<>();
    private boolean isProcessingPage = false;

    public static Intent createIntent(Context context, String tabTag) {
        return new Intent(context, NaviActivity.class).putExtra(EXTRA_TAB_TAG, tabTag);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataStore = new NaviDataStore(this);
        automationManager = new AutomationManager();

        int padding = (int) (14 * getResources().getDisplayMetrics().density);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(padding, padding, padding, padding);

        TextView heading = new TextView(this);
        heading.setText(R.string.navi_title);
        heading.setTextSize(22);
        heading.setTypeface(null, Typeface.BOLD);
        root.addView(heading);

        statusView = new TextView(this);
        statusView.setText(R.string.navi_ready);
        statusView.setTextColor(Color.parseColor("#4CAF50"));
        statusView.setPadding(0, padding / 2, 0, padding / 2);
        root.addView(statusView);

        TextView configLabel = new TextView(this);
        configLabel.setText("Configuração JSON da Automação:");
        configLabel.setTextSize(14);
        root.addView(configLabel);

        configEditor = new EditText(this);
        configEditor.setText(dataStore.loadConfig());
        configEditor.setTextSize(12);
        configEditor.setTypeface(Typeface.MONOSPACE);
        configEditor.setBackgroundColor(Color.parseColor("#1E1E1E"));
        configEditor.setTextColor(Color.parseColor("#D4D4D4"));
        configEditor.setPadding(padding / 2, padding / 2, padding / 2, padding / 2);
        LinearLayout.LayoutParams editorParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (140 * getResources().getDisplayMetrics().density)
        );
        editorParams.setMargins(0, padding / 4, 0, padding / 2);
        root.addView(configEditor, editorParams);

        LinearLayout buttonBar = new LinearLayout(this);
        buttonBar.setOrientation(LinearLayout.HORIZONTAL);

        startStopButton = new Button(this);
        startStopButton.setText("Iniciar Automação");
        startStopButton.setOnClickListener(view -> toggleAutomation());
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        buttonBar.addView(startStopButton, btnParams);

        clearLogButton = new Button(this);
        clearLogButton.setText("Limpar Log");
        clearLogButton.setOnClickListener(view -> clearLogs());
        LinearLayout.LayoutParams clearParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        buttonBar.addView(clearLogButton, clearParams);

        root.addView(buttonBar);

        TextView logLabel = new TextView(this);
        logLabel.setText("Log de Processamento:");
        logLabel.setTextSize(14);
        logLabel.setPadding(0, padding / 2, 0, padding / 4);
        root.addView(logLabel);

        logScrollView = new ScrollView(this);
        logView = new TextView(this);
        logView.setText(dataStore.readLog());
        logView.setTextSize(11);
        logView.setTypeface(Typeface.MONOSPACE);
        logView.setTextColor(Color.parseColor("#EEEEEE"));
        logView.setBackgroundColor(Color.parseColor("#121212"));
        logView.setPadding(padding / 2, padding / 2, padding / 2, padding / 2);

        LinearLayout.LayoutParams logParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (160 * getResources().getDisplayMetrics().density)
        );
        logScrollView.addView(logView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(logScrollView, logParams);

        Button closeButton = new Button(this);
        closeButton.setText(R.string.navi_return_to_browser);
        closeButton.setOnClickListener(view -> finish());
        root.addView(closeButton, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        setContentView(root);
        setTitle(R.string.navi_title);

        bindBrowserTab(getIntent().getStringExtra(EXTRA_TAB_TAG));
        setupAutomationListeners();
    }

    private void bindBrowserTab(String tabTag) {
        WebView webView = tabTag == null
                ? null
                : ResourceManager.getInstance().getWebViewForTab(tabTag);
        if (webView == null || !tabTag.equals(String.valueOf(webView.getTag()))) {
            statusView.setText(R.string.navi_tab_unavailable);
            statusView.setTextColor(Color.RED);
            startStopButton.setEnabled(false);
            return;
        }

        browserSession = new WeekBrowserWebViewAdapter(tabTag, webView);
        browserSession.observeNavigation(new NaviBrowserSession.NavigationListener() {
            @Override
            public void onPageStarted(String url) {
                // Notifica início de carregamento
            }

            @Override
            public void onPageFinished(String url) {
                mainHandler.post(() -> onPageFinishedLoading(url));
            }

            @Override
            public void onPageLoadFailed(String url) {
                mainHandler.post(() -> {
                    appendLog("Falha no carregamento: " + url, true);
                    processNextPage();
                });
            }
        });
    }

    private void setupAutomationListeners() {
        automationManager.addListener(event -> mainHandler.post(() -> {
            if (event instanceof ActionEvent.SendMsg) {
                ActionEvent.SendMsg msg = (ActionEvent.SendMsg) event;
                appendLog(msg.getMessage(), msg.isLineBreak());
                if (msg.isStop()) {
                    stopAutomation();
                }
            } else if (event instanceof ActionEvent.EnqueuePage) {
                ActionEvent.EnqueuePage page = (ActionEvent.EnqueuePage) event;
                appendLog("Enfileirado: " + page.getCodigo() + " -> " + page.getUrl(), true);
                dataStore.appendProcessed(page.getCodigo() + " | " + page.getUrl());
                pageQueue.offer(page);
                processNextPage();
            } else if (event instanceof ActionEvent.OpenPage) {
                ActionEvent.OpenPage open = (ActionEvent.OpenPage) event;
                appendLog("Abrindo URL base: " + open.getUrl(), true);
                if (browserSession != null) {
                    browserSession.loadUrl(open.getUrl());
                }
            }
        }));
    }

    private void toggleAutomation() {
        if (automationManager.isRunning()) {
            stopAutomation();
        } else {
            startAutomation();
        }
    }

    private void startAutomation() {
        String configJson = configEditor.getText().toString();
        dataStore.saveConfig(configJson);

        pageQueue.clear();
        isProcessingPage = false;
        startStopButton.setText("Parar Automação");
        statusView.setText("Automação em execução...");
        statusView.setTextColor(Color.parseColor("#2196F3"));

        automationManager.start(configJson, "", "", 5.0);
    }

    private void stopAutomation() {
        automationManager.stop();
        pageQueue.clear();
        isProcessingPage = false;
        startStopButton.setText("Iniciar Automação");
        statusView.setText("Automação parada.");
        statusView.setTextColor(Color.parseColor("#FF9800"));
    }

    private void processNextPage() {
        if (isProcessingPage || pageQueue.isEmpty() || browserSession == null) {
            return;
        }

        ActionEvent.EnqueuePage nextPage = pageQueue.poll();
        if (nextPage == null || nextPage.getUrl().isEmpty()) {
            return;
        }

        isProcessingPage = true;
        appendLog("Navegando para: " + nextPage.getUrl(), true);
        try {
            browserSession.loadUrl(nextPage.getUrl());
        } catch (Exception e) {
            appendLog("Erro ao navegar: " + e.getMessage(), true);
            isProcessingPage = false;
            processNextPage();
        }
    }

    private void onPageFinishedLoading(String url) {
        if (!isProcessingPage || browserSession == null) {
            return;
        }

        appendLog("Página pronta: " + url, true);
        isProcessingPage = false;
        processNextPage();
    }

    private void appendLog(String message, boolean lineBreak) {
        dataStore.appendLog(message, lineBreak);
        if (logView != null) {
            logView.append((lineBreak ? "\n" : "") + message);
            logScrollView.post(() -> logScrollView.fullScroll(View.FOCUS_DOWN));
        }
    }

    private void clearLogs() {
        dataStore.clearLog();
        if (logView != null) {
            logView.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        stopAutomation();
        if (browserSession != null) {
            browserSession.close();
            browserSession = null;
        }
        super.onDestroy();
    }
}
