package io.sprint0.cli.tools;

/**
 *
 */
public class NginxTest extends ToolTest <Nginx> {

    @Override
    public Nginx createTool() {
        return new Nginx();
    }
}
