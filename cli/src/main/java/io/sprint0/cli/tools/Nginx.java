package io.sprint0.cli.tools;

import io.sprint0.cli.configuration.Tool;

/**
 *
 */
public class Nginx extends Tool {

    public Nginx() {
        setName("sprint0_nginx");
        setImageRef("sprint0/nginx");
        setVersion("latest");
        setPorts("80");
    }
}
