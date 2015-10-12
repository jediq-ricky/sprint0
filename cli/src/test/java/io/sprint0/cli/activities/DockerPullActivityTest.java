package io.sprint0.cli.activities;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
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
public class DockerPullActivityTest {

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

        verify(docker).pull("test_jenkins");
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

        doThrow(new DockerException("fake")).when(docker).pull("test_jenkins");

        ActivityResult activityResult = dockerActivity.go(null);
        assertThat(activityResult.getStatus(), is(ActivityResult.Status.FAILURE));
        assertThat(activityResult.getCause(), is(instanceOf(DockerException.class)));
    }

    @Test
    @Category(IntegrationTest.class)
    public void testKnownImageIT() {
        DockerPullActivity dockerPullActivity = new DockerPullActivity("jenkins");


        ActivityResult activityResult = dockerPullActivity.go(null);
        assertThat(activityResult.getStatus(), is(ActivityResult.Status.SUCCESS));
    }
}
