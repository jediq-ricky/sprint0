package io.sprint0.cli.activities;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class DockerPullActivity extends DockerActivity {

    private final String imageName;

    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    public DockerPullActivity(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public ActivityResult go(CommandLine commandLine) {
        try {
            DockerClient docker = getDocker();
            docker.pull(imageName);
        } catch (DockerException | DockerCertificateException | InterruptedException e) {
            logger.debug("Got exception from docker for imageName : " + imageName, e);
            return new ActivityResult(ActivityResult.Status.FAILURE, e);
        }

        return new ActivityResult(ActivityResult.Status.SUCCESS);
    }
}
