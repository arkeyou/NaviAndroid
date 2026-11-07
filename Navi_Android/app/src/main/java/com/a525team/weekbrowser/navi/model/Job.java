package com.a525team.weekbrowser.navi.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public final class Job {
    private final UUID id;
    private JobPayload payload;
    private JobStatus status;
    private final Date createdAt;
    private Date updatedAt;
    private String errorMessage;

    public Job(UUID id, JobPayload payload, JobStatus status, Date createdAt, Date updatedAt, String errorMessage) {
        this.id = id != null ? id : UUID.randomUUID();
        this.payload = payload != null ? payload : new JobPayload("", 0, 0, "", "");
        this.status = status != null ? status : JobStatus.added;
        this.createdAt = createdAt != null ? createdAt : new Date();
        this.updatedAt = updatedAt != null ? updatedAt : new Date();
        this.errorMessage = errorMessage;
    }

    public Job(JobPayload payload) {
        this(UUID.randomUUID(), payload, JobStatus.added, new Date(), new Date(), null);
    }

    public static Job fromJson(JSONObject json) {
        if (json == null) return null;
        UUID id;
        try {
            id = UUID.fromString(json.optString("id", ""));
        } catch (Exception e) {
            id = UUID.randomUUID();
        }

        JobPayload payload = JobPayload.fromJson(json.optJSONObject("payload"));
        JobStatus status = JobStatus.fromString(json.optString("status", "added"));
        long createdMs = json.optLong("createdAt", System.currentTimeMillis());
        long updatedMs = json.optLong("updatedAt", System.currentTimeMillis());
        String errorMsg = json.isNull("errorMessage") ? null : json.optString("errorMessage", null);

        return new Job(id, payload, status, new Date(createdMs), new Date(updatedMs), errorMsg);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", id.toString());
        json.put("payload", payload.toJson());
        json.put("status", status.name());
        json.put("createdAt", createdAt.getTime());
        json.put("updatedAt", updatedAt.getTime());
        if (errorMessage != null) {
            json.put("errorMessage", errorMessage);
        } else {
            json.put("errorMessage", JSONObject.NULL);
        }
        return json;
    }

    public UUID getId() {
        return id;
    }

    public JobPayload getPayload() {
        return payload;
    }

    public void setPayload(JobPayload payload) {
        this.payload = payload != null ? payload : new JobPayload("", 0, 0, "", "");
        this.updatedAt = new Date();
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status != null ? status : JobStatus.added;
        this.updatedAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt != null ? updatedAt : new Date();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        this.updatedAt = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Job)) return false;
        Job job = (Job) o;
        return Objects.equals(id, job.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", payload=" + payload +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
