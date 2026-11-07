package com.a525team.weekbrowser.navi.model;

public enum JobStatus {
    // MonitorAgent
    added,

    // FlowAgent
    isValidating,
    dupe,
    valid,
    notFound,

    // ActionAgent
    isRunning,
    ok,
    error;

    public static JobStatus fromString(String value) {
        if (value == null) return added;
        for (JobStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return added;
    }
}
