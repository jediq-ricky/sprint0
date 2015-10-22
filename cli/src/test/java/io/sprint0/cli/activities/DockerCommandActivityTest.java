package io.sprint0.cli.activities;

import com.google.common.collect.ImmutableList;
import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.ProgressHandler;
import com.spotify.docker.client.messages.Image;
import io.sprint0.cli.IntegrationTest;
import io.sprint0.cli.configuration.Tool;
import io.sprint0.cli.tools.Jenkins;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.*;

/**
 *
 */

@Ignore
public class DockerCommandActivityTest {

    @Test
    public void testGoodResult() throws Exception {
        final DockerClient docker = mock(DockerClient.class);

        DockerActivity dockerActivity = getDockerActivity(docker);

        ActivityResult activityResult = dockerActivity.go(null);
        assertThat(activityResult.getStatus(), is(ActivityResult.Status.SUCCESS));

        verify(docker).pull(eq("jenkins"), any(ProgressHandler.class));
    }

    @Test
    public void testBadResult() throws Exception {
        final DockerClient docker = mock(DockerClient.class);

        DockerActivity dockerActivity = getDockerActivity(docker);

        doThrow(new DockerException("fake")).when(docker).pull(eq("jenkins"), any(ProgressHandler.class));

        ActivityResult activityResult = dockerActivity.go(null);
        assertThat(activityResult.getStatus(), is(ActivityResult.Status.FAILURE));
        assertThat(activityResult.getCause(), is(instanceOf(DockerException.class)));
    }

    private DockerActivity getDockerActivity(final DockerClient docker) {

        Tool tool = new Tool();
        tool.addInstance("12345");

        return new DockerCommandActivity(tool, "ls") {
            @Override
            public DockerClient getDocker() throws DockerCertificateException {
                return docker;
            }
        };
    }

    @Test
    @Category(IntegrationTest.class)
    public void testKnownImageIT() {
        DockerPullActivity dockerPullActivity = new DockerPullActivity(new Jenkins());

        ActivityResult activityResult = dockerPullActivity.go(null);
        assertThat(activityResult.getStatus(), is(ActivityResult.Status.SUCCESS));
    }
}
