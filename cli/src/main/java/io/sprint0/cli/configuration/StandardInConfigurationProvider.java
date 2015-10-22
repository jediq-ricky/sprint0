package io.sprint0.cli.configuration;

import java.util.Scanner;

public class StandardInConfigurationProvider implements ConfigurationProvider {


    @Override
    public Configuration provide() {

        Configuration configuration = new Configuration();

        Scanner scanner = new Scanner(System.in);


        System.out.print("Docker host : ");
        configuration.setCurrentDockerHost(scanner.next());

        System.out.print("Docker port : ");
        configuration.setCurrentDockerPort(scanner.next());

        System.out.print("Docker protocol : ");
        configuration.setCurrentDockerProtocol(scanner.next());

        System.out.print("Docker cert path : ");
        configuration.setCurrentDockerCertPath(scanner.next());


        return configuration;
    }
}
