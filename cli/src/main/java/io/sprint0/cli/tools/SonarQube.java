package io.sprint0.cli.tools;

import io.sprint0.cli.configuration.Tool;

/**
 *
 */
public class SonarQube extends Tool {

    public SonarQube() {
        setName("SonarQube");
        setImageRef("sonarqube");
        setVersion("latest");
    }
}
