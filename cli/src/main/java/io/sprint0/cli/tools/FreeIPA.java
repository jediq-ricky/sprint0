package io.sprint0.cli.tools;

import io.sprint0.cli.configuration.Tool;

/**
 * LDAP
 */
public class FreeIPA extends Tool {

    public FreeIPA() {
        setName("FreeIPA");
        setImageRef("adelton/freeipa-server");
        setVersion("latest");
    }
}
