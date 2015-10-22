package io.sprint0.cli.activities;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.messages.*;
import io.sprint0.cli.configuration.Configuration;
import io.sprint0.cli.configuration.Tool;
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

    private final Tool tool;

    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    public DockerStartActivity(Tool tool) {
        this.tool = tool;
    }


    @Override
    public ActivityResult go(CommandLine commandLine) {
        try {
            String imageId = findExistingImageId(tool.getImageRef());
            if (imageId == null) {
                return new ActivityResult(ActivityResult.Status.FAILURE, "We haven't pulled image : " + tool.getImageRef());
            }

            DockerClient docker = getDocker();

            Container container = findExistingContainer(tool);
            if (container !=null) {
                if (container.status().startsWith("Up ")) {
                    logger.info("Container is already started for {}.", tool);
                    tool.addInstance(container.id());
                    return new ActivityResult(ActivityResult.Status.SUCCESS);
                }

                logger.info("Already created container for {}, starting it now.", tool);
                docker.startContainer(container.id());
                tool.addInstance(container.id());
                return new ActivityResult(ActivityResult.Status.SUCCESS);
            }


            String[] ports = tool.getPorts();
            final Map<String, List<PortBinding>> portBindings = getPortBindings(ports);
            HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();

            ContainerConfig containerConfig = ContainerConfig.builder()
                    .image(imageId)
                    .exposedPorts(ports)
                    .build();

            String containerName = tool.getName() + "_" + tool.getInstances().size();
            ContainerCreation creation = docker.createContainer(containerConfig, containerName);


            String id = creation.id();

            ContainerInfo info = docker.inspectContainer(id);
            logger.info("Starting container : " + info);

            docker.startContainer(id, hostConfig);

            tool.addInstance(id);


        } catch (DockerException | DockerCertificateException | InterruptedException e) {
            logger.debug("Got exception from docker for : " + tool, e);
            return new ActivityResult(ActivityResult.Status.FAILURE, e);
        }

        return new ActivityResult(ActivityResult.Status.SUCCESS);
    }

    private Container findExistingContainer(Tool tool)
            throws DockerException, DockerCertificateException, InterruptedException {
        String name = "/" + tool.getName() + "_" + tool.getInstances().size();
        DockerClient.ListContainersParam listContainersParam = new DockerClient.ListContainersParam("all", "true");
        List<Container> containers = getDocker().listContainers(listContainersParam);
        for (Container container : containers) {
            if (container.names().contains(name)) {
                return container;
            }
        }
        return null;
    }

    private Map<String, List<PortBinding>> getPortBindings(String[] ports) {
        final Map<String, List<PortBinding>> portBindings = new HashMap<>();
        Configuration config = job.getConfigurationStore().loadConfiguration();

        for (String port : ports) {
            List<PortBinding> hostPorts = new ArrayList<>();
            hostPorts.add(PortBinding.of(config.getCurrentDockerHost(), port));
            portBindings.put(port, hostPorts);
        }
        return portBindings;
    }

    @Override
    public String toString() {
        return "DockerStart : (" + tool + ")";
    }
}
