package io.sprint0.cli.activities;

import java.io.IOException;
import org.apache.commons.cli.CommandLine;

/**
 *
 */
public class CheckDockerInstalled implements Activity {

    @Override
    public ActivityResult go(CommandLine commandLine) {
        try {
            Runtime.getRuntime().exec("docker");
        } catch (IOException e) {
            return new ActivityResult(ActivityResult.Status.FAILURE);
        }

        return new ActivityResult(ActivityResult.Status.SUCCESS);
    }
}
