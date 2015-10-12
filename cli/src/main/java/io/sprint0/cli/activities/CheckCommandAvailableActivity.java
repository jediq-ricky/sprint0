package io.sprint0.cli.activities;

import java.io.IOException;
import org.apache.commons.cli.CommandLine;

/**
 *
 */
public abstract class CheckCommandAvailableActivity implements Activity {


    @Override
    public ActivityResult go(CommandLine commandLine) {
        try {
            Runtime.getRuntime().exec(getCommand());
        } catch (IOException e) {
            return new ActivityResult(ActivityResult.Status.FAILURE, e);
        }

        return new ActivityResult(ActivityResult.Status.SUCCESS);
    }

    public abstract String getCommand();
}
