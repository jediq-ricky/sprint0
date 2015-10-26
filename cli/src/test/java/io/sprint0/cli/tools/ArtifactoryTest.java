package io.sprint0.cli.tools;

/**
 *
 */
public class ArtifactoryTest extends ToolTest <Artifactory> {

    @Override
    public Artifactory createTool() {
        return new Artifactory();
    }
}
