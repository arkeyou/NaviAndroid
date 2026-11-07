package com.a525team.weekbrowser.navi.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.a525team.weekbrowser.navi.browser.NaviBrowserSession;
import com.a525team.weekbrowser.navi.core.AutomationManager;
import com.a525team.weekbrowser.navi.core.NaviDataStore;
import com.a525team.weekbrowser.navi.model.ActionEvent;

import java.util.LinkedList;
import java.util.Queue;

public class NaviSheetView extends LinearLayout {

    public enum TabSelection {
        SCRIPT, LOG, PROCESSED
    }

    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final NaviDataStore dataStore;
    private final AutomationManager automationManager;
    private NaviBrowserSession browserSession;
    private NaviBrowserSession.Cancellable navigationObserver;

    private TabSelection currentTab = TabSelection.SCRIPT;

    // Abas botões
    private Button tabScriptBtn;
    private Button tabLogBtn;
    private Button tabProcessedBtn;

    // Conteúdo das Abas
    private FrameLayout tabContentContainer;
    private LinearLayout scriptView;
    private LinearLayout logViewContainer;
    private LinearLayout processedViewContainer;

    // Script View componentes
    private EditText configEditor;
    private Button startStopBtn;
    private Button settingsBtn;

    // Log View componentes
    private TextView logTextView;
    private ScrollView logScrollView;

    // Processados View componentes
    private TextView processedTextView;
    private ScrollView processedScrollView;

    // Fila e controle de página corrente
    private final Queue<ActionEvent.EnqueuePage> pageQueue = new LinkedList<>();
    private ActionEvent.EnqueuePage currentPage = null;
    private boolean isProcessingPage = false;
    private boolean isPageFullyLoaded = false;
    private int verifyAttemptCount = 0;

    public NaviSheetView(Context context) {
        super(context);
        this.dataStore = new NaviDataStore(context);
        this.automationManager = new AutomationManager();
        initView();
        setupAutomationListeners();
    }

    public void setBrowserSession(NaviBrowserSession session) {
        if (this.navigationObserver != null) {
            this.navigationObserver.cancel();
            this.navigationObserver = null;
        }

        this.browserSession = session;

        if (session != null) {
            this.navigationObserver = session.observeNavigation(new NaviBrowserSession.NavigationListener() {
                @Override
                public void onPageStarted(String url) {
                    mainHandler.post(() -> {
                        isPageFullyLoaded = false;
                        if (isProcessingPage && currentPage != null) {
                            appendLog("Iniciando carregamento: " + url, true);
                        }
                    });
                }

                @Override
                public void onPageFinished(String url) {
                    mainHandler.post(() -> onPageFinishedLoading(url));
                }

                @Override
                public void onPageLoadFailed(String url) {
                    mainHandler.post(() -> {
                        if (isProcessingPage) {
                            appendLog("Falha no carregamento: " + url, true);
                            isProcessingPage = false;
                            isPageFullyLoaded = false;
                            currentPage = null;
                            processNextPage();
                        }
                    });
                }
            });
        }
    }

