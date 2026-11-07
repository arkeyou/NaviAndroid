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
            String cookieList,
            double monitorIntervalSeconds
    ) {
        if (isRunning) return;

        try {
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
            if (config.getUrlSessionIdentifier() != null && effectiveSessionId.isEmpty()) {
                try {
                    effectiveSessionId = fetchSessionIdentifier(config.getUrlSessionIdentifier(), cookieList);
                } catch (Exception e) {
                    emit(new ActionEvent.SendMsg("Session Warning: " + e.getMessage(), false, true));
                }
            }

            long intervalMillis = (long) (monitorIntervalSeconds * 1000);
            if (intervalMillis <= 0) intervalMillis = 5000L;

            monitorAgent = new MonitorAgent(
                    store,
                    config.getUrlMonitor(),
                    config.getTriggerMonitor(),
                    effectiveSessionId,
                    cookieList,
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

            store.addListener(jobEventListener);

            monitorAgent.start();
            flowAgent.start();
            actionAgent.start();

            isRunning = true;
            emit(new ActionEvent.SendMsg("Automação iniciada com sucesso!", false, true));

        } catch (Exception e) {
            emit(new ActionEvent.SendMsg("Falha ao iniciar automação: " + e.getMessage(), true, true));
            stop();
        }
    }

    public synchronized void stop() {
        if (!isRunning) return;
        isRunning = false;

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

    public boolean isRunning() {
        return isRunning;
    }

    public JobStore getStore() {
        return store;
    }

    private String fetchSessionIdentifier(String urlSessionIdentifier, String cookies) throws Exception {
        URL url = new URL(urlSessionIdentifier);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);
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
