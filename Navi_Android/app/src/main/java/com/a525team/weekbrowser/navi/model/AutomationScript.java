package com.a525team.weekbrowser.navi.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public final class AutomationScript {
    private String type;
    private String message;
    private String selector;
    private String customSelector;

    public AutomationScript() {
        this.type = "";
    }

    public AutomationScript(String type, String selector, String message, String customSelector) {
        this.type = type != null ? type : "";
        this.selector = selector;
        this.message = message;
        this.customSelector = customSelector;
    }

    public static AutomationScript fromJson(JSONObject json) {
        if (json == null) {
            return new AutomationScript();
        }
        String type = json.optString("type", "");
        String selector = json.isNull("selector") ? null : json.optString("selector", null);
        String message = json.isNull("message") ? null : json.optString("message", null);
        String customSelector = json.isNull("customSelector") ? null : json.optString("customSelector", null);
        return new AutomationScript(type, selector, message, customSelector);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("type", type);
        if (selector != null) json.put("selector", selector);
        if (message != null) json.put("message", message);
        if (customSelector != null) json.put("customSelector", customSelector);
        return json;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type != null ? type : "";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getCustomSelector() {
        return customSelector;
    }

    public void setCustomSelector(String customSelector) {
        this.customSelector = customSelector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AutomationScript)) return false;
        AutomationScript that = (AutomationScript) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(message, that.message) &&
                Objects.equals(selector, that.selector) &&
                Objects.equals(customSelector, that.customSelector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, message, selector, customSelector);
    }

    @Override
    public String toString() {
        return "AutomationScript{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", selector='" + selector + '\'' +
                ", customSelector='" + customSelector + '\'' +
                '}';
    }
}
