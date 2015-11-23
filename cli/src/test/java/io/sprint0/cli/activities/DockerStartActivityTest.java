package io.sprint0.cli.activities;

import com.google.common.collect.ImmutableList;
import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
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
import static org.mockito.Mockito.*;

/**
 *
 */
public class DockerStartActivityTest {

    @Test
    public void testHaveNotPulled() throws Exception {
        final DockerClient docker = mock(DockerClient.class);
        DockerActivity dockerActivity = getDockerActivity(docker);
        Image image = mock(Image.class);
        when(image.repoTags()).thenReturn(ImmutableList.of("test_jenkinsXXXX:123456"));
        when(docker.listImages()).thenReturn(ImmutableList.of(image));

        ActivityResult activityResult = dockerActivity.go(null);
        assertThat(activityResult.getStatus(), is(ActivityResult.Status.FAILURE));
        assertThat(activityResult.getMessage(), is("We haven't pulled image : jenkins"));

        verify(docker, never()).startContainer(eq("test_jenkins"));
    }

    @Test
    public void testSuccessfulStart() throws Exception {
        final DockerClient docker = mock(DockerClient.class);
        DockerActivity dockerActivity = getDockerActivity(docker);
        Image image = mock(Image.class);
        when(image.id()).thenReturn("123456");
        when(image.repoTags()).thenReturn(ImmutableList.of("jenkins:12.34.56"));
        when(docker.listImages()).thenReturn(ImmutableList.of(image));

        ContainerCreation containerCreation = mock(ContainerCreation.class);
        when(docker.createContainer(any(ContainerConfig.class), any(String.class))).thenReturn(containerCreation);

        ActivityResult activityResult = dockerActivity.go(null);

        assertThat(activityResult.getStatus(), is(ActivityResult.Status.SUCCESS));

        verify(docker, never()).startContainer(eq("test_jenkins"));
    }

    private DockerActivity getDockerActivity(final DockerClient docker) {
        DockerStartActivity activity = new DockerStartActivity(new Jenkins()) {
            @Override
            public DockerClient getDocker() throws DockerCertificateException {
                return docker;
            }
        };
        Job job = new Job();
        job.setConfigurationStore(new ConfigurationStore());
        job.addActivity(activity);
        return activity;
    }

    @Test
    @Category(IntegrationTest.class)
    public void testKnownImageIT() {
        DockerActivity dockerActivity = new DockerStartActivity(new Nginx());
        Job job = new Job();
        job.setConfigurationStore(new ConfigurationStore());
        job.addActivity(dockerActivity);


        ActivityResult activityResult = dockerActivity.go(null);
        assertThat(activityResult.getStatus(), is(ActivityResult.Status.SUCCESS));
    }
}
