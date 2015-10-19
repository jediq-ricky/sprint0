package io.sprint0.cli.activities;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.messages.*;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class DockerStartActivity extends DockerActivity {

    private final String imageName;

    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    public DockerStartActivity(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public ActivityResult go(CommandLine commandLine) {
        try {
            String imageId = findExistingImageId(imageName);
            if (imageId == null) {
                return new ActivityResult(ActivityResult.Status.FAILURE, "We haven't pulled image : " + imageName);
            }

            DockerClient docker = getDocker();

            String[] ports = {"80", "22"};
            final Map<String, List<PortBinding>> portBindings = getPortBindings(ports);
            HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();

            ContainerConfig containerConfig = ContainerConfig.builder()
                    .image(imageId).exposedPorts(ports)
              .build();

            ContainerCreation creation = docker.createContainer(containerConfig);
            String id = creation.id();


            ContainerInfo info = docker.inspectContainer(id);
            System.out.println("info = " + info);

            docker.startContainer(id, hostConfig);

            ContainerInfo infoAfter = docker.inspectContainer(id);
            System.out.println("infoAfter = " + infoAfter);

        } catch (DockerException | DockerCertificateException | InterruptedException e) {
            logger.debug("Got exception from docker for imageName : " + imageName, e);
            return new ActivityResult(ActivityResult.Status.FAILURE, e);
        }

        return new ActivityResult(ActivityResult.Status.SUCCESS);
    }

    private Map<String, List<PortBinding>> getPortBindings(String[] ports) {
        final Map<String, List<PortBinding>> portBindings = new HashMap<>();
        for (String port : ports) {
            List<PortBinding> hostPorts = new ArrayList<>();
            hostPorts.add(PortBinding.of(config.getCurrentDockerHost(), port));
            portBindings.put(port, hostPorts);
        }
        return portBindings;
    }

}
