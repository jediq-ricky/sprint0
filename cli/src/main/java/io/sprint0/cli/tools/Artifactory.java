package io.sprint0.cli.tools;

import io.sprint0.cli.configuration.Tool;

/**
 *
 */
public class Artifactory extends Tool {

    public Artifactory() {
        setName("Artifactory");
        setImageRef("jfrog-docker-registry.bintray.io/jfrog/artifactory-pro");
        setVersion("latest");
    }
}
