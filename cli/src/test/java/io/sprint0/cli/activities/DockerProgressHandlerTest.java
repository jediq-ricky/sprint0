package io.sprint0.cli.activities;

import com.spotify.docker.client.ImageNotFoundException;
import com.spotify.docker.client.ImagePullFailedException;
import com.spotify.docker.client.messages.ProgressMessage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DockerProgressHandlerTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void testNormalFlow() throws Exception {
        DockerProgressHandler handler = new DockerProgressHandler("tool", System.out);
        handler.start();
        ProgressMessage message = new ProgressMessage();
        handler.progress(message);
        handler.stop();
        String msg = "\rpull tool: ProgressMessage{id=null, status=null, stream=null, error=null, progress=null, progressDetail=null}\n";
        assertThat(systemOutRule.getLog(), is(msg));
    }

    @Test(expected= ImageNotFoundException.class)
    public void testNotFound() throws Exception {
        DockerProgressHandler handler = new DockerProgressHandler("tool", System.out);
        ProgressMessage message = new ProgressMessage();
        message.error("404");
        handler.progress(message);
    }

    @Test(expected= ImagePullFailedException.class)
    public void testNotPulled() throws Exception {
        DockerProgressHandler handler = new DockerProgressHandler("tool", System.out);
        ProgressMessage message = new ProgressMessage();
        message.error("failed");
        handler.progress(message);
    }
}
