package io.sprint0.cli.activities;

import org.apache.commons.cli.CommandLine;

/**
 *
 */
public interface Activity {

    ActivityResult go(CommandLine commandLine);
}
