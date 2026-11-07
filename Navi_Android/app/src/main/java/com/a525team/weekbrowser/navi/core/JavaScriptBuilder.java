package com.a525team.weekbrowser.navi.core;

import com.a525team.weekbrowser.navi.model.AutomationScript;

public final class JavaScriptBuilder {

    private JavaScriptBuilder() {}

    public static class BuilderException extends Exception {
        public BuilderException(String message) {
            super(message);
        }
    }

    public static String generate(AutomationScript script) throws BuilderException {
        if (script == null) {
            throw new BuilderException("AutomationScript não pode ser nulo.");
        }

        String type = script.getType();
        if (type == null) {
            type = "";
        }

        switch (type) {
            case "confirm": {
                String message = script.getMessage();
                if (message == null || message.trim().isEmpty()) {
                    throw new BuilderException("Parâmetro obrigatório não informado: message");
                }
                String escapedMessage = escapeForJsString(message);
                return "confirm(\"" + escapedMessage + "\") ? true : false;";
            }

            case "exists": {
                String selector = script.getSelector();
                if (selector == null || selector.trim().isEmpty()) {
                    throw new BuilderException("Parâmetro obrigatório não informado: selector");
                }
                String escapedSelector = escapeForJsString(selector);
                StringBuilder js = new StringBuilder();
                js.append("document.querySelectorAll(\"").append(escapedSelector).append("\").length > 0");
                String customSelector = script.getCustomSelector();
                if (customSelector != null && !customSelector.trim().isEmpty()) {
                    js.append(" || ").append(customSelector);
                }
                return js.toString();
            }

            case "click": {
                String selector = script.getSelector();
                if (selector == null || selector.trim().isEmpty()) {
                    throw new BuilderException("Parâmetro obrigatório não informado: selector");
                }
                String escapedSelector = escapeForJsString(selector);
                StringBuilder js = new StringBuilder();
                js.append("(document.querySelectorAll(\"").append(escapedSelector).append("\")).forEach(button => button.click());");
                String customSelector = script.getCustomSelector();
                if (customSelector != null && !customSelector.trim().isEmpty()) {
                    js.append(customSelector).append(";");
                }
                return js.toString();
            }

            default:
                throw new BuilderException("Tipo de script não suportado: " + type);
        }
    }

    private static String escapeForJsString(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r");
    }
}
