package io.sprint0.cli.configuration;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DefaultConfigurationProviderTest {

    ConfigurationProvider configurationProvider = new DefaultConfigurationProvider();

    @Test
    public void test() {
        Configuration configuration = configurationProvider.provide();

        assertThat(configuration.getCurrentDockerHost(), is("192.168.99.100"));
    }
}
