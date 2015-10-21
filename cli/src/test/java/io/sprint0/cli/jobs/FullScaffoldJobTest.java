package io.sprint0.cli.jobs;

import io.sprint0.cli.activities.Activity;
import io.sprint0.cli.activities.CheckCommandAvailableActivity;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;

import io.sprint0.cli.activities.DockerPullActivity;
import io.sprint0.cli.activities.DockerStartActivity;
import io.sprint0.cli.configuration.ConfigurationStore;
import org.junit.Test;

/**
 *
 */
public class FullScaffoldJobTest {

    @Test
    public void test() {
        FullScaffoldJob fullScaffoldJob = new FullScaffoldJob();

        List<Activity> activities = fullScaffoldJob.getActivities();
        assertThat(activities.size(), is(11));
        assertThat(activities.get(0), instanceOf(CheckCommandAvailableActivity.class));
    }
}