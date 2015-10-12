package io.sprint0.cli.jobs;

import io.sprint0.cli.activities.ActivityResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.Test;

/**
 *
 */
public class JobTest {

    @Test
    public void testExecute_one_pass() {
        Job job = new Job();
        job.addActivity(a -> new ActivityResult(ActivityResult.Status.SUCCESS));

        Job.Status jobStatus = job.execute(null);
        assertThat(jobStatus, is(Job.Status.SUCCESS));
    }


    @Test
    public void testExecute_two_pass() {
        Job job = new Job();
        job.addActivity(a -> new ActivityResult(ActivityResult.Status.SUCCESS));
        job.addActivity(a -> new ActivityResult(ActivityResult.Status.SUCCESS));

        Job.Status jobStatus = job.execute(null);
        assertThat(jobStatus, is(Job.Status.SUCCESS));
    }


    @Test
    public void testExecute_one_pass_one_fail() {
        Job job = new Job();
        job.addActivity(a -> new ActivityResult(ActivityResult.Status.SUCCESS));
        job.addActivity(a -> new ActivityResult(ActivityResult.Status.FAILURE));

        Job.Status jobStatus = job.execute(null);
        assertThat(jobStatus, is(Job.Status.FAILURE));
    }

    @Test
    public void testExecute_one_fail() {
        Job job = new Job();
        job.addActivity(a -> new ActivityResult(ActivityResult.Status.FAILURE));

        Job.Status jobStatus = job.execute(null);
        assertThat(jobStatus, is(Job.Status.FAILURE));
    }

    @Test
    public void testExecute_two_fail() {
        Job job = new Job();
        job.addActivity(a -> new ActivityResult(ActivityResult.Status.FAILURE));
        job.addActivity(a -> new ActivityResult(ActivityResult.Status.FAILURE));

        Job.Status jobStatus = job.execute(null);
        assertThat(jobStatus, is(Job.Status.FAILURE));
    }


}
