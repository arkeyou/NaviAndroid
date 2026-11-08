package com.a525team.weekbrowser.navi.core;

/** Supplies cookies from the browser's cookie store for a specific request URL. */
public interface BrowserCookieProvider {
    String getCookieHeader(String url);
}
