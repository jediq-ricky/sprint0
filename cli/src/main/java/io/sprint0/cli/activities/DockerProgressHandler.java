package io.sprint0.cli.activities;

import com.spotify.docker.client.LoggingPullHandler;
import com.spotify.docker.client.ProgressHandler;

public class DockerProgressHandler extends LoggingPullHandler implements ProgressHandler {

    public DockerProgressHandler(String image) {
        super(image);
    }
}
