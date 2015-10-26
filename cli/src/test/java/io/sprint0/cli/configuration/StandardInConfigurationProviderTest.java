package io.sprint0.cli.configuration;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class StandardInConfigurationProviderTest {


    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Test
    public void test() {
        systemInMock.provideText("host\nport\nprotocol\ncert\n");

        PrintStream printWriter = System.out;
        StandardInConfigurationProvider configurationProvider = new StandardInConfigurationProvider(printWriter);
        Configuration configuration = configurationProvider.provide();

        assertThat(configuration.getCurrentDockerHost(), is("host"));
        assertThat(configuration.getCurrentDockerPort(), is("port"));
        assertThat(configuration.getCurrentDockerProtocol(), is("protocol"));
        assertThat(configuration.getCurrentDockerCertPath(), is("cert"));
    }

}
