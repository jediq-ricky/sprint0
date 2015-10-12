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
        ActivityResult result = checkCommand("ls");
        assertThat(result.getStatus(), is(ActivityResult.Status.SUCCESS));
        assertThat(result.getCause(), is(nullValue()));
    }

    @Test
    public void testNotAvailableCommand() {
        ActivityResult result = checkCommand("missingthing");
        assertThat(result.getStatus(), is(ActivityResult.Status.FAILURE));
        assertThat(result.getCause(), is(notNullValue()));
    }

    @Test
    public void testToString() {
        Activity activity = new CheckCommandAvailableActivity("mine");
        assertThat(activity.toString(), is("Check command available activity : mine"));

    }

    public ActivityResult checkCommand(String commandName) {
        return new CheckCommandAvailableActivity(commandName).go(null);
    }


}
