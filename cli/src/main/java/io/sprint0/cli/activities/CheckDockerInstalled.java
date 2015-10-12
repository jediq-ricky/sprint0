package io.sprint0.cli.activities;

import java.io.IOException;
import org.apache.commons.cli.CommandLine;

/**
 *
 */
public class CheckDockerInstalled extends CheckCommmandAvailableActivity {

    @Override
    public String getCommand() {
        return "docker";
    }
}
