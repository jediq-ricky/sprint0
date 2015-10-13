package io.sprint0.cli.activities;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.ProgressHandler;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.Image;
import com.spotify.docker.client.messages.ImageInfo;
import com.spotify.docker.client.messages.ProgressMessage;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
            if (checkForExistingImage(imageName)) {
                return new ActivityResult(ActivityResult.Status.SUCCESS);
            }
            getDocker().pull(imageName, new DockerProgressHandler(imageName));

        } catch (DockerException | DockerCertificateException | InterruptedException e) {
            logger.debug("Got exception from docker for imageName : " + imageName, e);
            return new ActivityResult(ActivityResult.Status.FAILURE, e);
        }

        return new ActivityResult(ActivityResult.Status.SUCCESS);
    }

}
