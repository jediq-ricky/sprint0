package io.sprint0.cli.activities;

import com.google.common.collect.ImmutableList;
import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.ProgressHandler;
import com.spotify.docker.client.messages.Image;
import io.sprint0.cli.IntegrationTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.mockito.Mockito.*;

/**
 *
 */
public class DockerStartActivityTest {

    @Test
    public void testGoodResult() throws Exception {
        final DockerClient docker = mock(DockerClient.class);

        DockerActivity dockerActivity = new DockerPullActivity("test_jenkins") {
            @Override
            public DockerClient getDocker() throws DockerCertificateException {
                return docker;
            }
        };

        ActivityResult activityResult = dockerActivity.go(null);
        assertThat(activityResult.getStatus(), is(ActivityResult.Status.SUCCESS));

        verify(docker).pull(eq("test_jenkins"), any(ProgressHandler.class));
    }

    @Test
    public void testBadResult() throws Exception {
        final DockerClient docker = mock(DockerClient.class);

        DockerActivity dockerActivity = new DockerPullActivity("test_jenkins") {
            @Override
            public DockerClient getDocker() throws DockerCertificateException {
                return docker;
            }
        };

        doThrow(new DockerException("fake")).when(docker).pull(eq("test_jenkins"), any(ProgressHandler.class));

        ActivityResult activityResult = dockerActivity.go(null);
        assertThat(activityResult.getStatus(), is(ActivityResult.Status.FAILURE));
        assertThat(activityResult.getCause(), is(instanceOf(DockerException.class)));
    }

    @Test
    public void testAlreadyPulled() throws Exception {
        final DockerClient docker = mock(DockerClient.class);

        DockerActivity dockerActivity = new DockerPullActivity("test_jenkins") {
            @Override
            public DockerClient getDocker() throws DockerCertificateException {
                return docker;
            }
        };
        Image image = mock(Image.class);
        when(image.repoTags()).thenReturn(ImmutableList.of("test_jenkins:123456"));
        when(docker.listImages()).thenReturn(ImmutableList.of(image));

        dockerActivity.go(null);

        verify(docker, never()).pull(eq("test_jenkins"), any(ProgressHandler.class));

    }

    @Test
    @Category(IntegrationTest.class)
    public void testKnownImageIT() {
        DockerPullActivity dockerPullActivity = new DockerPullActivity("jenkins");


        ActivityResult activityResult = dockerPullActivity.go(null);
        assertThat(activityResult.getStatus(), is(ActivityResult.Status.SUCCESS));
    }
}
