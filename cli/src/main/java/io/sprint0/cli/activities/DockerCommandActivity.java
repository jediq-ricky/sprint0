package io.sprint0.cli.activities;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.LogStream;
import io.sprint0.cli.configuration.Tool;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class DockerCommandActivity extends DockerActivity {

    private final Tool tool;

    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String[] command;

    public DockerCommandActivity(Tool tool, String ... command) {

        this.tool = tool;
        this.command = command;
    }

    @Override
    public ActivityResult go(CommandLine commandLine) {

        try {

            logger.info("Executing command : " + Arrays.toString(command));

            DockerClient docker = getDocker();
            String instanceId = tool.getInstances().get(0);
            String execId = docker.execCreate(instanceId, command,
                    DockerClient.ExecParameter.STDOUT,
                    DockerClient.ExecParameter.STDERR);
            LogStream output = docker.execStart(execId);
            String execOutput = output.readFully();

            logger.info("Command output : " + execOutput);

            return new ActivityResult(ActivityResult.Status.SUCCESS);

        } catch (DockerException | DockerCertificateException | InterruptedException e) {
            logger.debug("Got exception from docker for : {} : issuing command : {}", tool, command, e.getMessage());
            return new ActivityResult(ActivityResult.Status.FAILURE, e);
        }

    }
}
