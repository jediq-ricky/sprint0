package io.sprint0.cli.activities;

import com.spotify.docker.client.*;
import com.spotify.docker.client.messages.Image;
import io.sprint0.cli.SemVer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 */
public abstract class DockerActivity implements Activity {

    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String dockerMachineProtocol = "https";
    private String dockerMachineHost = "192.168.99.100";
    protected String dockerMachinePort = "2376";
    protected String dockerCertPath = "/Users/rickyw/.docker/machine/machines/default";

    private DockerClient dockerClient;

    public DockerClient getDocker() throws DockerCertificateException {

        if (dockerClient == null) {
            String uri = dockerMachineProtocol + "://" + getDockerMachineHost() + ":" + dockerMachinePort;
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


    protected String findExistingImageId(String imageName)
            throws DockerException, InterruptedException, DockerCertificateException {

        List<Image> images = getDocker().listImages();

        List<Image> matchingImages = images.stream()
                .filter(i -> i.repoTags().stream()
                        .anyMatch(t -> t.startsWith(imageName + ":")))
                .collect(Collectors.toList());

        logger.debug("matchingImages = ", matchingImages);

        if (matchingImages.isEmpty()) {
            return null;
        }

        List<SemVer> semVers = matchingImages.stream()
                .map(i -> i.repoTags().get(0))
                .map(t -> t.split(":")[1])
                .map(v -> new SemVer(v))
                .collect(Collectors.toList());

        semVers.sort(new SemVer.SVComparator());
        SemVer newestVersion = semVers.get(semVers.size()-1);

        String imagePlusVersion = imageName + ":" + newestVersion.getValue();
        logger.debug("newestVersion = {}", imagePlusVersion);

        Optional<Image> first = matchingImages.stream().filter(i -> i.repoTags().stream()
                .anyMatch(t -> t.startsWith(imageName + ":"))).findFirst();

        return first.get().id();
    }

    public String getDockerMachineHost() {
        return dockerMachineHost;
    }
}
