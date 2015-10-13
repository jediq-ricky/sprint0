package io.sprint0.cli.activities;

/**
 *
 */
public class ActivityResult {

    public enum Status {
        SUCCESS, FAILURE
    }

    private final Status status;
    private final String message;
    private final Throwable cause;

    public ActivityResult(Status status) {
        this.status = status;
        this.message = null;
        this.cause = null;
    }

    public ActivityResult(Status status, String message) {
        this.status = status;
        this.message = message;
        this.cause = null;
    }

    public ActivityResult(Status status, Throwable cause) {
        this.status = status;
        this.message = cause.getMessage();
        this.cause = cause;
    }

    public Status getStatus() {
        return status;
    }

    public Throwable getCause() {
        return cause;
    }

    public String getMessage() {
        return message;
    }
}
