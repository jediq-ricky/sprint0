package io.sprint0.cli.tools;

import io.sprint0.cli.configuration.Tool;

/**
 *
 */
public class Nginx extends Tool {

    public Nginx() {
        setName("Nginx");
        setImageRef("nginx");
        setVersion("latest");
    }
}
