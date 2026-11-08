package com.a525team.weekbrowser.navi.browser;

import android.webkit.CookieManager;

import com.a525team.weekbrowser.navi.core.BrowserCookieProvider;

import java.net.URL;

/** Reads the cookies used by WeekBrowser WebViews, scoped to the request origin. */
public final class WeekBrowserCookieProvider implements BrowserCookieProvider {
    @Override
    public String getCookieHeader(String url) {
        if (url == null || url.trim().isEmpty()) return "";
        try {
            String scheme = new URL(url).getProtocol();
            if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
                return "";
            }
            String cookies = CookieManager.getInstance().getCookie(url);
            return cookies != null ? cookies : "";
        } catch (Exception ignored) {
            return "";
        }
    }
}
