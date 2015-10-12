package io.sprint0.cli.activities;

/**
 *
 */
public class CheckDockerInstalled extends CheckCommandAvailableActivity {

    @Override
    public String getCommand() {
        return "docker";
    }
}
