package io.sprint0.cli.activities;

/**
 *
 */
public class ActivityResult {

    public enum Status {
        SUCCESS, FAILURE
    }

    private final Status status;

    private Throwable cause;

    public ActivityResult(Status status) {
        this.status = status;
    }

    public ActivityResult(Status status, Throwable cause) {
        this.status = status;
        this.cause = cause;
    }

    public Status getStatus() {
        return status;
    }

    public Throwable getCause() {
        return cause;
    }
}
