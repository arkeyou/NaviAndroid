package com.a525team.weekbrowser.navi.model;

import org.json.JSONObject;

public final class SearchResponse {
    private final String bffMeta;
    private final String error;
    private final String debugMsg;
    private final DirectSearchResult directSearchResult;
    private final String searchInfo;

    public SearchResponse(String bffMeta, String error, String debugMsg,
                          DirectSearchResult directSearchResult, String searchInfo) {
        this.bffMeta = bffMeta;
        this.error = error;
        this.debugMsg = debugMsg;
        this.directSearchResult = directSearchResult != null ? directSearchResult : new DirectSearchResult(0, new Item(0, 0));
        this.searchInfo = searchInfo;
    }

    public static SearchResponse fromJson(JSONObject json) {
        if (json == null) {
            return new SearchResponse(null, null, null, null, null);
        }
        String bffMeta = json.isNull("bff_meta") ? null : json.optString("bff_meta", null);
        String error = json.isNull("error") ? null : json.optString("error", null);
        String debugMsg = json.isNull("debug_msg") ? null : json.optString("debug_msg", null);
        DirectSearchResult directSearchResult = DirectSearchResult.fromJson(json.optJSONObject("direct_search_result"));
        String searchInfo = json.isNull("search_info") ? null : json.optString("search_info", null);

        return new SearchResponse(bffMeta, error, debugMsg, directSearchResult, searchInfo);
    }

    public String getBffMeta() {
        return bffMeta;
    }

    public String getError() {
        return error;
    }

    public String getDebugMsg() {
        return debugMsg;
    }

    public DirectSearchResult getDirectSearchResult() {
        return directSearchResult;
    }

    public String getSearchInfo() {
        return searchInfo;
    }

    public static final class DirectSearchResult {
        private final int resultType;
        private final Item item;

        public DirectSearchResult(int resultType, Item item) {
            this.resultType = resultType;
            this.item = item != null ? item : new Item(0, 0);
        }

        public static DirectSearchResult fromJson(JSONObject json) {
            if (json == null) return new DirectSearchResult(0, new Item(0, 0));
            int resultType = json.optInt("result_type", 0);
            Item item = Item.fromJson(json.optJSONObject("item"));
            return new DirectSearchResult(resultType, item);
        }

        public int getResultType() {
            return resultType;
        }

        public Item getItem() {
            return item;
        }
    }

    public static final class Item {
        private final long itemID;
        private final long shopID;

        public Item(long itemID, long shopID) {
            this.itemID = itemID;
            this.shopID = shopID;
        }

        public static Item fromJson(JSONObject json) {
            if (json == null) return new Item(0, 0);
            long itemID = json.optLong("item_id", 0);
            long shopID = json.optLong("shop_id", 0);
            return new Item(itemID, shopID);
        }

        public long getItemID() {
            return itemID;
        }

        public long getShopID() {
            return shopID;
        }
    }
}
