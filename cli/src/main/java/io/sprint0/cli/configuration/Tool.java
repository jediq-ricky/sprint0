package io.sprint0.cli.configuration;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Tool {

    private String name;
    private String imageId;
    private String version;
    private LocalDateTime installDateTime;

    private Map<Integer, Integer> portMappings = new HashMap<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LocalDateTime getInstallDateTime() {
        return installDateTime;
    }

    public void setInstallDateTime(LocalDateTime installDateTime) {
        this.installDateTime = installDateTime;
    }

    public Map<Integer, Integer> getPortMappings() {
        return portMappings;
    }

    public void setPortMappings(Map<Integer, Integer> portMappings) {
        this.portMappings = portMappings;
    }
}
