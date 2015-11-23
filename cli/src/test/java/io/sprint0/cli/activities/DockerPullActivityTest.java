package io.sprint0.cli.activities;

import com.google.common.collect.ImmutableList;
import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.ProgressHandler;
import com.spotify.docker.client.messages.Image;
import io.sprint0.cli.IntegrationTest;
import io.sprint0.cli.configuration.ConfigurationStore;
import io.sprint0.cli.jobs.Job;
import io.sprint0.cli.tools.Jenkins;
import io.sprint0.cli.tools.Nginx;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.*;

/**
 *
 */
public class DockerPullActivityTest {

    @Test
    public void testGoodResult() throws Exception {
        final DockerClient docker = mock(DockerClient.class);

        DockerActivity dockerActivity = getDockerPullActivity(docker);

        ActivityResult activityResult = dockerActivity.go(null);
        assertThat(activityResult.getStatus(), is(ActivityResult.Status.SUCCESS));

        verify(docker).pull(eq("sprint0/jenkins"), any(ProgressHandler.class));
    }

    @Test
    public void testBadResult() throws Exception {
        final DockerClient docker = mock(DockerClient.class);

        DockerActivity dockerActivity = getDockerPullActivity(docker);

        doThrow(new DockerException("fake")).when(docker).pull(eq("sprint0/jenkins"), any(ProgressHandler.class));

        ActivityResult activityResult = dockerActivity.go(null);
        assertThat(activityResult.getStatus(), is(ActivityResult.Status.FAILURE));
        assertThat(activityResult.getCause(), is(instanceOf(DockerException.class)));
    }

    private DockerPullActivity getDockerPullActivity(final DockerClient docker) {
        return new DockerPullActivity(new Jenkins()) {
            @Override
            public DockerClient getDocker() throws DockerCertificateException {
                return docker;
            }
        };
    }

    @Test
    public void testAlreadyPulled() throws Exception {
        final DockerClient docker = mock(DockerClient.class);

        DockerActivity dockerActivity = getDockerPullActivity(docker);
        Image image = mock(Image.class);
        when(image.id()).thenReturn("123456");
        when(image.repoTags()).thenReturn(ImmutableList.of("jenkins:12.34.56"));
        when(docker.listImages()).thenReturn(ImmutableList.of(image));

        dockerActivity.go(null);

        verify(docker, never()).pull(eq("test_jenkins"), any(ProgressHandler.class));

    }

    @Test
    @Category(IntegrationTest.class)
    public void testKnownImageIT() {
        DockerPullActivity dockerActivity = new DockerPullActivity(new Jenkins());

        Job job = new Job();
        job.setConfigurationStore(new ConfigurationStore());
        job.addActivity(dockerActivity);

        ActivityResult activityResult = dockerActivity.go(null);
        assertThat(activityResult.getStatus(), is(ActivityResult.Status.SUCCESS));
    }
}
