package io.sprint0.cli.tools;

/**
 *
 */
public class SonarQubeTest extends ToolTest <SonarQube> {

    @Override
    public SonarQube createTool() {
        return new SonarQube();
    }
}
