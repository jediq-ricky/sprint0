package io.sprint0.cli.activities;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import org.junit.Test;

/**
 *
 */
public class CheckCommandAvailableActivityTest {

    @Test
    public void testAvailableCommand() {
        ActivityResult result = checkCommand("ls", ActivityResult.Status.SUCCESS);
        assertThat(result.getStatus(), is(ActivityResult.Status.SUCCESS));
        assertThat(result.getCause(), is(nullValue()));

    }

    @Test
    public void testNotAvailableCommand() {
        ActivityResult result = checkCommand("missingthing", ActivityResult.Status.FAILURE);
        assertThat(result.getStatus(), is(ActivityResult.Status.FAILURE));
        assertThat(result.getCause(), is(notNullValue()));

    }


    public ActivityResult checkCommand(String commandName, ActivityResult.Status status) {

        CheckCommandAvailableActivity activity = new CheckCommandAvailableActivity() {
            @Override
            public String getCommand() {
                return commandName;
            }
        };

        return activity.go(null);
    }


}
