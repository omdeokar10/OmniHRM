package com.example.performance_management.entity;

public enum CompletionStatus {
    NOT_STARTED("Not started"),
    IN_PROGRESS("in_progress"),
    BLOCKED("blocked"),
    DONE("done");

    private String status;

    CompletionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
