package com.a525team.weekbrowser.navi.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public final class NaviConfig {
    // Monitor
    private String urlMonitor = "";
    private String triggerMonitor = "";

    // Flow
    private String urlFlow = "";

    // Action
    private String urlAction = "";

    // Browser scripts
    private AutomationScript mainVerify = new AutomationScript();
    private AutomationScript mainAction = new AutomationScript();
    private AutomationScript secondaryVerify = null;
    private AutomationScript secondaryAction = null;

    // Core / Session
    private String urlSessionInfo = null;
    private String urlSessionIdentifier = null;

    public NaviConfig() {
    }

    public static NaviConfig fromJsonString(String jsonString) throws JSONException {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            throw new JSONException("Configuração JSON vazia");
        }
        return fromJson(new JSONObject(jsonString));
    }

    public static NaviConfig fromJson(JSONObject json) {
        NaviConfig config = new NaviConfig();
        if (json == null) {
            return config;
        }

        config.urlMonitor = json.optString("urlMonitor", "");
        config.triggerMonitor = json.optString("triggerMonitor", "");
        config.urlFlow = json.isNull("urlFlow") ? null : json.optString("urlFlow", "");
        config.urlAction = json.optString("urlAction", "");

        if (json.has("mainVerify") && !json.isNull("mainVerify")) {
            config.mainVerify = AutomationScript.fromJson(json.optJSONObject("mainVerify"));
        }
        if (json.has("mainAction") && !json.isNull("mainAction")) {
            config.mainAction = AutomationScript.fromJson(json.optJSONObject("mainAction"));
        }
        if (json.has("secondaryVerify") && !json.isNull("secondaryVerify")) {
            config.secondaryVerify = AutomationScript.fromJson(json.optJSONObject("secondaryVerify"));
        }
        if (json.has("secondaryAction") && !json.isNull("secondaryAction")) {
            config.secondaryAction = AutomationScript.fromJson(json.optJSONObject("secondaryAction"));
        }

        config.urlSessionInfo = json.isNull("urlSessionInfo") ? null : json.optString("urlSessionInfo", null);
        config.urlSessionIdentifier = json.isNull("urlSessionIdentifier") ? null : json.optString("urlSessionIdentifier", null);

        return config;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("urlMonitor", urlMonitor);
        json.put("triggerMonitor", triggerMonitor);
        if (urlFlow != null) json.put("urlFlow", urlFlow);
        json.put("urlAction", urlAction);

        if (mainVerify != null) json.put("mainVerify", mainVerify.toJson());
        if (mainAction != null) json.put("mainAction", mainAction.toJson());
        if (secondaryVerify != null) json.put("secondaryVerify", secondaryVerify.toJson());
        if (secondaryAction != null) json.put("secondaryAction", secondaryAction.toJson());

        if (urlSessionInfo != null) json.put("urlSessionInfo", urlSessionInfo);
        if (urlSessionIdentifier != null) json.put("urlSessionIdentifier", urlSessionIdentifier);

        return json;
    }

    public String getUrlMonitor() {
        return urlMonitor;
    }

    public void setUrlMonitor(String urlMonitor) {
        this.urlMonitor = urlMonitor != null ? urlMonitor : "";
    }

    public String getTriggerMonitor() {
        return triggerMonitor;
    }

    public void setTriggerMonitor(String triggerMonitor) {
        this.triggerMonitor = triggerMonitor != null ? triggerMonitor : "";
    }

    public String getUrlFlow() {
        return urlFlow;
    }

    public void setUrlFlow(String urlFlow) {
        this.urlFlow = urlFlow;
    }

    public String getUrlAction() {
        return urlAction;
    }

    public void setUrlAction(String urlAction) {
        this.urlAction = urlAction != null ? urlAction : "";
    }

    public AutomationScript getMainVerify() {
        return mainVerify;
    }

    public void setMainVerify(AutomationScript mainVerify) {
        this.mainVerify = mainVerify != null ? mainVerify : new AutomationScript();
    }

    public AutomationScript getMainAction() {
        return mainAction;
    }

    public void setMainAction(AutomationScript mainAction) {
        this.mainAction = mainAction != null ? mainAction : new AutomationScript();
    }

    public AutomationScript getSecondaryVerify() {
        return secondaryVerify;
    }

    public void setSecondaryVerify(AutomationScript secondaryVerify) {
        this.secondaryVerify = secondaryVerify;
    }

    public AutomationScript getSecondaryAction() {
        return secondaryAction;
    }

    public void setSecondaryAction(AutomationScript secondaryAction) {
        this.secondaryAction = secondaryAction;
    }

    public String getUrlSessionInfo() {
        return urlSessionInfo;
    }

    public void setUrlSessionInfo(String urlSessionInfo) {
        this.urlSessionInfo = urlSessionInfo;
    }

    public String getUrlSessionIdentifier() {
        return urlSessionIdentifier;
    }

    public void setUrlSessionIdentifier(String urlSessionIdentifier) {
        this.urlSessionIdentifier = urlSessionIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NaviConfig)) return false;
        NaviConfig that = (NaviConfig) o;
        return Objects.equals(urlMonitor, that.urlMonitor) &&
                Objects.equals(triggerMonitor, that.triggerMonitor) &&
                Objects.equals(urlFlow, that.urlFlow) &&
                Objects.equals(urlAction, that.urlAction) &&
                Objects.equals(mainVerify, that.mainVerify) &&
                Objects.equals(mainAction, that.mainAction) &&
                Objects.equals(secondaryVerify, that.secondaryVerify) &&
                Objects.equals(secondaryAction, that.secondaryAction) &&
                Objects.equals(urlSessionInfo, that.urlSessionInfo) &&
                Objects.equals(urlSessionIdentifier, that.urlSessionIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlMonitor, triggerMonitor, urlFlow, urlAction,
                mainVerify, mainAction, secondaryVerify, secondaryAction,
                urlSessionInfo, urlSessionIdentifier);
    }

    @Override
    public String toString() {
        return "NaviConfig{" +
                "urlMonitor='" + urlMonitor + '\'' +
                ", triggerMonitor='" + triggerMonitor + '\'' +
                ", urlFlow='" + urlFlow + '\'' +
                ", urlAction='" + urlAction + '\'' +
                ", mainVerify=" + mainVerify +
                ", mainAction=" + mainAction +
                ", secondaryVerify=" + secondaryVerify +
                ", secondaryAction=" + secondaryAction +
                ", urlSessionInfo='" + urlSessionInfo + '\'' +
                ", urlSessionIdentifier='" + urlSessionIdentifier + '\'' +
                '}';
    }
}
