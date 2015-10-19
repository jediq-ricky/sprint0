package io.sprint0.cli.activities;

import com.spotify.docker.client.*;
import com.spotify.docker.client.messages.Image;
import io.sprint0.cli.SemVer;
import io.sprint0.cli.configuration.Configuration;
import io.sprint0.cli.jobs.Job;
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

    protected Job job;
    protected Configuration config;

    private DockerClient dockerClient;

    public DockerClient getDocker() throws DockerCertificateException {

        if (dockerClient == null) {

            String uri = config.getCurrentDockerProtocol()
                    + "://" + config.getCurrentDockerHost()
                    + ":" + config.getCurrentDockerPort();
            Path certPath = Paths.get(config.getCurrentDockerCertPath());
            DockerCertificates certificates = new DockerCertificates(certPath);
            dockerClient = DefaultDockerClient
                    .fromEnv()
                    .dockerCertificates(certificates)
                    .uri(uri)
                    .build();
        }
        return dockerClient;
    }

    @Override
    public void setJob(Job job) {
        this.job = job;
        config = job.getConfigurationStore().loadConfiguration();
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
}
