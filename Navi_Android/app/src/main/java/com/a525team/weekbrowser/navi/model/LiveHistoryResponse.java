package com.a525team.weekbrowser.navi.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class LiveHistoryResponse {
    private final int code;
    private final String msg;
    private final LiveHistoryData data;

    public LiveHistoryResponse(int code, String msg, LiveHistoryData data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static LiveHistoryResponse fromJson(JSONObject json) {
        if (json == null) return new LiveHistoryResponse(0, null, null);
        int code = json.optInt("code", 0);
        String msg = json.isNull("msg") ? null : json.optString("msg", null);
        LiveHistoryData data = json.has("data") && !json.isNull("data")
                ? LiveHistoryData.fromJson(json.optJSONObject("data"))
                : null;
        return new LiveHistoryResponse(code, msg, data);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public LiveHistoryData getData() {
        return data;
    }
}
