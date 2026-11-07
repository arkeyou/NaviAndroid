package com.a525team.weekbrowser.navi.core;

import com.a525team.weekbrowser.navi.model.Job;
import com.a525team.weekbrowser.navi.model.JobStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class JobStore {

    public interface JobListener {
        void onJobEvent(Job job);
    }

    private final Map<UUID, Job> jobs = new LinkedHashMap<>();
    private final List<JobListener> listeners = new CopyOnWriteArrayList<>();

    public synchronized void addListener(JobListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public synchronized void removeListener(JobListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    public synchronized void insert(Job job) {
        if (job == null) return;
        jobs.put(job.getId(), job);
        publish(job);
    }

    public synchronized void update(Job job) {
        if (job == null) return;
        jobs.put(job.getId(), job);
        publish(job);
    }

    public synchronized List<Job> all() {
        List<Job> list = new ArrayList<>(jobs.values());
        Collections.sort(list, new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                return o2.getCreatedAt().compareTo(o1.getCreatedAt());
            }
        });
        return list;
    }

    public synchronized void clear() {
        jobs.clear();
    }

    public synchronized List<Job> jobsWithStatus(JobStatus status) {
        List<Job> result = new ArrayList<>();
        for (Job job : jobs.values()) {
            if (job.getStatus() == status) {
                result.add(job);
            }
        }
        return result;
    }

    public synchronized boolean hasDuplicate(String code, UUID excludingId) {
        if (code == null) return false;
        for (Job job : jobs.values()) {
            if (!Objects.equals(job.getId(), excludingId)
                    && code.equals(job.getPayload().getCodigo())
                    && job.getStatus() != JobStatus.dupe) {
                return true;
            }
        }
        return false;
    }

    private void publish(Job job) {
        for (JobListener listener : listeners) {
            try {
                listener.onJobEvent(job);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
