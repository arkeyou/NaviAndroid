package com.a525team.weekbrowser.navi.agent;

import com.a525team.weekbrowser.navi.core.AutomationEventEmitter;
import com.a525team.weekbrowser.navi.core.BrowserCookieProvider;
import com.a525team.weekbrowser.navi.core.JobStore;
import com.a525team.weekbrowser.navi.model.ActionEvent;
import com.a525team.weekbrowser.navi.model.Job;
import com.a525team.weekbrowser.navi.model.JobPayload;
import com.a525team.weekbrowser.navi.model.JobStatus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonitorAgent {

    private final JobStore store;
    private final String urlMonitor;
    private final Pattern triggerMonitor;
    private final String sessionId;
    private final BrowserCookieProvider cookieProvider;
    private final long monitorIntervalMillis;
    private final AutomationEventEmitter emitter;

    private volatile boolean isRunning = false;
    private Thread monitorThread = null;

    public MonitorAgent(
            JobStore store,
            String urlMonitor,
            String triggerMonitorRegex,
            String sessionId,
            BrowserCookieProvider cookieProvider,
            long monitorIntervalMillis,
            AutomationEventEmitter emitter
    ) {
        this.store = store;
        this.urlMonitor = urlMonitor != null ? urlMonitor : "";
        Pattern compiledPattern;
        try {
            compiledPattern = Pattern.compile(triggerMonitorRegex != null ? triggerMonitorRegex : ".*");
        } catch (Exception e) {
            compiledPattern = Pattern.compile(".*");
        }
        this.triggerMonitor = compiledPattern;
        this.sessionId = sessionId != null ? sessionId : "";
        this.cookieProvider = cookieProvider != null ? cookieProvider : url -> "";
        this.monitorIntervalMillis = monitorIntervalMillis > 0 ? monitorIntervalMillis : 5000L;
        this.emitter = emitter;
    }

    public synchronized void start() {
        if (isRunning) return;
        isRunning = true;

        monitorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning && !Thread.currentThread().isInterrupted()) {
                    try {
                        scan();
                        if (emitter != null) {
                            emitter.emit(new ActionEvent.SendMsg("˙", false, false));
                        }
                        Thread.sleep(monitorIntervalMillis);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    } catch (Exception e) {
                        if (emitter != null) {
                            emitter.emit(new ActionEvent.SendMsg("Monitor Agent Error: " + e.getMessage(), false, true));
                        }
                        try {
                            Thread.sleep(monitorIntervalMillis);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                }
            }
        }, "Navi-MonitorAgent-Thread");

        monitorThread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        if (monitorThread != null) {
            monitorThread.interrupt();
            monitorThread = null;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void scan() throws Exception {
        List<JobPayload> apiItems = fetchApiChanges();
        for (JobPayload item : apiItems) {
            Job job = new Job(
                    UUID.randomUUID(),
                    item,
                    JobStatus.added,
                    new Date(),
                    new Date(),
                    null
            );
            store.insert(job);
        }
    }

    private List<JobPayload> fetchApiChanges() throws Exception {
        String effectiveSessionId = (sessionId.isEmpty() ? "0" : sessionId);
        String formattedUrl = formatMonitorUrl(urlMonitor, effectiveSessionId);

        URL url = new URL(formattedUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);
        String cookies = cookieProvider.getCookieHeader(formattedUrl);
        if (cookies != null && !cookies.isEmpty()) {
            conn.setRequestProperty("Cookie", cookies);
        }

        int responseCode = conn.getResponseCode();
        BufferedReader reader;
        if (responseCode >= 200 && responseCode <= 299) {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        } else {
            reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
        }

        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }
        reader.close();

        if (responseCode < 200 || responseCode > 299) {
            throw new Exception("HTTP " + responseCode + ": " + responseBuilder);
        }

        String responseBody = responseBuilder.toString();
        List<JobPayload> listaJobPayload = new ArrayList<>();
        Map<String, String> codigos = new LinkedHashMap<>();

        // Tenta interpretar formato de comentários caso venha empacotado em JSON de comments
        boolean parsedAsComments = false;
        try {
            JSONObject json = new JSONObject(responseBody);
            JSONObject data = json.optJSONObject("data");
            if (data != null && data.has("comments")) {
                JSONArray comments = data.optJSONArray("comments");
                if (comments != null) {
                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject c = comments.optJSONObject(i);
                        if (c != null) {
                            String content = c.optString("content", "");
                            String username = c.optString("username", "");
                            Matcher matcher = triggerMonitor.matcher(content);
                            while (matcher.find()) {
                                String code = matcher.group();
                                String treated = treatCode(code);
                                codigos.put(treated, username);
                            }
                        }
                    }
                    parsedAsComments = true;
                }
            }
        } catch (Exception ignored) {
            parsedAsComments = false;
        }

        if (!parsedAsComments) {
            Matcher matcher = triggerMonitor.matcher(responseBody);
            while (matcher.find()) {
                String code = matcher.group();
                codigos.put(code, code);
            }
        }

        for (Map.Entry<String, String> entry : codigos.entrySet()) {
            listaJobPayload.add(new JobPayload(entry.getKey(), 0, 0, "", entry.getValue()));
        }

        return listaJobPayload;
    }

    /**
     * Mirrors the iOS String(format:) session substitution while accepting the
     * Java-style %s placeholder used by earlier Android configs. Other percent
     * sequences (for example URL escapes such as %2F) are kept intact.
     */
    public static String formatMonitorUrl(String template, String sessionValue) {
        if (template == null || template.isEmpty()) return template != null ? template : "";
        String replacement = sessionValue != null ? sessionValue : "";
        StringBuilder formatted = new StringBuilder(template.length() + replacement.length());

        for (int i = 0; i < template.length();) {
            if (template.charAt(i) != '%' || i + 1 >= template.length()) {
                formatted.append(template.charAt(i++));
                continue;
            }

            if (template.charAt(i + 1) == '%') {
                formatted.append('%');
                i += 2;
                continue;
            }

            int specifierIndex = i + 1;
            int digitsEnd = specifierIndex;
            while (digitsEnd < template.length() && Character.isDigit(template.charAt(digitsEnd))) {
                digitsEnd++;
            }
            if (digitsEnd > specifierIndex && digitsEnd + 1 < template.length()
                    && template.charAt(digitsEnd) == '$'
                    && (template.charAt(digitsEnd + 1) == '@' || template.charAt(digitsEnd + 1) == 's')) {
                formatted.append(replacement);
                i = digitsEnd + 2;
                continue;
            }

            char specifier = template.charAt(specifierIndex);
            if (specifier == '@' || specifier == 's') {
                formatted.append(replacement);
                i = specifierIndex + 1;
            } else {
                // Keep unrecognized percent sequences literally; URL-encoded
                // bytes such as %2F must not be interpreted as format tokens.
                formatted.append('%');
                i++;
            }
        }
        return formatted.toString();
    }

    private String treatCode(String codigo) {
        if (codigo == null) return "";
        String codigoTratado = codigo.contains(".")
                ? codigo.replace(".", "-")
                : codigo;

        if (codigoTratado.length() == 9 && isAllLetters(codigoTratado)) {
            codigoTratado = codigoTratado.substring(0, 3) + "-"
                    + codigoTratado.substring(3, 6) + "-"
                    + codigoTratado.substring(6, 9);
        }
        return codigoTratado;
    }

    private boolean isAllLetters(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isLetter(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
