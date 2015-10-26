package io.sprint0.cli.activities;

import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.ImageNotFoundException;
import com.spotify.docker.client.ImagePullFailedException;
import com.spotify.docker.client.ProgressHandler;
import com.spotify.docker.client.messages.ProgressMessage;

import java.io.PrintStream;

public class DockerProgressHandler implements ProgressHandler {

    private final String image;
    private final PrintStream printStream;

    public DockerProgressHandler(String image, PrintStream printStream) {
        this.image = image;
        this.printStream = printStream;
    }

    public void start() {
        // do nothing by default
    }

    @Override
    public void progress(ProgressMessage message) throws DockerException {
        if (message.error() != null) {
            if (message.error().contains("404") || message.error().contains("not found")) {
                throw new ImageNotFoundException(image, message.toString());
            } else {
                throw new ImagePullFailedException(image, message.toString());
            }
        }

        printStream.print(String.format("\rpull %s: %s", image, message));
    }

    public void stop() {
        printStream.println();
    }


}
