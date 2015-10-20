package io.sprint0.cli.tools;

import io.sprint0.cli.configuration.Tool;

/**
 *
 */
public class Bloodhound extends Tool {

    public Bloodhound() {
        setName("Bloodhound");
        setImageRef("vanjanssen/apache-bloodhound");
        setVersion("latest");
    }
}
