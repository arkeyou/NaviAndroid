package com.a525team.weekbrowser.navi.core;

import com.a525team.weekbrowser.navi.agent.ActionAgent;
import com.a525team.weekbrowser.navi.agent.FlowAgent;
import com.a525team.weekbrowser.navi.agent.MonitorAgent;
import com.a525team.weekbrowser.navi.model.ActionEvent;
import com.a525team.weekbrowser.navi.model.Job;
import com.a525team.weekbrowser.navi.model.JobStatus;
import com.a525team.weekbrowser.navi.model.LiveHistoryResponse;
import com.a525team.weekbrowser.navi.model.LiveSession;
import com.a525team.weekbrowser.navi.model.NaviConfig;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AutomationManager implements AutomationEventEmitter {

    public interface AutomationEventListener {
        void onActionEvent(ActionEvent event);
    }

    private final JobStore store = new JobStore();
    private final List<AutomationEventListener> listeners = new CopyOnWriteArrayList<>();

    private MonitorAgent monitorAgent;
    private FlowAgent flowAgent;
    private ActionAgent actionAgent;

    private NaviConfig config = new NaviConfig();

    private String mainVerifyScript = "";
    private String mainActionScript = "";
    private String secondaryVerifyScript = "";
    private String secondaryActionScript = "";

    private volatile boolean isRunning = false;
    private volatile boolean isLiveSessionMonitorRunning = false;
    private Thread liveSessionMonitorThread;
    private final JobStore.JobListener jobEventListener;

    public AutomationManager() {
        this.jobEventListener = new JobStore.JobListener() {
            @Override
            public void onJobEvent(Job job) {
                if (!isRunning || job == null) return;

                if (job.getStatus() == JobStatus.notFound) {
                    emit(new ActionEvent.SendMsg("Codigo nao encontrado: " + job.getPayload().getCodigo()
                            + " (" + job.getPayload().getUsername() + ") ", false, true));
                    return;
                }

                if (job.getStatus() == JobStatus.ok) {
                    emit(new ActionEvent.EnqueuePage(
                            job.getPayload().getCodigo(),
                            job.getPayload().getUrl(),
                            job.getPayload().getUsername(),
                            mainActionScript,
                            mainVerifyScript,
                            secondaryActionScript,
                            secondaryVerifyScript
                    ));
                }
            }
        };
    }

    public void addListener(AutomationEventListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(AutomationEventListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    @Override
    public void emit(ActionEvent event) {
        if (event == null) return;
        for (AutomationEventListener listener : listeners) {
            try {
                listener.onActionEvent(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void start(
            String naviConfigJson,
            String sessionId,
            BrowserCookieProvider cookieProvider,
            double monitorIntervalSeconds
    ) {
        start(naviConfigJson, sessionId, cookieProvider, monitorIntervalSeconds, 30.0);
    }

    public synchronized void start(
            String naviConfigJson,
            String sessionId,
            BrowserCookieProvider cookieProvider,
            double monitorIntervalSeconds,
            double monitorLiveOnlineIntervalSeconds
    ) {
        if (isRunning) return;

        try {
            final BrowserCookieProvider effectiveCookieProvider = cookieProvider != null
                    ? cookieProvider
                    : url -> "";
            config = NaviConfig.fromJsonString(naviConfigJson);

            mainVerifyScript = JavaScriptBuilder.generate(config.getMainVerify());
            mainActionScript = JavaScriptBuilder.generate(config.getMainAction());

            secondaryVerifyScript = config.getSecondaryVerify() != null
                    ? JavaScriptBuilder.generate(config.getSecondaryVerify())
                    : "";
            secondaryActionScript = config.getSecondaryAction() != null
                    ? JavaScriptBuilder.generate(config.getSecondaryAction())
                    : "";

            String effectiveSessionId = sessionId != null ? sessionId : "";
            if (effectiveSessionId.trim().isEmpty() && config.getSessionId() != null) {
                effectiveSessionId = config.getSessionId();
            }
            if (config.getUrlSessionIdentifier() != null && effectiveSessionId.isEmpty()) {
                try {
                    effectiveSessionId = fetchSessionIdentifier(
                            config.getUrlSessionIdentifier(), effectiveCookieProvider);
                } catch (Exception e) {
                    emit(new ActionEvent.SendMsg("Session Warning: " + e.getMessage(), false, true));
                }
            }

            if (Thread.currentThread().isInterrupted()) return;

            long intervalMillis = (long) (monitorIntervalSeconds * 1000);
            if (intervalMillis <= 0) intervalMillis = 5000L;

            monitorAgent = new MonitorAgent(
                    store,
                    config.getUrlMonitor(),
                    config.getTriggerMonitor(),
                    effectiveSessionId,
                    effectiveCookieProvider,
                    intervalMillis,
                    this
            );

            flowAgent = new FlowAgent(
                    store,
                    config.getUrlFlow(),
                    this
            );

            actionAgent = new ActionAgent(
                    store,
                    config.getUrlAction(),
                    this
            );

            if (Thread.currentThread().isInterrupted()) return;

            store.addListener(jobEventListener);

            isRunning = true;
            if (Thread.currentThread().isInterrupted()) {
                stop();
                return;
            }
            monitorAgent.start();
            if (Thread.currentThread().isInterrupted()) {
                stop();
                return;
            }
            flowAgent.start();
            if (Thread.currentThread().isInterrupted()) {
                stop();
                return;
            }
            actionAgent.start();
            emit(new ActionEvent.SendMsg("Automação iniciada com sucesso!", false, true));

            String urlSessionInfo = config.getUrlSessionInfo();
            if (urlSessionInfo != null && !urlSessionInfo.trim().isEmpty()) {
                if (effectiveSessionId.trim().isEmpty()) {
                    emit(new ActionEvent.SendMsg(
                            "Session Warning: não foi possível verificar o status da live porque o sessionId está vazio.",
                            false, true));
                } else {
                    startLiveSessionMonitor(urlSessionInfo, effectiveSessionId, effectiveCookieProvider,
                            monitorLiveOnlineIntervalSeconds);
                }
            }

        } catch (Exception e) {
            emit(new ActionEvent.SendMsg("Falha ao iniciar automação: " + e.getMessage(), true, true));
            stop();
        }
    }

    public void stop() {
        isRunning = false;
        isLiveSessionMonitorRunning = false;

        if (liveSessionMonitorThread != null) {
            liveSessionMonitorThread.interrupt();
            liveSessionMonitorThread = null;
        }

        if (monitorAgent == null && flowAgent == null && actionAgent == null) return;

        store.removeListener(jobEventListener);

        if (monitorAgent != null) {
            monitorAgent.stop();
            monitorAgent = null;
        }

        if (flowAgent != null) {
            flowAgent.stop();
            flowAgent = null;
        }

        if (actionAgent != null) {
            actionAgent.stop();
            actionAgent = null;
        }

        store.clear();
        emit(new ActionEvent.SendMsg("Automação parada.", false, true));
    }

    private void startLiveSessionMonitor(String urlSessionInfo, String sessionId,
                                         BrowserCookieProvider cookieProvider, double intervalSeconds) {
        if (isLiveSessionMonitorRunning) return;
        isLiveSessionMonitorRunning = true;
        long intervalMillis = (long) (intervalSeconds * 1000);
        if (intervalMillis <= 0) intervalMillis = 30000L;
        final long liveCheckIntervalMillis = intervalMillis;

        liveSessionMonitorThread = new Thread(() -> {
            boolean onlineMessageEmitted = false;
            while (isRunning && isLiveSessionMonitorRunning
                    && !Thread.currentThread().isInterrupted()) {
                try {
                    if (!fetchSessionIsOpen(urlSessionInfo, sessionId, cookieProvider)) {
                        emit(new ActionEvent.SendMsg("A live NÃO está online! Automação interrompida.", true, true));
                        isLiveSessionMonitorRunning = false;
                        break;
                    }
                    if (!onlineMessageEmitted) {
                        emit(new ActionEvent.SendMsg("Live online; monitoramento ativo.", false, true));
                        onlineMessageEmitted = true;
                    }
                    Thread.sleep(liveCheckIntervalMillis);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    if (isRunning && isLiveSessionMonitorRunning) {
                        String message = e.getMessage() != null
                                ? e.getMessage()
                                : e.getClass().getSimpleName();
                        emit(new ActionEvent.SendMsg("Session Error: " + message, false, true));
                    }
                    isLiveSessionMonitorRunning = false;
                    break;
                }
            }
        }, "Navi-LiveSessionMonitor-Thread");
        liveSessionMonitorThread.setDaemon(true);
        liveSessionMonitorThread.start();
    }

    private boolean fetchSessionIsOpen(String urlSessionInfo, String sessionId,
                                       BrowserCookieProvider cookieProvider)
            throws Exception {
        String formattedUrl = MonitorAgent.formatMonitorUrl(urlSessionInfo, sessionId);
        URL url = new URL(formattedUrl);
        String protocol = url.getProtocol();
        if (!"http".equalsIgnoreCase(protocol) && !"https".equalsIgnoreCase(protocol)) {
            throw new java.io.IOException("URL de verificação da live deve usar HTTP ou HTTPS.");
        }

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            String cookies = cookieProvider.getCookieHeader(formattedUrl);
            if (cookies != null && !cookies.isEmpty()) {
                connection.setRequestProperty("Cookie", cookies);
            }

            int responseCode = connection.getResponseCode();
            InputStreamReader reader = new InputStreamReader(
                    responseCode >= 200 && responseCode <= 299
                            ? connection.getInputStream()
                            : connection.getErrorStream(),
                    StandardCharsets.UTF_8);
            StringBuilder response = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) response.append(line);
            }

            if (responseCode < 200 || responseCode > 299) {
                throw new java.io.IOException("HTTP " + responseCode + " na verificação da live.");
            }

            JSONObject json = new JSONObject(response.toString());
            if (json.has("msg") && !json.isNull("msg")) {
                throw new Exception("Gentileza efetuar o login para verificar a live.");
            }
            JSONObject data = json.optJSONObject("data");
            if (data == null || !data.has("sessionStatus")) {
                throw new java.io.IOException("Resposta inválida na verificação da live.");
            }
            Object status = data.opt("sessionStatus");
            if (status instanceof Number && ((Number) status).intValue() == 1) return true;
            if ("1".equals(String.valueOf(status))) return true;
            return false;
        } finally {
            connection.disconnect();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public JobStore getStore() {
        return store;
    }

    private String fetchSessionIdentifier(String urlSessionIdentifier,
                                          BrowserCookieProvider cookieProvider) throws Exception {
        URL url = new URL(urlSessionIdentifier);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);
        String cookies = cookieProvider.getCookieHeader(url.toString());
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

        JSONObject json = new JSONObject(responseBuilder.toString());
        LiveHistoryResponse liveHistory = LiveHistoryResponse.fromJson(json);
        if (liveHistory.getMsg() == null && liveHistory.getData() != null) {
            for (LiveSession session : liveHistory.getData().getList()) {
                if (session.getStatus() == 1) {
                    emit(new ActionEvent.SendMsg("Encontrou sessão online ativa!", false, true));
                    return String.valueOf(session.getSessionId());
                }
            }
        }
        throw new Exception("Nenhuma sessão online ativa encontrada.");
    }
}
