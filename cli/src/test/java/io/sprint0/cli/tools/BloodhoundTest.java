package io.sprint0.cli.tools;

import io.sprint0.cli.IntegrationTest;
import io.sprint0.cli.activities.ActivityResult;
import io.sprint0.cli.activities.DockerActivity;
import io.sprint0.cli.activities.DockerPullActivity;
import io.sprint0.cli.configuration.ConfigurationStore;
import io.sprint0.cli.jobs.Job;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 *
 */
public class BloodhoundTest extends ToolTest <Bloodhound> {

    @Override
    public Bloodhound createTool() {
        return new Bloodhound();
    }
}
