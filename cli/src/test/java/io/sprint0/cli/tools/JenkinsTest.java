package io.sprint0.cli.tools;

/**
 *
 */
public class JenkinsTest extends ToolTest <Jenkins> {

    @Override
    public Jenkins createTool() {
        return new Jenkins();
    }
}
