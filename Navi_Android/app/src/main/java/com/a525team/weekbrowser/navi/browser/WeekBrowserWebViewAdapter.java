package com.a525team.weekbrowser.navi.browser;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;

import com.a525team.weekbrowser.ResourceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class WeekBrowserWebViewAdapter implements NaviBrowserSession {
    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());
    private static final AtomicLong NEXT_CALL_ID = new AtomicLong();
    private static final Map<WebView, WeakReference<WeekBrowserWebViewAdapter>> ACTIVE_ADAPTERS =
            new WeakHashMap<>();
    private static final int MAX_SCRIPT_LENGTH = 64 * 1024;
    private static final long MAX_TIMEOUT_MILLIS = 60_000L;

    private final String tabId;
    private final WebView webView;
    private final Map<Long, PendingEvaluation> pendingEvaluations = new HashMap<>();
    private final List<NavigationListener> navigationListeners = new ArrayList<>();
    private long navigationVersion;
    private boolean pageLoading;
    private boolean pageLoadFailed;
    private boolean closed;

    public WeekBrowserWebViewAdapter(String tabId, WebView webView) {
        requireMainThread();
        this.tabId = Objects.requireNonNull(tabId, "tabId");
        this.webView = Objects.requireNonNull(webView, "webView");
        if (!isBoundToTab()) {
            throw new IllegalArgumentException("The WebView is not registered to the requested tab.");
        }
        pageLoading = webView.getProgress() < 100;
        synchronized (ACTIVE_ADAPTERS) {
            ACTIVE_ADAPTERS.put(webView, new WeakReference<>(this));
        }
    }

    @Override
    public String getTabId() {
        return tabId;
    }

    @Override
    public String getCurrentUrl() {
        requireMainThread();
        return isBoundToTab() ? webView.getUrl() : null;
    }

    @Override
    public boolean isPageLoading() {
        requireMainThread();
        return closed || pageLoading;
    }

    @Override
    public Cancellable observeNavigation(NavigationListener listener) {
        requireMainThread();
        Objects.requireNonNull(listener, "listener");
        if (!isBoundToTab()) {
            throw new IllegalStateException("The browser tab is no longer available.");
        }
        navigationListeners.add(listener);
        return () -> MAIN_HANDLER.post(() -> navigationListeners.remove(listener));
    }

    @Override
    public boolean canGoBack() {
        requireMainThread();
        return isBoundToTab() && webView.canGoBack();
    }

    @Override
    public void goBack() {
        requireMainThread();
        if (isBoundToTab() && webView.canGoBack()) {
            webView.goBack();
        }
    }

    @Override
    public boolean canGoForward() {
        requireMainThread();
        return isBoundToTab() && webView.canGoForward();
    }

    @Override
    public void goForward() {
        requireMainThread();
        if (isBoundToTab() && webView.canGoForward()) {
            webView.goForward();
        }
    }

    @Override
    public void loadUrl(String url) {
        requireMainThread();
        if (!isSupportedUrl(url)) {
            throw new IllegalArgumentException("Only absolute HTTP(S) URLs without credentials are supported.");
        }
        if (!isBoundToTab()) {
            throw new IllegalStateException("The browser tab is no longer available.");
        }
        navigationVersion++;
        pageLoading = true;
        pageLoadFailed = false;
        failPending(
                EvaluationResult.Error.PAGE_CHANGED,
                "The page changed before the script completed."
        );
        webView.loadUrl(url);
    }

    @Override
    public Cancellable evaluateJavaScript(
            String script,
            long timeoutMillis,
            EvaluationCallback callback
    ) {
        Objects.requireNonNull(callback, "callback");
        if (script == null || script.trim().isEmpty()) {
            throw new IllegalArgumentException("The JavaScript source must not be empty.");
        }
        if (script.length() > MAX_SCRIPT_LENGTH) {
            throw new IllegalArgumentException("The JavaScript source exceeds the allowed size.");
        }
        if (timeoutMillis <= 0 || timeoutMillis > MAX_TIMEOUT_MILLIS) {
            throw new IllegalArgumentException("The timeout must be between 1 and 60000 milliseconds.");
        }

        PendingEvaluation evaluation = new PendingEvaluation(
                NEXT_CALL_ID.incrementAndGet(),
                script,
                timeoutMillis,
                callback
        );
        MAIN_HANDLER.post(() -> startEvaluation(evaluation));
        return () -> MAIN_HANDLER.post(() -> complete(
                evaluation,
                EvaluationResult.failure(
                        EvaluationResult.Error.CANCELLED,
                        "The script evaluation was cancelled."
                )
        ));
    }

    public static void onPageStarted(WebView webView, String url) {
        notifyAdapter(webView, adapter -> adapter.handlePageStarted(url));
    }

    public static void onPageFinished(WebView webView, String url) {
        notifyAdapter(webView, adapter -> adapter.handlePageFinished(url));
    }

    public static void onPageLoadFailed(WebView webView, String url) {
        notifyAdapter(webView, adapter -> adapter.handlePageLoadFailed(url));
    }

    public static void onTabClosed(WebView webView) {
        notifyAdapter(webView, WeekBrowserWebViewAdapter::closeOnMainThread);
    }

    @Override
    public void close() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            MAIN_HANDLER.post(this::closeOnMainThread);
            return;
        }
        closeOnMainThread();
    }

    private void startEvaluation(PendingEvaluation evaluation) {
        if (evaluation.completed) {
            return;
        }
        if (!isBoundToTab()) {
            complete(evaluation, EvaluationResult.failure(
                    EvaluationResult.Error.TAB_CLOSED,
                    "The browser tab is no longer available."
            ));
            return;
        }
        if (pageLoading) {
            complete(evaluation, EvaluationResult.failure(
                    EvaluationResult.Error.PAGE_NOT_READY,
                    "Wait for the current page to finish loading."
            ));
            return;
        }

        String pageUrl = webView.getUrl();
        if (!isSupportedUrl(pageUrl)) {
            complete(evaluation, EvaluationResult.failure(
                    EvaluationResult.Error.UNSUPPORTED_URL,
                    "JavaScript is restricted to HTTP(S) pages."
            ));
            return;
        }

        evaluation.navigationVersion = navigationVersion;
        evaluation.pageUrl = pageUrl;
        pendingEvaluations.put(evaluation.id, evaluation);
        evaluation.timeout = () -> complete(
                evaluation,
                EvaluationResult.failure(
                        EvaluationResult.Error.TIMED_OUT,
                        "The page did not return a result before the timeout."
                )
        );
        MAIN_HANDLER.postDelayed(evaluation.timeout, evaluation.timeoutMillis);
        webView.evaluateJavascript(wrapScript(evaluation.script), rawResult -> {
            if (evaluation.completed) {
                return;
            }
            if (!isBoundToTab()) {
                complete(evaluation, EvaluationResult.failure(
                        EvaluationResult.Error.TAB_CLOSED,
                        "The browser tab was closed before the script completed."
                ));
                return;
            }
            if (evaluation.navigationVersion != navigationVersion
                    || !evaluation.pageUrl.equals(webView.getUrl())) {
                complete(evaluation, EvaluationResult.failure(
                        EvaluationResult.Error.PAGE_CHANGED,
                        "The page changed before the script completed."
                ));
                return;
            }
            complete(evaluation, parseResult(rawResult));
        });
    }

    private EvaluationResult parseResult(String rawResult) {
        if (rawResult == null) {
            return EvaluationResult.failure(
                    EvaluationResult.Error.INVALID_RESULT,
                    "The WebView returned no JavaScript result."
            );
        }
        try {
            JSONObject result = new JSONObject(rawResult);
            if (!result.optBoolean("ok")) {
                return EvaluationResult.failure(
                        EvaluationResult.Error.JAVASCRIPT_ERROR,
                        result.optString("error", "JavaScript execution failed.")
                );
            }
            return EvaluationResult.success(result.opt("value"));
        } catch (JSONException error) {
            return EvaluationResult.failure(
                    EvaluationResult.Error.INVALID_RESULT,
                    "The WebView returned an invalid JavaScript result."
            );
        }
    }

    private String wrapScript(String script) {
        return "(function(){try{var value=(0,eval)(" + JSONObject.quote(script) + ");"
                + "return {ok:true,value:value===undefined?null:value};"
                + "}catch(error){return {ok:false,error:String(error&&error.message||error)};}})()";
    }

    private void handlePageStarted(String url) {
        if (closed) {
            return;
        }
        navigationVersion++;
        pageLoading = true;
        pageLoadFailed = false;
        failPending(
                EvaluationResult.Error.PAGE_CHANGED,
                "The page changed before the script completed."
        );
        for (NavigationListener listener : new ArrayList<>(navigationListeners)) {
            listener.onPageStarted(url);
        }
    }

    private void handlePageFinished(String url) {
        if (!closed
                && !pageLoadFailed
                && isBoundToTab()
                && Objects.equals(url, webView.getUrl())) {
            pageLoading = false;
            for (NavigationListener listener : new ArrayList<>(navigationListeners)) {
                listener.onPageFinished(url);
            }
        }
    }

    private void handlePageLoadFailed(String url) {
        if (!closed && isBoundToTab() && Objects.equals(url, webView.getUrl())) {
            pageLoadFailed = true;
            pageLoading = true;
            failPending(
                    EvaluationResult.Error.PAGE_NOT_READY,
                    "The page failed to load; reload or navigate before running JavaScript."
            );
            for (NavigationListener listener : new ArrayList<>(navigationListeners)) {
                listener.onPageLoadFailed(url);
            }
        }
    }

    private void failPending(EvaluationResult.Error error, String detail) {
        for (PendingEvaluation evaluation : new ArrayList<>(pendingEvaluations.values())) {
            complete(evaluation, EvaluationResult.failure(error, detail));
        }
    }

    private void complete(PendingEvaluation evaluation, EvaluationResult result) {
        if (evaluation.completed) {
            return;
        }
        evaluation.completed = true;
        if (evaluation.timeout != null) {
            MAIN_HANDLER.removeCallbacks(evaluation.timeout);
        }
        pendingEvaluations.remove(evaluation.id);
        evaluation.callback.onComplete(result);
    }

    private void closeOnMainThread() {
        if (closed) {
            return;
        }
        closed = true;
        synchronized (ACTIVE_ADAPTERS) {
            WeakReference<WeekBrowserWebViewAdapter> reference = ACTIVE_ADAPTERS.get(webView);
            if (reference != null && reference.get() == this) {
                ACTIVE_ADAPTERS.remove(webView);
            }
        }
        failPending(
                EvaluationResult.Error.TAB_CLOSED,
                "The browser session was closed."
        );
        navigationListeners.clear();
    }

    private boolean isBoundToTab() {
        Object registeredTag = webView.getTag();
        return !closed
                && registeredTag != null
                && tabId.equals(String.valueOf(registeredTag))
                && ResourceManager.getInstance().getWebViewForTab(tabId) == webView;
    }

    private static boolean isSupportedUrl(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        Uri uri = Uri.parse(value);
        String scheme = uri.getScheme();
        return uri.getHost() != null
                && uri.getUserInfo() == null
                && ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme));
    }

    private static void notifyAdapter(
            WebView webView,
            AdapterAction action
    ) {
        if (webView == null) {
            return;
        }
        WeekBrowserWebViewAdapter adapter;
        synchronized (ACTIVE_ADAPTERS) {
            WeakReference<WeekBrowserWebViewAdapter> reference = ACTIVE_ADAPTERS.get(webView);
            adapter = reference == null ? null : reference.get();
        }
        if (adapter != null) {
            WeekBrowserWebViewAdapter activeAdapter = adapter;
            if (Looper.myLooper() == Looper.getMainLooper()) {
                action.run(activeAdapter);
            } else {
                MAIN_HANDLER.post(() -> action.run(activeAdapter));
            }
        }
    }

    private static void requireMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("Browser session state must be accessed on the main thread.");
        }
    }

    private interface AdapterAction {
        void run(WeekBrowserWebViewAdapter adapter);
    }

    private static final class PendingEvaluation {
        private final long id;
        private final String script;
        private final long timeoutMillis;
        private final EvaluationCallback callback;
        private long navigationVersion;
        private String pageUrl;
        private Runnable timeout;
        private boolean completed;

        private PendingEvaluation(
                long id,
                String script,
                long timeoutMillis,
                EvaluationCallback callback
        ) {
            this.id = id;
            this.script = script;
            this.timeoutMillis = timeoutMillis;
            this.callback = callback;
        }
    }
}
