package io.sprint0.cli.activities;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.Test;

/**
 *
 */
public class CheckCommandAvailableActivityTest {

    @Test
    public void testAvailableCommand() {
        checkCommand("ls", ActivityResult.Status.SUCCESS);
    }

    @Test
    public void testNotAvailableCommand() {
        checkCommand("missingthing", ActivityResult.Status.FAILURE);
    }


    public void checkCommand(String commandName, ActivityResult.Status status) {

        CheckCommmandAvailableActivity activity = new CheckCommmandAvailableActivity() {
            @Override
            public String getCommand() {
                return commandName;
            }
        };

        ActivityResult activityResult = activity.go(null);
        assertThat(activityResult.getStatus(), is(status));
    }


}
