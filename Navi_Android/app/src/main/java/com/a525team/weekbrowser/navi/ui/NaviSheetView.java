package com.a525team.weekbrowser.navi.ui;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.a525team.weekbrowser.navi.browser.NaviBrowserSession;
import com.a525team.weekbrowser.navi.browser.WeekBrowserCookieProvider;
import com.a525team.weekbrowser.navi.core.AutomationManager;
import com.a525team.weekbrowser.navi.core.NaviDataStore;
import com.a525team.weekbrowser.navi.core.NpointConfigLoader;
import com.a525team.weekbrowser.navi.model.ActionEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class NaviSheetView extends LinearLayout {

    public interface BrowserSessionResolver {
        String getActiveTabId();

        NaviBrowserSession resolve(String tabId);
    }

    public interface AutomationStateListener {
        void onAutomationStateChanged(boolean running);
    }

    public enum TabSelection {
        SCRIPT, LOG, PROCESSED
    }

    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final NaviDataStore dataStore;
    private final AutomationManager automationManager;
    private NaviBrowserSession browserSession;
    private BrowserSessionResolver browserSessionResolver;
    private AutomationStateListener automationStateListener;
    private NaviBrowserSession.Cancellable navigationObserver;

    private TabSelection currentTab = TabSelection.SCRIPT;

    // Abas botões
    private Button tabScriptBtn;
    private Button tabLogBtn;
    private Button tabProcessedBtn;
    private TextView logBadge;
    private TextView processedBadge;
    private int processedRecordCount = 0;

    // Conteúdo das Abas
    private FrameLayout tabContentContainer;
    private LinearLayout scriptView;
    private LinearLayout logViewContainer;
    private LinearLayout processedViewContainer;

    // Script View componentes
    private EditText configEditor;
    private Button startStopBtn;
    private final List<Button> startStopButtons = new ArrayList<>();
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
    private boolean isStarting = false;
    private boolean automationStateKnown = false;
    private boolean lastAutomationState = false;
    private int startGeneration = 0;
    private Thread configLoadThread;

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

    public void setBrowserSessionResolver(BrowserSessionResolver resolver) {
        this.browserSessionResolver = resolver;
        bindToCurrentTab();
    }

    public void setAutomationStateListener(AutomationStateListener listener) {
        this.automationStateListener = listener;
        if (listener != null && automationStateKnown) {
            listener.onAutomationStateChanged(lastAutomationState);
        }
    }

    private void publishAutomationState(boolean running) {
        if (!automationStateKnown && !running) return;
        if (automationStateKnown && lastAutomationState == running) return;
        automationStateKnown = true;
        lastAutomationState = running;
        if (automationStateListener != null) {
            automationStateListener.onAutomationStateChanged(running);
        }
    }

    private void bindToCurrentTab() {
        if (browserSessionResolver == null) return;
        String activeTabId = browserSessionResolver.getActiveTabId();
        if (activeTabId == null || activeTabId.trim().isEmpty()) return;
        if (browserSession != null && activeTabId.equals(browserSession.getTabId())) return;
        setBrowserSession(browserSessionResolver.resolve(activeTabId));
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
        logBadge = createBadge();
        processedBadge = createBadge();

        tabScriptBtn.setOnClickListener(v -> selectTab(TabSelection.SCRIPT));
        tabLogBtn.setOnClickListener(v -> selectTab(TabSelection.LOG));
        tabProcessedBtn.setOnClickListener(v -> selectTab(TabSelection.PROCESSED));

        int tabHeight = (int) (36 * getResources().getDisplayMetrics().density);
        tabBar.addView(createTabItem(tabScriptBtn, null), new LayoutParams(0, tabHeight, 1f));
        tabBar.addView(createTabItem(tabLogBtn, logBadge), new LayoutParams(0, tabHeight, 1f));
        tabBar.addView(createTabItem(tabProcessedBtn, processedBadge), new LayoutParams(0, tabHeight, 1f));

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

    private FrameLayout createTabItem(Button button, TextView badge) {
        FrameLayout item = new FrameLayout(getContext());
        item.addView(button, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        if (badge != null) {
            FrameLayout.LayoutParams badgeParams = new FrameLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, (int) (18 * getResources().getDisplayMetrics().density), Gravity.TOP | Gravity.END);
            badgeParams.topMargin = (int) (1 * getResources().getDisplayMetrics().density);
            badgeParams.rightMargin = (int) (8 * getResources().getDisplayMetrics().density);
            item.addView(badge, badgeParams);
        }
        return item;
    }

    private TextView createBadge() {
        TextView badge = new TextView(getContext());
        badge.setTextColor(Color.WHITE);
        badge.setTextSize(10);
        badge.setTypeface(null, Typeface.BOLD);
        badge.setGravity(Gravity.CENTER);
        badge.setPadding((int) (5 * getResources().getDisplayMetrics().density), 0,
                (int) (5 * getResources().getDisplayMetrics().density), 0);
        android.graphics.drawable.GradientDrawable background = new android.graphics.drawable.GradientDrawable();
        background.setColor(Color.parseColor("#E53935"));
        background.setCornerRadius((int) (9 * getResources().getDisplayMetrics().density));
        badge.setBackground(background);
        badge.setVisibility(GONE);
        return badge;
    }

    private void selectTab(TabSelection tab) {
        currentTab = tab;

        if (tab == TabSelection.LOG && logBadge != null) logBadge.setVisibility(GONE);
        if (tab == TabSelection.PROCESSED && processedBadge != null) {
            processedBadge.setVisibility(GONE);
        }

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
        configureStartStopButton(startStopBtn);
        LayoutParams startParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
        actionRow.addView(startStopBtn, startParams);

        Button hideKeyboardBtn = new Button(getContext());
        hideKeyboardBtn.setText("⌄");
        hideKeyboardBtn.setTextSize(20);
        hideKeyboardBtn.setTextColor(Color.WHITE);
        hideKeyboardBtn.setBackgroundColor(Color.parseColor("#333333"));
        hideKeyboardBtn.setPadding(0, 0, 0, 0);
        hideKeyboardBtn.setContentDescription("Ocultar teclado");
        hideKeyboardBtn.setOnClickListener(v -> {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(configEditor.getWindowToken(), 0);
            }
        });
        LayoutParams hideKeyboardParams = new LayoutParams(
                (int) (44 * getResources().getDisplayMetrics().density), LayoutParams.MATCH_PARENT);
        hideKeyboardParams.leftMargin = p / 2;
        actionRow.addView(hideKeyboardBtn, hideKeyboardParams);

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

        LinearLayout actionRow = createAuxiliaryActionRow();
        Button logStartStopBtn = new Button(getContext());
        configureStartStopButton(logStartStopBtn);
        actionRow.addView(logStartStopBtn, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));

        Button clearLogBtn = createClearButton("Limpar log");
        clearLogBtn.setText("🗑");
        clearLogBtn.setOnClickListener(v -> {
            dataStore.clearLog();
            logTextView.setText("");
        });
        actionRow.addView(clearLogBtn, iconButtonParams(p));
        logViewContainer.addView(actionRow, auxiliaryActionParams(p));
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

        LinearLayout actionRow = createAuxiliaryActionRow();
        Button processedStartStopBtn = new Button(getContext());
        configureStartStopButton(processedStartStopBtn);
        actionRow.addView(processedStartStopBtn, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));

        Button clearProcBtn = createClearButton("Limpar processados");
        clearProcBtn.setText("🧹");
        clearProcBtn.setOnClickListener(v -> {
            dataStore.clearProcessed();
            processedRecordCount = 0;
            processedBadge.setVisibility(GONE);
            processedTextView.setText("");
        });
        actionRow.addView(clearProcBtn, iconButtonParams(p));
        processedViewContainer.addView(actionRow, auxiliaryActionParams(p));
    }

    private void configureStartStopButton(Button button) {
        button.setTextColor(Color.WHITE);
        button.setOnClickListener(v -> toggleAutomation());
        startStopButtons.add(button);
        syncStartStopButtons();
    }

    private void syncStartStopButtons() {
        boolean running = automationManager.isRunning() || isStarting;
        String label = running ? "Parar Automação" : "Iniciar Automação";
        int color = Color.parseColor(running ? "#B71C1C" : "#1B5E20");
        for (Button button : startStopButtons) {
            button.setText(label);
            button.setBackgroundColor(color);
        }
    }

    private LinearLayout createAuxiliaryActionRow() {
        LinearLayout row = new LinearLayout(getContext());
        row.setOrientation(HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        return row;
    }

    private Button createClearButton(String description) {
        Button button = new Button(getContext());
        button.setTextSize(18);
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(Color.parseColor("#333333"));
        button.setPadding(0, 0, 0, 0);
        button.setContentDescription(description);
        return button;
    }

    private LayoutParams iconButtonParams(int padding) {
        LayoutParams params = new LayoutParams((int) (44 * getResources().getDisplayMetrics().density), LayoutParams.MATCH_PARENT);
        params.leftMargin = padding / 2;
        return params;
    }

    private LayoutParams auxiliaryActionParams(int padding) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, (int) (40 * getResources().getDisplayMetrics().density));
        params.topMargin = padding / 2;
        return params;
    }

    private void setupAutomationListeners() {
        automationManager.addListener(event -> mainHandler.post(() -> {
            if (event instanceof ActionEvent.SendMsg) {
                ActionEvent.SendMsg msg = (ActionEvent.SendMsg) event;
                if (msg.getMessage().startsWith("Session Warning:")
                        || msg.getMessage().startsWith("Session Error:")) {
                    selectTab(TabSelection.LOG);
                }
                appendLog(msg.getMessage(), msg.isLineBreak());
                if (msg.isStop()) {
                    stopAutomation();
                }
            } else if (event instanceof ActionEvent.EnqueuePage) {
                ActionEvent.EnqueuePage page = (ActionEvent.EnqueuePage) event;
                appendLog("Enfileirado: " + page.getCodigo() + " -> " + page.getUrl(), true);
                dataStore.appendProcessed(page.getCodigo() + " - " + page.getUsername());
                processedRecordCount++;
                if (currentTab == TabSelection.PROCESSED) {
                    processedTextView.setText(dataStore.readProcessed());
                } else {
                    processedBadge.setText(String.valueOf(processedRecordCount));
                    processedBadge.setVisibility(VISIBLE);
                }
                pageQueue.offer(page);
                processNextPage();
            } else if (event instanceof ActionEvent.OpenPage) {
                ActionEvent.OpenPage open = (ActionEvent.OpenPage) event;
                appendLog("Abrindo URL base: " + open.getUrl(), true);
                bindToCurrentTab();
                if (browserSession != null) {
                    browserSession.loadUrl(open.getUrl());
                }
            }
        }));
    }

    private void toggleAutomation() {
        if (automationManager.isRunning() || isStarting) {
            stopAutomation();
        } else {
            startAutomation();
        }
    }

    public void startAutomation() {
        selectTab(TabSelection.LOG);
        publishAutomationState(true);
        String configJson = configEditor.getText().toString();
        dataStore.saveConfig(configJson);

        pageQueue.clear();
        isProcessingPage = false;
        isPageFullyLoaded = false;
        currentPage = null;
        syncStartStopButtons();

        String trimmedScript = configJson.trim();
        if (trimmedScript.startsWith("javascript:")) {
            executeJavascriptScript(trimmedScript.substring("javascript:".length()));
            return;
        }

        final JSONObject scriptObject;
        try {
            scriptObject = new JSONObject(configJson);
        } catch (JSONException ignored) {
            // Scripts JSON legados continuam sendo encaminhados ao parser atual para
            // preservar os erros e a compatibilidade já existentes.
            startWithConfig(configJson);
            return;
        }

        if (NpointConfigLoader.isNpointWrapper(scriptObject)) {
            resolveNpointAndStart(scriptObject);
            return;
        }

        startWithConfig(configJson);
    }

    private void startWithConfig(String configJson) {
        final int generation = ++startGeneration;
        isStarting = true;
        syncStartStopButtons();
        configLoadThread = new Thread(() -> {
            try {
                automationManager.start(configJson, "", new WeekBrowserCookieProvider(), dataStore.getMonitorInterval(),
                        dataStore.getMonitorLiveOnlineInterval());
            } catch (Exception e) {
                mainHandler.post(() -> {
                    if (generation != startGeneration || !isStarting) return;
                    appendLog("Falha ao iniciar automação: " + safeErrorMessage(e), true);
                    resetStartButton();
                });
            } finally {
                mainHandler.post(() -> {
                    if (generation != startGeneration || !isStarting) return;
                    isStarting = false;
                    configLoadThread = null;
                    syncStartStopButtons();
                });
            }
        }, "navi-automation-start");
        configLoadThread.setDaemon(true);
        configLoadThread.start();
    }

    private void resolveNpointAndStart(JSONObject wrapper) {
        final int generation = ++startGeneration;
        isStarting = true;
        syncStartStopButtons();
        appendLog("Buscando configuração criptografada no nPoint...", true);

        configLoadThread = new Thread(() -> {
            try {
                String resolvedConfig = NpointConfigLoader.resolve(wrapper);
                String wrapperSessionId = wrapper.optString("sessionId", "");
                if (!wrapperSessionId.trim().isEmpty()) {
                    JSONObject resolvedJson = new JSONObject(resolvedConfig);
                    resolvedJson.put("sessionId", wrapperSessionId);
                    resolvedConfig = resolvedJson.toString();
                }
                final String configToStart = resolvedConfig;
                mainHandler.post(() -> {
                    if (generation != startGeneration || !isStarting) return;
                    isStarting = false;
                    configLoadThread = null;
                    syncStartStopButtons();
                    appendLog("Configuração nPoint carregada.", true);
                    startWithConfig(configToStart);
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    if (generation != startGeneration || !isStarting) return;
                    isStarting = false;
                    configLoadThread = null;
                    appendLog("Falha ao carregar configuração nPoint: " + safeErrorMessage(e), true);
                    resetStartButton();
                });
            }
        }, "navi-npoint-config");
        configLoadThread.start();
    }

    private void executeJavascriptScript(String javascript) {
        if (javascript.trim().isEmpty()) {
            appendLog("O script JavaScript está vazio.", true);
            resetStartButton();
            return;
        }
        if (browserSession == null) {
            appendLog("Não há uma aba do navegador disponível para executar JavaScript.", true);
            resetStartButton();
            return;
        }

        final int generation = ++startGeneration;
        isStarting = true;
        syncStartStopButtons();
        appendLog("Executando script JavaScript...", true);
        browserSession.evaluateJavaScript(javascript, 60000L, result -> mainHandler.post(() -> {
            if (generation != startGeneration || !isStarting) return;
            isStarting = false;
            if (result.isSuccess()) {
                appendLog("Script JavaScript executado.", true);
                if (result.getValue() != null) {
                    appendLog("Resultado: " + result.getValue(), true);
                }
            } else {
                appendLog("Falha ao executar JavaScript: " + result.getDetail(), true);
            }
            resetStartButton();
        }));
    }

    private String safeErrorMessage(Exception error) {
        String message = error.getMessage();
        return message != null && !message.trim().isEmpty()
                ? message
                : error.getClass().getSimpleName();
    }

    private void resetStartButton() {
        isStarting = false;
        publishAutomationState(false);
        syncStartStopButtons();
    }

    public void stopAutomation() {
        startGeneration++;
        isStarting = false;
        if (configLoadThread != null) {
            configLoadThread.interrupt();
            configLoadThread = null;
        }
        automationManager.stop();
        mainHandler.removeCallbacksAndMessages(null);
        pageQueue.clear();
        isProcessingPage = false;
        isPageFullyLoaded = false;
        currentPage = null;
        resetStartButton();
        int stopFeedback = android.os.Build.VERSION.SDK_INT >= 30
                ? HapticFeedbackConstants.REJECT
                : HapticFeedbackConstants.LONG_PRESS;
        performHapticFeedback(stopFeedback);
    }

    public boolean isAutomationRunning() {
        return automationManager.isRunning();
    }

    private void processNextPage() {
        bindToCurrentTab();
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
            executeAction(actionScript, true);
            return;
        }

        browserSession.evaluateJavaScript(verifyScript, 5000L, result -> mainHandler.post(() -> {
            if (!automationManager.isRunning() || !isProcessingPage || activePage != currentPage) {
                return;
            }

            performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK);

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
                executeAction(actionScript, true);
            } else {
                String verify2 = activePage.getScriptVerify2();
                if (verify2 != null && !verify2.trim().isEmpty()) {
                    pollSecondaryVerification();
                } else {
                    appendLog(".", false);
                    scheduleMainVerificationAfterMonitorInterval();
                }
            }
        }));
    }

    private void pollSecondaryVerification() {
        if (!automationManager.isRunning() || !isProcessingPage || currentPage == null || browserSession == null) {
            return;
        }
        final ActionEvent.EnqueuePage activePage = currentPage;
        String verify2 = activePage.getScriptVerify2();
        if (verify2 == null || verify2.trim().isEmpty()) {
            scheduleMainVerificationAfterMonitorInterval();
            return;
        }

        browserSession.evaluateJavaScript(verify2, 5000L, result -> mainHandler.post(() -> {
            if (!automationManager.isRunning() || !isProcessingPage || activePage != currentPage) {
                return;
            }

            performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK);

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

            if (isCondition2Met) {
                appendLog("Encontrou! Executou 2a ação!", true);
                executeAction(activePage.getScript2(), false);
            } else {
                appendLog(".", false);
                scheduleMainVerificationAfterMonitorInterval();
            }
        }));
    }

    private void executeAction(String actionScript, boolean finishAfterAction) {
        if (actionScript == null || actionScript.trim().isEmpty()) {
            if (finishAfterAction) {
                finishCurrentPage();
            } else {
                scheduleMainVerification();
            }
            return;
        }

        final ActionEvent.EnqueuePage activePage = currentPage;
        appendLog("Executando ação...", true);
        browserSession.evaluateJavaScript(actionScript, 60000L, result -> mainHandler.post(() -> {
            if (!automationManager.isRunning() || !isProcessingPage || activePage != currentPage) {
                return;
            }
            if (result.isSuccess()) {
                appendLog("Ação concluída.", true);
                if (finishAfterAction && (Boolean.FALSE.equals(result.getValue()) || "false".equalsIgnoreCase(String.valueOf(result.getValue())))) {
                    appendLog("Automação pausada pelo usuário no alerta de confirmação.", true);
                    stopAutomation();
                    return;
                }
            } else {
                appendLog("Resultado da ação: " + result.getDetail(), true);
            }
            if (finishAfterAction) {
                finishCurrentPage();
            } else {
                scheduleMainVerification();
            }
        }));
    }

    private void scheduleMainVerification() {
        long likeWaitMs = (long) (dataStore.getLikeWaitInterval() * 1000);
        if (likeWaitMs < 500) likeWaitMs = 500L;
        long monitorWaitMs = (long) (dataStore.getIdsWaitInterval() * 1000);
        if (monitorWaitMs < 500) monitorWaitMs = 500L;
        mainHandler.postDelayed(this::pollVerification, likeWaitMs + monitorWaitMs);
    }

    private void scheduleMainVerificationAfterMonitorInterval() {
        long monitorWaitMs = (long) (dataStore.getIdsWaitInterval() * 1000);
        if (monitorWaitMs < 500) monitorWaitMs = 500L;
        mainHandler.postDelayed(this::pollVerification, monitorWaitMs);
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
        } else if (logBadge != null) {
            logBadge.setText("!");
            logBadge.setVisibility(VISIBLE);
        }
    }
}
