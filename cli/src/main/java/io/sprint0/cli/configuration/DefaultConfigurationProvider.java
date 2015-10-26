package io.sprint0.cli.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class DefaultConfigurationProvider implements ConfigurationProvider {

    private String defaultConfigurationJsonFilename = "/defaultConfiguration.json";
    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Configuration provide() {

        try (InputStream stream = this.getClass().getResourceAsStream(defaultConfigurationJsonFilename)) {
            if (stream == null) {
                throw new FileNotFoundException(defaultConfigurationJsonFilename);
            }
            logger.info("Loading default values from : {}", this.getClass().getResource(defaultConfigurationJsonFilename).toURI());
            return mapper.readValue(stream, Configuration.class);
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }
}

