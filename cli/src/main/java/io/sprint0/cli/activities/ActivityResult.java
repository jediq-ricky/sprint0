package io.sprint0.cli.activities;

/**
 *
 */
public class ActivityResult {

    public enum Status {SUCCESS, FAILURE}

    private final Status status;

    public ActivityResult(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
