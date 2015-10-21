package io.sprint0.cli.tools;

import io.sprint0.cli.IntegrationTest;
import io.sprint0.cli.activities.ActivityResult;
import io.sprint0.cli.activities.DockerActivity;
import io.sprint0.cli.activities.DockerPullActivity;
import io.sprint0.cli.configuration.ConfigurationStore;
import io.sprint0.cli.configuration.Tool;
import io.sprint0.cli.jobs.Job;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 *
 */
public abstract class ToolTest <T extends Tool> {


    @Test
    @Category(IntegrationTest.class)
    public void testPull() {
        DockerActivity dockerActivity = new DockerPullActivity(createTool());
        Job job = new Job();
        job.setConfigurationStore(new ConfigurationStore());
        job.addActivity(dockerActivity);

        ActivityResult activityResult = dockerActivity.go(null);
        assertThat(activityResult.getStatus(), is(ActivityResult.Status.SUCCESS));
    }

    protected abstract T createTool();


}
