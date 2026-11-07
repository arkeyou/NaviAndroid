package com.a525team.weekbrowser.navi.agent;

import com.a525team.weekbrowser.navi.core.AutomationEventEmitter;
import com.a525team.weekbrowser.navi.core.JobStore;
import com.a525team.weekbrowser.navi.model.ActionEvent;
import com.a525team.weekbrowser.navi.model.Job;
import com.a525team.weekbrowser.navi.model.JobStatus;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ActionAgent {

    private final JobStore store;
    private final String urlAction;
    private final AutomationEventEmitter emitter;

    private final BlockingQueue<Job> queue = new LinkedBlockingQueue<>();
    private final JobStore.JobListener jobListener;

    private volatile boolean isRunning = false;
    private Thread workerThread = null;

    public ActionAgent(JobStore store, String urlAction, AutomationEventEmitter emitter) {
        this.store = store;
        this.urlAction = urlAction != null ? urlAction : "";
        this.emitter = emitter;

        this.jobListener = new JobStore.JobListener() {
            @Override
            public void onJobEvent(Job job) {
                if (isRunning && job != null && job.getStatus() == JobStatus.valid) {
                    queue.offer(job);
                }
            }
        };
    }

    public synchronized void start() {
        if (isRunning) return;
        isRunning = true;

        store.addListener(jobListener);

        for (Job existing : store.jobsWithStatus(JobStatus.valid)) {
            queue.offer(existing);
        }

        workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning && !Thread.currentThread().isInterrupted()) {
                    try {
                        Job job = queue.take();
                        if (!isRunning) break;
                        process(job);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    } catch (Exception e) {
                        if (emitter != null) {
                            emitter.emit(new ActionEvent.SendMsg("Action Agent Error: " + e.getMessage(), false, true));
                        }
                    }
                }
            }
        }, "Navi-ActionAgent-Thread");

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

    private void process(Job job) {
        if (job.getStatus() != JobStatus.valid) {
            return;
        }

        String targetUrl;
        if (job.getPayload().getParam1() != 0 && job.getPayload().getParam2() != 0) {
            targetUrl = urlAction + job.getPayload().getParam1() + "." + job.getPayload().getParam2();
        } else {
            targetUrl = urlAction + job.getPayload().getCodigo();
        }

        job.getPayload().setUrl(targetUrl);
        job.setStatus(JobStatus.ok);
        job.setUpdatedAt(new Date());

        store.update(job);
    }
}
