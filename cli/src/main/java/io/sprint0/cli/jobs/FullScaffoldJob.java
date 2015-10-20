package io.sprint0.cli.jobs;

import io.sprint0.cli.activities.CheckCommandAvailableActivity;
import io.sprint0.cli.activities.DockerPullActivity;
import io.sprint0.cli.activities.DockerStartActivity;
import io.sprint0.cli.tools.Bind;
import io.sprint0.cli.tools.Jenkins;

/**
 *
 */
public class FullScaffoldJob extends Job {



    public FullScaffoldJob() {
        addActivity(new CheckCommandAvailableActivity("docker"));

        Jenkins jenkins = new Jenkins();
        Bind bind = new Bind();

        addActivity(new DockerPullActivity(jenkins));
        addActivity(new DockerPullActivity(bind));

        addActivity(new DockerStartActivity(jenkins));
        addActivity(new DockerStartActivity(bind));
    }
}
