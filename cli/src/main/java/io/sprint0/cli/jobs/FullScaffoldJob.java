package io.sprint0.cli.jobs;

import io.sprint0.cli.activities.CheckCommandAvailableActivity;
import io.sprint0.cli.activities.DockerPullActivity;
import io.sprint0.cli.activities.DockerStartActivity;
import io.sprint0.cli.configuration.ConfigurationStore;

/**
 *
 */
public class FullScaffoldJob extends Job {



    public FullScaffoldJob() {
        addActivity(new CheckCommandAvailableActivity("docker"));
        addActivity(new DockerPullActivity("jenkins"));
        addActivity(new DockerStartActivity("jenkins"));
    }
}
