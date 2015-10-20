package io.sprint0.cli.jobs;

import io.sprint0.cli.activities.CheckCommandAvailableActivity;
import io.sprint0.cli.activities.DockerPullActivity;
import io.sprint0.cli.activities.DockerStartActivity;
import io.sprint0.cli.configuration.ConfigurationStore;
import io.sprint0.cli.tools.Dns;
import io.sprint0.cli.tools.Jenkins;

/**
 *
 */
public class FullScaffoldJob extends Job {



    public FullScaffoldJob() {
        addActivity(new CheckCommandAvailableActivity("docker"));

        Jenkins jenkins = new Jenkins();
        Dns dns = new Dns();

        addActivity(new DockerPullActivity(jenkins));
        addActivity(new DockerPullActivity(dns));

        addActivity(new DockerStartActivity(jenkins));
        addActivity(new DockerStartActivity(dns));
    }
}
