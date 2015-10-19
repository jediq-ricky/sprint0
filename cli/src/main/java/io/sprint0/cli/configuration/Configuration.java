package io.sprint0.cli.configuration;

public class Configuration {

    private String currentDockerHost = "https";
    private String currentDockerProtocol = "192.168.99.100";
    private String currentDockerPort = "2376";
    private String currentDockerCertPath= "/Users/rickywalker/.docker/machine/machines/default";

    public String getCurrentDockerHost() {
        return currentDockerHost;
    }

    public void setCurrentDockerHost(String currentHost) {
        this.currentDockerHost = currentHost;
    }

    public String getCurrentDockerProtocol() {
        return currentDockerProtocol;
    }

    public void setCurrentDockerProtocol(String currentDockerProtocol) {
        this.currentDockerProtocol = currentDockerProtocol;
    }

    public String getCurrentDockerPort() {
        return currentDockerPort;
    }

    public void setCurrentDockerPort(String currentDockerPort) {
        this.currentDockerPort = currentDockerPort;
    }

    public String getCurrentDockerCertPath() {
        return currentDockerCertPath;
    }

    public void setCurrentDockerCertPath(String currentDockerCertPath) {
        this.currentDockerCertPath = currentDockerCertPath;
    }
}
