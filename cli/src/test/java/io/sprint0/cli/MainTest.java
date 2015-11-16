package io.sprint0.cli;

import io.sprint0.cli.jobs.Job;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

/**
 *
 */
public class MainTest {


    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void testNoop() throws Exception {
        Main main = new Main(new String[] { "noop" });
        assertThat(main.getJobStatus(), is(Job.Status.SUCCESS));
        assertThat(main.displayedHelp(), is(false));
    }


    @Test
    public void testNoopViaMainMethod() throws Exception {
        exit.expectSystemExit();
        Main.main(new String[] { "noop" });
    }

    @Test
    public void testHelp() throws Exception {
        Main main = new Main(new String[] { "-h" });
        assertThat(main.getJobStatus(), is(nullValue()));
        assertThat(main.displayedHelp(), is(true));

    }

    @Test
    public void testUnknownJob() throws Exception {
        Main main = new Main(new String[] { "unknownJob" });
        assertThat(main.getJobStatus(), is(nullValue()));
        assertThat(main.displayedHelp(), is(true));
    }
}
