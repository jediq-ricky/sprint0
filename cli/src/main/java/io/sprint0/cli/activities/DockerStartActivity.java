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

import java.util.*;

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

            ContainerConfig containerConfig = ContainerConfig.builder()
                    .image(imageId)
                    .exposedPorts(ports)
                    .build();

            String containerName = tool.getName();
            ContainerCreation creation = docker.createContainer(containerConfig, containerName);


            String id = creation.id();

            ContainerInfo info = docker.inspectContainer(id);
            logger.info("Starting container : " + info);

            final Map<String, List<PortBinding>> portBindings = getPortBindings(ports);
            HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();

            docker.startContainer(id, hostConfig);

            tool.addInstance(id);


        } catch (DockerException | DockerCertificateException | InterruptedException e) {
            String thisClassName = this.getClass().getCanonicalName();
            int lineNum = Arrays.stream(e.getStackTrace())
                    .filter(el -> el.getClassName().equals(thisClassName))
                    .findFirst().get().getLineNumber();
            logger.debug(String.format("Got %s at line %s from docker for %s with message %s",
                    e.getClass().getSimpleName(), lineNum, tool, e.getMessage()), e);

            return new ActivityResult(ActivityResult.Status.FAILURE, e);
        }

        return new ActivityResult(ActivityResult.Status.SUCCESS);
    }

    private Container findExistingContainer(Tool tool)
            throws DockerException, DockerCertificateException, InterruptedException {
        String name = "/" + tool.getName();
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
