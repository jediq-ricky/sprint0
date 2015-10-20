package io.sprint0.cli.tools;

import io.sprint0.cli.configuration.Tool;

/**
 *
 */
public class Dns extends Tool {

    public Dns() {
        setName("Dns");
        setImageRef("sameersbn/bind");
        setVersion("latest");
    }
}
