package io.sprint0.cli.jobs;

import io.sprint0.cli.activities.Activity;
import io.sprint0.cli.activities.ActivityResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.cli.CommandLine;

/**
 *
 */
public class Job {

    public enum Status {
        SUCCESS, FAILURE
    }

    private List<Activity> activities = new ArrayList <> ();

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public Status execute(CommandLine commandLine) {

        List<ActivityResult> results = new ArrayList <>();
        for (Activity activity : activities) {
            ActivityResult result = activity.go(commandLine);
            results.add(result);
            if (ActivityResult.Status.FAILURE.equals(result.getStatus())) {
                return Status.FAILURE;
            }
        }
        return Status.SUCCESS;
    }

    public List<Activity> getActivities() {
        return Collections.unmodifiableList(activities);
    }
}
