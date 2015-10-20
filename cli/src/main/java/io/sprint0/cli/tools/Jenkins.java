package io.sprint0.cli.tools;

import io.sprint0.cli.configuration.Tool;

/**
 *
 */
public class Jenkins extends Tool {

    public Jenkins() {
        setName("Jenkins");
        setImageRef("jenkins");
        setVersion("latest");
        setPorts("8080");
    }
}
