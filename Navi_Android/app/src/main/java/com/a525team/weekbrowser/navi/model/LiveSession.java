package com.a525team.weekbrowser.navi.model;

import org.json.JSONObject;

public final class LiveSession {
    private final long sessionId;
    private final String title;
    private final int status;

    public LiveSession(long sessionId, String title, int status) {
        this.sessionId = sessionId;
        this.title = title != null ? title : "";
        this.status = status;
    }

    public static LiveSession fromJson(JSONObject json) {
        if (json == null) return new LiveSession(0, "", 0);
        long sessionId = json.optLong("sessionId", 0);
        String title = json.optString("title", "");
        int status = json.optInt("status", 0);
        return new LiveSession(sessionId, title, status);
    }

    public long getSessionId() {
        return sessionId;
    }

    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }
}
