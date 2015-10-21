package io.sprint0.cli.activities;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerException;
import io.sprint0.cli.configuration.Tool;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class DockerPullActivity extends DockerActivity {

    private final Tool tool;

    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    public DockerPullActivity(Tool tool) {
        this.tool = tool;
    }

    @Override
    public ActivityResult go(CommandLine commandLine) {
        try {
            if (findExistingImageId(tool.getImageRef()) != null) {
                return new ActivityResult(ActivityResult.Status.SUCCESS);
            }
            getDocker().pull(tool.getImageRef(), new DockerProgressHandler(tool.getImageRef()));

        } catch (DockerException | DockerCertificateException | InterruptedException e) {
            logger.debug("Got exception from docker for imageName : " + tool, e);
            return new ActivityResult(ActivityResult.Status.FAILURE, e);
        }

        return new ActivityResult(ActivityResult.Status.SUCCESS);
    }

    @Override
    public String toString() {
        return "DockerPull : (" + tool + ")";
    }
}