    private void initView() {
        setOrientation(VERTICAL);
        setBackgroundColor(Color.parseColor("#181818"));

        int p = (int) (10 * getResources().getDisplayMetrics().density);

        // Barra de abas superior
        LinearLayout tabBar = new LinearLayout(getContext());
        tabBar.setOrientation(HORIZONTAL);
        tabBar.setBackgroundColor(Color.parseColor("#222222"));
        tabBar.setPadding(p / 2, p / 4, p / 2, p / 4);

        tabScriptBtn = createTabButton("Script");
        tabLogBtn = createTabButton("Log");
        tabProcessedBtn = createTabButton("Processados");

        tabScriptBtn.setOnClickListener(v -> selectTab(TabSelection.SCRIPT));
        tabLogBtn.setOnClickListener(v -> selectTab(TabSelection.LOG));
        tabProcessedBtn.setOnClickListener(v -> selectTab(TabSelection.PROCESSED));

        tabBar.addView(tabScriptBtn, new LayoutParams(0, (int) (36 * getResources().getDisplayMetrics().density), 1f));
        tabBar.addView(tabLogBtn, new LayoutParams(0, (int) (36 * getResources().getDisplayMetrics().density), 1f));
        tabBar.addView(tabProcessedBtn, new LayoutParams(0, (int) (36 * getResources().getDisplayMetrics().density), 1f));

        addView(tabBar, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        // Container de Conteúdo das Abas
        tabContentContainer = new FrameLayout(getContext());
        tabContentContainer.setPadding(p, p / 2, p, p);

        createScriptView(p);
        createLogView(p);
        createProcessedView(p);

        tabContentContainer.addView(scriptView);
        tabContentContainer.addView(logViewContainer);
        tabContentContainer.addView(processedViewContainer);

        addView(tabContentContainer, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        selectTab(TabSelection.SCRIPT);
    }

    private Button createTabButton(String text) {
        Button btn = new Button(getContext());
        btn.setText(text);
        btn.setTextSize(12);
        btn.setTypeface(null, Typeface.BOLD);
        btn.setTextColor(Color.parseColor("#AAAAAA"));
        btn.setBackgroundColor(Color.TRANSPARENT);
        return btn;
    }

    private void selectTab(TabSelection tab) {
        currentTab = tab;

        tabScriptBtn.setTextColor(tab == TabSelection.SCRIPT ? Color.parseColor("#2196F3") : Color.parseColor("#888888"));
        tabLogBtn.setTextColor(tab == TabSelection.LOG ? Color.parseColor("#2196F3") : Color.parseColor("#888888"));
        tabProcessedBtn.setTextColor(tab == TabSelection.PROCESSED ? Color.parseColor("#2196F3") : Color.parseColor("#888888"));

        scriptView.setVisibility(tab == TabSelection.SCRIPT ? VISIBLE : GONE);
        logViewContainer.setVisibility(tab == TabSelection.LOG ? VISIBLE : GONE);
        processedViewContainer.setVisibility(tab == TabSelection.PROCESSED ? VISIBLE : GONE);

        if (tab == TabSelection.LOG) {
            logTextView.setText(dataStore.readLog());
            logScrollView.post(() -> logScrollView.fullScroll(FOCUS_DOWN));
        } else if (tab == TabSelection.PROCESSED) {
            processedTextView.setText(dataStore.readProcessed());
        }
    }

    private void createScriptView(int p) {
        scriptView = new LinearLayout(getContext());
        scriptView.setOrientation(VERTICAL);

        configEditor = new EditText(getContext());
        configEditor.setText(dataStore.loadConfig());
        configEditor.setTextSize(11);
        configEditor.setTypeface(Typeface.MONOSPACE);
        configEditor.setTextColor(Color.parseColor("#E0E0E0"));
        configEditor.setBackgroundColor(Color.parseColor("#121212"));
        configEditor.setPadding(p / 2, p / 2, p / 2, p / 2);
        configEditor.setGravity(Gravity.TOP);

        LayoutParams editorParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f);
        scriptView.addView(configEditor, editorParams);

        // Linha com Botão Iniciar Automação + Botão Engrenagem de Configurações
        LinearLayout actionRow = new LinearLayout(getContext());
        actionRow.setOrientation(HORIZONTAL);
        actionRow.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams actionRowParams = new LayoutParams(LayoutParams.MATCH_PARENT, (int) (40 * getResources().getDisplayMetrics().density));
        actionRowParams.topMargin = p / 2;

        startStopBtn = new Button(getContext());
        startStopBtn.setText("Iniciar Automação");
        startStopBtn.setBackgroundColor(Color.parseColor("#1B5E20"));
        startStopBtn.setTextColor(Color.WHITE);
        startStopBtn.setOnClickListener(v -> toggleAutomation());
        LayoutParams startParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
        actionRow.addView(startStopBtn, startParams);

        settingsBtn = new Button(getContext());
        settingsBtn.setText("⚙");
        settingsBtn.setTextSize(18);
        settingsBtn.setTextColor(Color.WHITE);
        settingsBtn.setBackgroundColor(Color.parseColor("#333333"));
        settingsBtn.setPadding(0, 0, 0, 0);
        settingsBtn.setOnClickListener(v -> NaviSettingsDialog.show(getContext(), dataStore, () -> {
            appendLog("Intervalos de automação atualizados.", true);
        }));
        LayoutParams settingsParams = new LayoutParams((int) (44 * getResources().getDisplayMetrics().density), LayoutParams.MATCH_PARENT);
        settingsParams.leftMargin = p / 2;
        actionRow.addView(settingsBtn, settingsParams);

        scriptView.addView(actionRow, actionRowParams);
    }

    private void createLogView(int p) {
        logViewContainer = new LinearLayout(getContext());
        logViewContainer.setOrientation(VERTICAL);

        logScrollView = new ScrollView(getContext());
        logTextView = new TextView(getContext());
        logTextView.setTextSize(11);
        logTextView.setTypeface(Typeface.MONOSPACE);
        logTextView.setTextColor(Color.parseColor("#D4D4D4"));
        logTextView.setBackgroundColor(Color.parseColor("#121212"));
        logTextView.setPadding(p / 2, p / 2, p / 2, p / 2);

        logScrollView.addView(logTextView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        logViewContainer.addView(logScrollView, new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f));

        Button clearLogBtn = new Button(getContext());
        clearLogBtn.setText("Limpar Log");
        clearLogBtn.setOnClickListener(v -> {
            dataStore.clearLog();
            logTextView.setText("");
        });
        LayoutParams clearParams = new LayoutParams(LayoutParams.MATCH_PARENT, (int) (38 * getResources().getDisplayMetrics().density));
        clearParams.topMargin = p / 2;
        logViewContainer.addView(clearLogBtn, clearParams);
    }

    private void createProcessedView(int p) {
        processedViewContainer = new LinearLayout(getContext());
        processedViewContainer.setOrientation(VERTICAL);

        processedScrollView = new ScrollView(getContext());
        processedTextView = new TextView(getContext());
        processedTextView.setTextSize(11);
        processedTextView.setTypeface(Typeface.MONOSPACE);
        processedTextView.setTextColor(Color.parseColor("#4CAF50"));
        processedTextView.setBackgroundColor(Color.parseColor("#121212"));
        processedTextView.setPadding(p / 2, p / 2, p / 2, p / 2);

        processedScrollView.addView(processedTextView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        processedViewContainer.addView(processedScrollView, new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f));

        Button clearProcBtn = new Button(getContext());
        clearProcBtn.setText("Limpar Processados");
        clearProcBtn.setOnClickListener(v -> {
            dataStore.clearProcessed();
            processedTextView.setText("");
        });
        LayoutParams clearParams = new LayoutParams(LayoutParams.MATCH_PARENT, (int) (38 * getResources().getDisplayMetrics().density));
        clearParams.topMargin = p / 2;
        processedViewContainer.addView(clearProcBtn, clearParams);
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
                if (currentTab == TabSelection.PROCESSED) {
                    processedTextView.setText(dataStore.readProcessed());
                }
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

    public void startAutomation() {
        String configJson = configEditor.getText().toString();
        dataStore.saveConfig(configJson);

        pageQueue.clear();
        isProcessingPage = false;
        isPageFullyLoaded = false;
        currentPage = null;
        startStopBtn.setText("Parar Automação");
        startStopBtn.setBackgroundColor(Color.parseColor("#B71C1C"));

        double monitorInterval = dataStore.getMonitorInterval();
        automationManager.start(configJson, "", "", monitorInterval);
    }

    public void stopAutomation() {
        automationManager.stop();
        mainHandler.removeCallbacksAndMessages(null);
        pageQueue.clear();
        isProcessingPage = false;
        isPageFullyLoaded = false;
        currentPage = null;
        startStopBtn.setText("Iniciar Automação");
        startStopBtn.setBackgroundColor(Color.parseColor("#1B5E20"));
    }

    public boolean isAutomationRunning() {
        return automationManager.isRunning();
    }

    private void processNextPage() {
        if (!automationManager.isRunning() || isProcessingPage || pageQueue.isEmpty() || browserSession == null) {
            return;
        }

        ActionEvent.EnqueuePage nextPage = pageQueue.poll();
        if (nextPage == null || nextPage.getUrl().isEmpty()) {
            return;
        }

        isProcessingPage = true;
        isPageFullyLoaded = false;
        currentPage = nextPage;
        verifyAttemptCount = 0;

        appendLog("Abrindo página: " + nextPage.getUrl(), true);
        try {
            browserSession.loadUrl(nextPage.getUrl());
        } catch (Exception e) {
            appendLog("Erro ao carregar página: " + e.getMessage(), true);
            isProcessingPage = false;
            currentPage = null;
            processNextPage();
        }
    }

    private void onPageFinishedLoading(String url) {
        if (!automationManager.isRunning() || !isProcessingPage || currentPage == null || browserSession == null) {
            return;
        }

        if (isPageFullyLoaded) {
            return;
        }
        isPageFullyLoaded = true;

        appendLog("Página carregada! Aguardando elementos da tela...", true);
        verifyAttemptCount = 0;
        pollVerification();
    }

    private void pollVerification() {
        if (!automationManager.isRunning() || !isProcessingPage || currentPage == null || browserSession == null) {
            return;
        }

        final ActionEvent.EnqueuePage activePage = currentPage;
        final String verifyScript = activePage.getScriptVerify();
        final String actionScript = activePage.getScript();

        if (verifyScript == null || verifyScript.trim().isEmpty()) {
            executeAction(actionScript);
            return;
        }

        verifyAttemptCount++;
        browserSession.evaluateJavaScript(verifyScript, 5000L, result -> mainHandler.post(() -> {
            if (!automationManager.isRunning() || !isProcessingPage || activePage != currentPage) {
                return;
            }

            boolean isConditionMet = false;
            if (result.isSuccess()) {
                Object val = result.getValue();
                if (val instanceof Boolean) {
                    isConditionMet = (Boolean) val;
                } else if (val != null) {
                    String s = String.valueOf(val).trim();
                    isConditionMet = "true".equalsIgnoreCase(s) || (!"false".equalsIgnoreCase(s) && !"0".equals(s) && !s.isEmpty());
                }
            }

            if (isConditionMet) {
                appendLog("Encontrou! Executou 1a ação!", true);
                executeAction(actionScript);
            } else {
                long likeWaitMs = (long) (dataStore.getLikeWaitInterval() * 1000);
                if (likeWaitMs < 500) likeWaitMs = 500L;

                int maxAttempts = (int) (dataStore.getPageWaitInterval() / (likeWaitMs / 1000.0));
                if (maxAttempts < 5) maxAttempts = 10;

                if (verifyAttemptCount < maxAttempts) {
                    appendLog("·", false);
                    mainHandler.postDelayed(this::pollVerification, likeWaitMs);
                } else {
                    String verify2 = activePage.getScriptVerify2();
                    if (verify2 != null && !verify2.trim().isEmpty()) {
                        pollSecondaryVerification(activePage.getScript2(), 0);
                    } else {
                        appendLog("\nElemento não encontrado na página após espera. Avançando.", true);
                        finishCurrentPage();
                    }
                }
            }
        }));
    }

    private void pollSecondaryVerification(String action2Script, int attempt) {
        if (!automationManager.isRunning() || !isProcessingPage || currentPage == null || browserSession == null) {
            return;
        }
        final ActionEvent.EnqueuePage activePage = currentPage;
        String verify2 = activePage.getScriptVerify2();
        if (verify2 == null || verify2.trim().isEmpty()) {
            finishCurrentPage();
            return;
        }

        browserSession.evaluateJavaScript(verify2, 5000L, result -> mainHandler.post(() -> {
            if (!automationManager.isRunning() || !isProcessingPage || activePage != currentPage) {
                return;
            }

            boolean isCondition2Met = false;
            if (result.isSuccess()) {
                Object val = result.getValue();
                if (val instanceof Boolean) {
                    isCondition2Met = (Boolean) val;
                } else if (val != null) {
                    String s = String.valueOf(val).trim();
                    isCondition2Met = "true".equalsIgnoreCase(s) || (!"false".equalsIgnoreCase(s) && !"0".equals(s) && !s.isEmpty());
                }
            }

            if (isCondition2Met && action2Script != null && !action2Script.trim().isEmpty()) {
                appendLog("Encontrou! Executou 2a ação!", true);
                executeAction(action2Script);
            } else if (attempt < 5) {
                long likeWaitMs = (long) (dataStore.getLikeWaitInterval() * 1000);
                if (likeWaitMs < 500) likeWaitMs = 500L;
                mainHandler.postDelayed(() -> pollSecondaryVerification(action2Script, attempt + 1), likeWaitMs);
            } else {
                appendLog("\nCondição secundária não atendida. Avançando.", true);
                finishCurrentPage();
            }
        }));
    }

    private void executeAction(String actionScript) {
        if (actionScript == null || actionScript.trim().isEmpty()) {
            finishCurrentPage();
            return;
        }

        appendLog("Executando ação...", true);
        browserSession.evaluateJavaScript(actionScript, 60000L, result -> mainHandler.post(() -> {
            if (result.isSuccess()) {
                appendLog("Ação concluída.", true);
                if (Boolean.FALSE.equals(result.getValue()) || "false".equalsIgnoreCase(String.valueOf(result.getValue()))) {
                    appendLog("Automação pausada pelo usuário no alerta de confirmação.", true);
                    stopAutomation();
                    return;
                }
            } else {
                appendLog("Resultado da ação: " + result.getDetail(), true);
            }
            finishCurrentPage();
        }));
    }

    private void finishCurrentPage() {
        long idsWaitMs = (long) (dataStore.getIdsWaitInterval() * 1000);
        if (idsWaitMs < 500) idsWaitMs = 1000L;

        mainHandler.postDelayed(() -> {
            isProcessingPage = false;
            isPageFullyLoaded = false;
            currentPage = null;
            processNextPage();
        }, idsWaitMs);
    }

    private void appendLog(String message, boolean lineBreak) {
        dataStore.appendLog(message, lineBreak);
        if (logTextView != null && currentTab == TabSelection.LOG) {
            logTextView.append((lineBreak ? "\n" : "") + message);
            logScrollView.post(() -> logScrollView.fullScroll(FOCUS_DOWN));
        }
    }
}
