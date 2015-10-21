package io.sprint0.cli.jobs;

import io.sprint0.cli.activities.CheckCommandAvailableActivity;
import io.sprint0.cli.activities.DockerPullActivity;
import io.sprint0.cli.activities.DockerStartActivity;
import io.sprint0.cli.configuration.Tool;
import io.sprint0.cli.tools.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class FullScaffoldJob extends Job {

    public FullScaffoldJob() {
        addActivity(new CheckCommandAvailableActivity("docker"));

        List<Tool> tools = Arrays.asList(
                new Bind(),
                new Nginx(),
                new FreeIPA(),
                new Jenkins(),
                new Artifactory()
                );

        tools.forEach(t -> {
            addActivity(new DockerPullActivity(t));
            addActivity(new DockerStartActivity(t));
        });
    }
}
