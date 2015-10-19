package io.sprint0.cli.activities;

import io.sprint0.cli.jobs.Job;
import org.apache.commons.cli.CommandLine;

/**
 *
 */
public interface Activity {

    ActivityResult go(CommandLine commandLine);

    void setJob(Job job);
}
