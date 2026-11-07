package com.a525team.weekbrowser;

import android.widget.LinearLayout;
import android.webkit.WebView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResourceManager {
    private static final ResourceManager instance = new ResourceManager();
    
    private final Map<String, List<Map<String, Object>>> tabResourcesMap = new ConcurrentHashMap<>();
    private final Map<String, String> tabSslErrorsMap = new ConcurrentHashMap<>();
    private final Map<String, Boolean> tabBlackAdMap = new ConcurrentHashMap<>();
    private final Map<String, WebView> tabWebViewMap = new ConcurrentHashMap<>();
    private final Map<String, LinearLayout> tabContainerMap = new ConcurrentHashMap<>();

    private ResourceManager() {}

    public static ResourceManager getInstance() {
        return instance;
    }

    private String getSafeKey(Object tag) {
        if (tag == null) return "";
        return String.valueOf(tag).replace("ll", "").trim();
    }

    // --- WebView ---
    public void setWebViewForTab(Object tag, WebView webView) {
        String safeKey = getSafeKey(tag);
        if (safeKey.isEmpty()) return;
        if (webView == null) {
            tabWebViewMap.remove(safeKey);
        } else {
            tabWebViewMap.put(safeKey, webView);
        }
    }

    public WebView getWebViewForTab(Object tag) {
        String safeKey = getSafeKey(tag);
        if (safeKey.isEmpty()) return null;
        return tabWebViewMap.get(safeKey);
    }

    // --- Resources ---
    public List<Map<String, Object>> getOrCreateResourcesForTab(Object tag) {
        String safeKey = getSafeKey(tag);
        if (safeKey.isEmpty()) return new ArrayList<>();
        
        List<Map<String, Object>> resources = tabResourcesMap.get(safeKey);
        if (resources == null) {
            resources = Collections.synchronizedList(new ArrayList<Map<String, Object>>());
            tabResourcesMap.put(safeKey, resources);
        }
        return resources;
    }

    public void clearResourcesForTab(Object tag) {
        String safeKey = getSafeKey(tag);
        if (safeKey.isEmpty()) return;
        
        List<Map<String, Object>> resources = tabResourcesMap.get(safeKey);
        if (resources != null) {
            resources.clear();
        }
    }

    // --- SSL Errors ---
    public void setSslErrorForTab(Object tag, String errorMessage) {
        String safeKey = getSafeKey(tag);
        if (safeKey.isEmpty()) return;
        
        if (errorMessage == null) {
            tabSslErrorsMap.remove(safeKey);
        } else {
            tabSslErrorsMap.put(safeKey, errorMessage);
        }
    }

    public String getSslErrorForTab(Object tag) {
        String safeKey = getSafeKey(tag);
        if (safeKey.isEmpty()) return null;
        return tabSslErrorsMap.get(safeKey);
    }

    public void clearSslErrorForTab(Object tag) {
        String safeKey = getSafeKey(tag);
        if (safeKey.isEmpty()) return;
        tabSslErrorsMap.remove(safeKey);
    }
    
    // --- Containers ---
    public void setContainerForTab(Object tag, LinearLayout container) {
        String safeKey = getSafeKey(tag);
        if (safeKey.isEmpty()) return;
        if (container == null) tabContainerMap.remove(safeKey);
        else tabContainerMap.put(safeKey, container);
    }

    public LinearLayout getContainerForTab(Object tag) {
        String safeKey = getSafeKey(tag);
        if (safeKey.isEmpty()) return null;
        return tabContainerMap.get(safeKey);
    }
    
    // --- Ad Blocking ---
    public void setAdForTab(Object tag, boolean need) {
        String safeKey = getSafeKey(tag);
        if (safeKey.isEmpty()) return;
        tabBlackAdMap.put(safeKey, need);
    }

    public boolean getAdForTab(Object tag) {
        String safeKey = getSafeKey(tag);
        if (safeKey.isEmpty()) return false;
        Boolean res = tabBlackAdMap.get(safeKey);
        return res != null ? res : false;
    }

    public void clearAdForTab(Object tag) {
        String safeKey = getSafeKey(tag);
        if (safeKey.isEmpty()) return;
        tabBlackAdMap.remove(safeKey);
    }

    public void removeTab(Object tag) {
        String safeKey = getSafeKey(tag);
        if (safeKey.isEmpty()) return;
        tabResourcesMap.remove(safeKey);
        tabSslErrorsMap.remove(safeKey);
        tabBlackAdMap.remove(safeKey);
        tabWebViewMap.remove(safeKey);
        tabContainerMap.remove(safeKey);
    }
}
