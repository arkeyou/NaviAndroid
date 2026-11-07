package com.a525team.weekbrowser.navi.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class LiveHistoryData {
    private final int page;
    private final int pageSize;
    private final int total;
    private final List<LiveSession> list;
    private final int totalPage;

    public LiveHistoryData(int page, int pageSize, int total, List<LiveSession> list, int totalPage) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list != null ? list : new ArrayList<LiveSession>();
        this.totalPage = totalPage;
    }

    public static LiveHistoryData fromJson(JSONObject json) {
        if (json == null) return new LiveHistoryData(0, 0, 0, null, 0);
        int page = json.optInt("page", 0);
        int pageSize = json.optInt("pageSize", 0);
        int total = json.optInt("total", 0);
        int totalPage = json.optInt("totalPage", 0);

        List<LiveSession> list = new ArrayList<>();
        JSONArray arr = json.optJSONArray("list");
        if (arr != null) {
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.optJSONObject(i);
                if (obj != null) {
                    list.add(LiveSession.fromJson(obj));
                }
            }
        }
        return new LiveHistoryData(page, pageSize, total, list, totalPage);
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotal() {
        return total;
    }

    public List<LiveSession> getList() {
        return list;
    }

    public int getTotalPage() {
        return totalPage;
    }
}
