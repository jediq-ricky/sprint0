package io.sprint0.cli.activities;

import org.apache.commons.cli.CommandLine;

/**
 *
 */
public class CheckDockerInstalled implements Activity {

    @Override
    public ActivityResult go(CommandLine commandLine) {

        return new ActivityResult(ActivityResult.Status.SUCCESS);
    }
}
