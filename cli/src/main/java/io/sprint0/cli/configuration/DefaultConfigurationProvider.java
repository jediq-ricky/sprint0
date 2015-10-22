package io.sprint0.cli.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class DefaultConfigurationProvider implements ConfigurationProvider {

    public static final String DEFAULT_CONFIGURATION_JSON_FILENAME = "/defaultConfiguration.json";
    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Configuration provide() {

        try (InputStream stream = this.getClass().getResourceAsStream(DEFAULT_CONFIGURATION_JSON_FILENAME)) {
            logger.info("Loading default values from : {}", this.getClass().getResource(DEFAULT_CONFIGURATION_JSON_FILENAME).toURI());
            return mapper.readValue(stream, Configuration.class);
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }
}

