package io.sprint0.cli.configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tool {

    private String name;
    private String imageId;
    private String imageRef;
    private String version;
    private String [] ports = new String [0];
    private LocalDateTime installDateTime;
    private List<String> instances = new ArrayList<>();

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

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

    public String[] getPorts() {
        return ports;
    }

    public void setPorts(String ... ports) {
        this.ports = ports;
    }

    @Override
    public String toString() {
        return getName();
    }

    public void addInstance(String instanceId) {
        this.instances.add(instanceId);
    }

    public List<String> getInstances() {
        return instances;
    }
}
