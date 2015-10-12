package io.sprint0.cli.activities;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;

/**
 *
 */
public abstract class DockerActivity implements Activity {

    protected String dockerMachineProtocol = "http";
    protected String dockerMachineHost = "localhost";
    protected String dockerMachinePort = "3376";

    public DockerClient getDocker() throws DockerCertificateException {
        String uri = dockerMachineProtocol + "://" + dockerMachineHost + ":" + dockerMachinePort;
        return DefaultDockerClient
                .fromEnv()
                .uri(uri)
                .build();
    }
}
