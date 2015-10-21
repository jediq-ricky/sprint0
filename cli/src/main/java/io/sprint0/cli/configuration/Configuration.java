package io.sprint0.cli.configuration;

public class Configuration {

    private String currentDockerProtocol;
    private String currentDockerHost;
    private String currentDockerPort;
    private String currentDockerCertPath;

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
