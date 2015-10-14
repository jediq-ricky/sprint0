package io.sprint0.cli.installation;

import java.util.ArrayList;
import java.util.List;

public class Installation {

    private String host;
    private List<Tool> tools = new ArrayList<>();


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }
}
