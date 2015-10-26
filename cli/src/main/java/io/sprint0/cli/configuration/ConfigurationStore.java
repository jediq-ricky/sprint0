package io.sprint0.cli.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigurationStore {

    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    private ObjectMapper mapper = new ObjectMapper();

    private ConfigurationProvider configurationProvider = new DefaultConfigurationProvider();

    public Configuration loadConfiguration() {
        Path filePath = findConfigPath();
        try {
            if (filePath.toFile().exists()) {
                return mapper.readValue(filePath.toFile(), Configuration.class);
            } else {
                logger.info("Could not load config file from : {}, creating new configuration.", filePath);

                Configuration configuration = configurationProvider.provide();
                saveConfiguration(configuration);
                return configuration;
            }
        } catch (IllegalStateException | IOException e) {
            throw new IllegalStateException("Could not load config file from :" + filePath, e);
        }
    }

    public void saveConfiguration(Configuration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("Configuration cannot be null");
        }
        Path filePath = findConfigPath();
        try {

            Files.createDirectories(filePath.getParent());
            logger.info("Writing config file to : {}.", filePath);

            mapper.writeValue(filePath.toFile(), configuration);
        } catch (IOException e) {
            throw new IllegalStateException("Could not save config file to :" + filePath, e);
        }
    }

    public void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
    }

    public void removeConfiguration() {
        Path filePath = findConfigPath();
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new IllegalStateException("Could not remove config file from : " + filePath, e);
        }
    }

    public Installation loadInstallation(String host) {
        Path filePath = findInstallPath(host);
        try {
            return mapper.readValue(filePath.toFile(), Installation.class);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load file for host : " + host, e);
        }
    }

    public void saveInstallation(String host, Installation installation) {
        if (host == null) {
            throw new IllegalArgumentException("Host cannot be null");
        }
        if (installation == null) {
            throw new IllegalArgumentException("Installation cannot be null");
        }
        Path filePath = findInstallPath(host);
        try {
            Files.createDirectories(filePath.getParent());
            mapper.writeValue(filePath.toFile(), installation);
        } catch (IOException e) {
            throw new IllegalStateException("Could not save file for host : " + host, e);
        }
    }

    public void removeInstallation(String host) {
        Path filePath = findInstallPath(host);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new IllegalStateException("Could not remove file for host : " + host, e);
        }
    }

    private Path findConfigPath() {
        String home = System.getProperty("user.home");
        return Paths.get(home, ".sprint0", "config");
    }


    private Path findInstallPath(String host) {
        return findConfigPath().getParent().resolve(Paths.get("hosts", host + ".json"));
    }
}
