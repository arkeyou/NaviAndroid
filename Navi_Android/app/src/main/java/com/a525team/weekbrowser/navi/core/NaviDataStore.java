package com.a525team.weekbrowser.navi.core;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class NaviDataStore {

    private static final String PREFS_NAME = "navi_settings";
    private static final String KEY_NAVI_CONFIG = "saved_navi_config";
    private static final String LOG_FILE = "navi_log.txt";
    private static final String PROCESSED_FILE = "navi_processed.txt";

    // Chaves de Intervalos de Automação (equivalentes ao Navi_iOS SettingsView)
    public static final String KEY_MONITOR_ONLINE_INTERVAL = "interval_monitor_online";
    public static final String KEY_MONITOR_INTERVAL = "interval_monitor";
    public static final String KEY_IDS_WAIT_INTERVAL = "interval_ids_wait";
    public static final String KEY_LIKE_WAIT_INTERVAL = "interval_like_wait";
    public static final String KEY_PAGE_WAIT_INTERVAL = "interval_page_wait";

    private final Context context;

    public NaviDataStore(Context context) {
        this.context = context.getApplicationContext();
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveConfig(String configJson) {
        getPrefs().edit()
                .putString(KEY_NAVI_CONFIG, configJson)
                .apply();
    }

    public String loadConfig() {
        return getPrefs().getString(KEY_NAVI_CONFIG, getDefaultConfig());
    }

    // --- Intervalos de Automação (segundos) ---

    public double getMonitorLiveOnlineInterval() {
        return getPrefs().getFloat(KEY_MONITOR_ONLINE_INTERVAL, 30.0f);
    }

    public void setMonitorLiveOnlineInterval(double seconds) {
        getPrefs().edit().putFloat(KEY_MONITOR_ONLINE_INTERVAL, (float) seconds).apply();
    }

    public double getMonitorInterval() {
        return getPrefs().getFloat(KEY_MONITOR_INTERVAL, 5.0f);
    }

    public void setMonitorInterval(double seconds) {
        getPrefs().edit().putFloat(KEY_MONITOR_INTERVAL, (float) seconds).apply();
    }

    public double getIdsWaitInterval() {
        return getPrefs().getFloat(KEY_IDS_WAIT_INTERVAL, 2.0f);
    }

    public void setIdsWaitInterval(double seconds) {
        getPrefs().edit().putFloat(KEY_IDS_WAIT_INTERVAL, (float) seconds).apply();
    }

    public double getLikeWaitInterval() {
        return getPrefs().getFloat(KEY_LIKE_WAIT_INTERVAL, 1.5f);
    }

    public void setLikeWaitInterval(double seconds) {
        getPrefs().edit().putFloat(KEY_LIKE_WAIT_INTERVAL, (float) seconds).apply();
    }

    public double getPageWaitInterval() {
        return getPrefs().getFloat(KEY_PAGE_WAIT_INTERVAL, 15.0f);
    }

    public void setPageWaitInterval(double seconds) {
        getPrefs().edit().putFloat(KEY_PAGE_WAIT_INTERVAL, (float) seconds).apply();
    }

    // --- Logs e Processados ---

    public synchronized void appendLog(String message, boolean lineBreak) {
        String entry = (lineBreak ? "\n" : "") + stripLegacyTimestamps(message);
        writeToFile(LOG_FILE, entry, true);
    }

    public synchronized void appendProcessed(String text) {
        writeToFile(PROCESSED_FILE, text + "\n", true);
    }

    public synchronized String readLog() {
        return stripLegacyTimestamps(readFromFile(LOG_FILE));
    }

    private String stripLegacyTimestamps(String text) {
        return text == null ? "" : text.replaceAll("(?m)^\\[\\d{2}:\\d{2}:\\d{2}\\]\\s*", "");
    }

    public synchronized String readProcessed() {
        return readFromFile(PROCESSED_FILE);
    }

    public synchronized void clearLog() {
        File file = new File(context.getFilesDir(), LOG_FILE);
        if (file.exists()) file.delete();
    }

    public synchronized void clearProcessed() {
        File file = new File(context.getFilesDir(), PROCESSED_FILE);
        if (file.exists()) file.delete();
    }

    private void writeToFile(String filename, String content, boolean append) {
        try {
            File file = new File(context.getFilesDir(), filename);
            FileOutputStream fos = new FileOutputStream(file, append);
            fos.write(content.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readFromFile(String filename) {
        try {
            File file = new File(context.getFilesDir(), filename);
            if (!file.exists()) return "";
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getDefaultConfig() {
        return "{\n"
                + "  \"urlMonitor\": \"https://opensky-network.org/api/states/all?lamin=-22.95&lomin=-51.30&lamax=-14.10&lomax=-39.80\",\n"
                + "  \"triggerMonitor\": \"[A-Z]{3}[0-9]{4}\",\n"
                + "  \"urlAction\": \"https://www.flightaware.com/live/flight/\",\n"
                + "  \"mainVerify\": {\n"
                + "    \"type\": \"exists\",\n"
                + "    \"selector\": \"div[class='flightPageFriendlyIdentLbl']\"\n"
                + "  },\n"
                + "  \"mainAction\": {\n"
                + "    \"type\": \"confirm\",\n"
                + "    \"message\": \"Ir para o proximo?\"\n"
                + "  }\n"
                + "}";
    }
}
