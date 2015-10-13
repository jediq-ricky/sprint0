package io.sprint0.cli.activities;

import com.spotify.docker.client.*;
import com.spotify.docker.client.messages.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 */
public abstract class DockerActivity implements Activity {

    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String dockerMachineProtocol = "https";
    protected String dockerMachineHost = "192.168.99.100";
    protected String dockerMachinePort = "2376";
    protected String dockerCertPath = "/Users/rickyw/.docker/machine/machines/default";

    private DockerClient dockerClient;

    public DockerClient getDocker() throws DockerCertificateException {

        if (dockerClient == null) {
            String uri = dockerMachineProtocol + "://" + dockerMachineHost + ":" + dockerMachinePort;
            Path certPath = Paths.get(dockerCertPath);
            DockerCertificates certificates = new DockerCertificates(certPath);
            dockerClient = DefaultDockerClient
                    .fromEnv()
                    .dockerCertificates(certificates)
                    .uri(uri)
                    .build();
        }
        return dockerClient;
    }


    protected boolean checkForExistingImage(String imageName)
            throws DockerException, InterruptedException, DockerCertificateException {

        List<Image> images = getDocker().listImages();
        for (Image image : images) {
            for (String repoTag : image.repoTags()) {
                if (repoTag.startsWith(imageName + ":")) {
                    logger.info("Already pulled image '{}' version number '{}'",
                            imageName, repoTag.substring(repoTag.indexOf(":")+1));
                    return true;
                }
            }
        }
        return false;
    }
}
