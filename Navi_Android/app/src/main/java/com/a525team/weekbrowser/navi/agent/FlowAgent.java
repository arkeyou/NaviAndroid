package com.a525team.weekbrowser.navi.agent;

import com.a525team.weekbrowser.navi.core.AutomationEventEmitter;
import com.a525team.weekbrowser.navi.core.JobStore;
import com.a525team.weekbrowser.navi.model.ActionEvent;
import com.a525team.weekbrowser.navi.model.Job;
import com.a525team.weekbrowser.navi.model.JobStatus;
import com.a525team.weekbrowser.navi.model.SearchResponse;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FlowAgent {

    private final JobStore store;
    private final String urlFlow;
    private final AutomationEventEmitter emitter;

    private final BlockingQueue<Job> queue = new LinkedBlockingQueue<>();
    private final JobStore.JobListener jobListener;

    private volatile boolean isRunning = false;
    private Thread workerThread = null;

    public FlowAgent(JobStore store, String urlFlow, AutomationEventEmitter emitter) {
        this.store = store;
        this.urlFlow = urlFlow != null ? urlFlow : "";
        this.emitter = emitter;

        this.jobListener = new JobStore.JobListener() {
            @Override
            public void onJobEvent(Job job) {
                if (isRunning && job != null && job.getStatus() == JobStatus.added) {
                    queue.offer(job);
                }
            }
        };
    }

    public synchronized void start() {
        if (isRunning) return;
        isRunning = true;

        store.addListener(jobListener);

        // Enfileira jobs que já possam estar no store com status added
        for (Job existing : store.jobsWithStatus(JobStatus.added)) {
            queue.offer(existing);
        }

        workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning && !Thread.currentThread().isInterrupted()) {
                    try {
                        Job job = queue.take();
                        if (!isRunning) break;
                        validate(job);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    } catch (Exception e) {
                        if (emitter != null) {
                            emitter.emit(new ActionEvent.SendMsg("Flow Agent Error: " + e.getMessage(), false, true));
                        }
                    }
                }
            }
        }, "Navi-FlowAgent-Thread");

        workerThread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        store.removeListener(jobListener);
        queue.clear();
        if (workerThread != null) {
            workerThread.interrupt();
            workerThread = null;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void validate(Job job) throws Exception {
        if (job.getStatus() != JobStatus.added) {
            return;
        }

        job.setStatus(JobStatus.isValidating);
        job.setUpdatedAt(new Date());
        store.update(job);

        boolean isDuplicate = store.hasDuplicate(job.getPayload().getCodigo(), job.getId());
        if (isDuplicate) {
            job.setStatus(JobStatus.dupe);
            job.setUpdatedAt(new Date());
            store.update(job);
            return;
        }

        if (urlFlow.isEmpty()) {
            job.setStatus(JobStatus.valid);
            job.setUpdatedAt(new Date());
            store.update(job);
            return;
        }

        try {
            SearchResponse searchResult = fetchApi(job.getPayload().getCodigo());
            if (searchResult != null && searchResult.getDirectSearchResult() != null) {
                job.setStatus(JobStatus.valid);
                job.setUpdatedAt(new Date());
                job.getPayload().setParam1(searchResult.getDirectSearchResult().getItem().getShopID());
                job.getPayload().setParam2(searchResult.getDirectSearchResult().getItem().getItemID());
                store.update(job);
            } else {
                job.setStatus(JobStatus.notFound);
                job.setUpdatedAt(new Date());
                store.update(job);
            }
        } catch (Exception e) {
            job.setStatus(JobStatus.notFound);
            job.setErrorMessage(e.getMessage());
            job.setUpdatedAt(new Date());
            store.update(job);
        }
    }

    private SearchResponse fetchApi(String codigo) throws Exception {
        URL url = new URL(urlFlow + codigo);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);

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
        return SearchResponse.fromJson(json);
    }
}
