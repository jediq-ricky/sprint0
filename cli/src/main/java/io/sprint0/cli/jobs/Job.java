package io.sprint0.cli.jobs;

import io.sprint0.cli.activities.Activity;
import io.sprint0.cli.activities.ActivityResult;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.cli.CommandLine;

/**
 *
 */
public class Job {

    public enum Status {SUCCESS, FAILURE}

    private List<Activity> activities = new ArrayList <> ();

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public Status execute(CommandLine commandLine) {
        List<ActivityResult> results = activities.stream()
                .map(a -> a.go(commandLine))
                .collect(Collectors.toList());

        return results.stream().allMatch(ar -> ActivityResult.Status.SUCCESS.equals(ar.getStatus()))
                ? Status.SUCCESS : Status.FAILURE;
    }
}
