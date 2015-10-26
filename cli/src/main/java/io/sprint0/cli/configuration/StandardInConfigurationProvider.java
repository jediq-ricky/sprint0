package io.sprint0.cli.configuration;

import java.io.PrintStream;
import java.util.Scanner;

public class StandardInConfigurationProvider implements ConfigurationProvider {

    private final PrintStream printStream;

    public StandardInConfigurationProvider(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public Configuration provide() {

        Configuration configuration = new Configuration();

        Scanner scanner = new Scanner(System.in);


        printStream.print("Docker host : ");
        configuration.setCurrentDockerHost(scanner.next());

        printStream.print("Docker port : ");
        configuration.setCurrentDockerPort(scanner.next());

        printStream.print("Docker protocol : ");
        configuration.setCurrentDockerProtocol(scanner.next());

        printStream.print("Docker cert path : ");
        configuration.setCurrentDockerCertPath(scanner.next());


        return configuration;
    }
}
