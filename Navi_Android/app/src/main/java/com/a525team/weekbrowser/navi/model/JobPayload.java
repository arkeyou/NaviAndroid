package com.a525team.weekbrowser.navi.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public final class JobPayload {
    private final String codigo;
    private long param1; // shopID
    private long param2; // itemID
    private String url;
    private String username;

    public JobPayload(String codigo, long param1, long param2, String url, String username) {
        this.codigo = codigo != null ? codigo : "";
        this.param1 = param1;
        this.param2 = param2;
        this.url = url != null ? url : "";
        this.username = username != null ? username : "";
    }

    public static JobPayload fromJson(JSONObject json) {
        if (json == null) {
            return new JobPayload("", 0, 0, "", "");
        }
        String codigo = json.optString("codigo", "");
        long param1 = json.optLong("param1", 0);
        long param2 = json.optLong("param2", 0);
        String url = json.optString("url", "");
        String username = json.optString("username", "");
        return new JobPayload(codigo, param1, param2, url, username);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("codigo", codigo);
        json.put("param1", param1);
        json.put("param2", param2);
        json.put("url", url);
        json.put("username", username);
        return json;
    }

    public String getCodigo() {
        return codigo;
    }

    public long getParam1() {
        return param1;
    }

    public void setParam1(long param1) {
        this.param1 = param1;
    }

    public long getParam2() {
        return param2;
    }

    public void setParam2(long param2) {
        this.param2 = param2;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url != null ? url : "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username != null ? username : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobPayload)) return false;
        JobPayload that = (JobPayload) o;
        return param1 == that.param1 &&
                param2 == that.param2 &&
                Objects.equals(codigo, that.codigo) &&
                Objects.equals(url, that.url) &&
                Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, param1, param2, url, username);
    }

    @Override
    public String toString() {
        return "JobPayload{" +
                "codigo='" + codigo + '\'' +
                ", param1=" + param1 +
                ", param2=" + param2 +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
