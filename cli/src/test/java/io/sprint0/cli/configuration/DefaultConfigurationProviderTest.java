package io.sprint0.cli.configuration;

import junitx.util.PrivateAccessor;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DefaultConfigurationProviderTest {

    ConfigurationProvider configurationProvider = new DefaultConfigurationProvider();

    @Test
    public void testLoadingCorrectly() {
        Configuration configuration = configurationProvider.provide();

        assertThat(configuration.getCurrentDockerHost(), is("192.168.99.100"));
    }


    @Test(expected=IllegalStateException.class)
    public void testFileNotFound() throws Exception {
        PrivateAccessor.setField(configurationProvider, "defaultConfigurationJsonFilename", "missingfile.path");
        Configuration configuration = configurationProvider.provide();

        assertThat(configuration.getCurrentDockerHost(), is("192.168.99.100"));
    }


}
