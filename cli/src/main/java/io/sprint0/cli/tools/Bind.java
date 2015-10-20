package io.sprint0.cli.tools;

import io.sprint0.cli.configuration.Tool;

/**
 * DNS
 */
public class Bind extends Tool {

    public Bind() {
        setName("Bind");
        setImageRef("sameersbn/bind");
        setVersion("latest");
    }
}
