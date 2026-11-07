package com.a525team.weekbrowser.navi.browser;

public interface NaviBrowserSession extends AutoCloseable {
    String getTabId();

    String getCurrentUrl();

    boolean isPageLoading();

    Cancellable observeNavigation(NavigationListener listener);

    boolean canGoBack();

    void goBack();

    boolean canGoForward();

    void goForward();

    void loadUrl(String url);

    Cancellable evaluateJavaScript(
            String script,
            long timeoutMillis,
            EvaluationCallback callback
    );

    @Override
    void close();

    interface Cancellable {
        void cancel();
    }

    interface EvaluationCallback {
        void onComplete(EvaluationResult result);
    }

    interface NavigationListener {
        void onPageStarted(String url);

        void onPageFinished(String url);

        void onPageLoadFailed(String url);
    }

    final class EvaluationResult {
        public enum Error {
            CANCELLED,
            TIMED_OUT,
            PAGE_CHANGED,
            TAB_CLOSED,
            PAGE_NOT_READY,
            UNSUPPORTED_URL,
            JAVASCRIPT_ERROR,
            INVALID_RESULT
        }

        private final Object value;
        private final Error error;
        private final String detail;

        private EvaluationResult(Object value, Error error, String detail) {
            this.value = value;
            this.error = error;
            this.detail = detail;
        }

        public static EvaluationResult success(Object value) {
            return new EvaluationResult(value, null, null);
        }

        public static EvaluationResult failure(Error error, String detail) {
            return new EvaluationResult(null, error, detail);
        }

        public boolean isSuccess() {
            return error == null;
        }

        public Object getValue() {
            return value;
        }

        public Error getError() {
            return error;
        }

        public String getDetail() {
            return detail;
        }
    }
}
