package com.a525team.weekbrowser.navi.model;

public abstract class ActionEvent {
    private ActionEvent() {}

    public static final class EnqueuePage extends ActionEvent {
        private final String codigo;
        private final String url;
        private final String username;
        private final String script;
        private final String scriptVerify;
        private final String script2;
        private final String scriptVerify2;

        public EnqueuePage(String codigo, String url, String username,
                           String script, String scriptVerify,
                           String script2, String scriptVerify2) {
            this.codigo = codigo != null ? codigo : "";
            this.url = url != null ? url : "";
            this.username = username != null ? username : "";
            this.script = script != null ? script : "";
            this.scriptVerify = scriptVerify != null ? scriptVerify : "";
            this.script2 = script2 != null ? script2 : "";
            this.scriptVerify2 = scriptVerify2 != null ? scriptVerify2 : "";
        }

        public String getCodigo() {
            return codigo;
        }

        public String getUrl() {
            return url;
        }

        public String getUsername() {
            return username;
        }

        public String getScript() {
            return script;
        }

        public String getScriptVerify() {
            return scriptVerify;
        }

        public String getScript2() {
            return script2;
        }

        public String getScriptVerify2() {
            return scriptVerify2;
        }

        @Override
        public String toString() {
            return "EnqueuePage{" +
                    "codigo='" + codigo + '\'' +
                    ", url='" + url + '\'' +
                    ", username='" + username + '\'' +
                    '}';
        }
    }

    public static final class SendMsg extends ActionEvent {
        private final String message;
        private final boolean stop;
        private final boolean lineBreak;

        public SendMsg(String message, boolean stop, boolean lineBreak) {
            this.message = message != null ? message : "";
            this.stop = stop;
            this.lineBreak = lineBreak;
        }

        public SendMsg(String message) {
            this(message, true, true);
        }

        public String getMessage() {
            return message;
        }

        public boolean isStop() {
            return stop;
        }

        public boolean isLineBreak() {
            return lineBreak;
        }

        @Override
        public String toString() {
            return "SendMsg{" +
                    "message='" + message + '\'' +
                    ", stop=" + stop +
                    ", lineBreak=" + lineBreak +
                    '}';
        }
    }

    public static final class OpenPage extends ActionEvent {
        private final String url;

        public OpenPage(String url) {
            this.url = url != null ? url : "";
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return "OpenPage{" +
                    "url='" + url + '\'' +
                    '}';
        }
    }
}
