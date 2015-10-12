package io.sprint0.cli;

import io.sprint0.cli.jobs.FullScaffoldJob;
import io.sprint0.cli.jobs.Job;
import io.sprint0.cli.jobs.NoOpJob;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class Main {

    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    private Job.Status jobStatus;
    private boolean displayedHelp = false;

    public Main(String[] args) throws ParseException {
        Map<String, Supplier<Job>> jobLookup = setupJobLookup();
        Options options = setupOptions();

        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);

        if (commandLine.hasOption("h")) {
            showHelp(options);
            return;
        }

        String command = commandLine.getArgList().get(0);
        if (jobLookup.containsKey(command)) {
            Job job = jobLookup.get(command).get();
            jobStatus = job.execute(commandLine);
            logger.info("Job status : " + jobStatus);
        } else {
            showHelp(options);
        }
    }

    public static void main(String[] args) throws Exception {
        new Main(args);
    }



    private void showHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("sprint0", options);
        displayedHelp = true;
    }

    protected Map<String, Supplier<Job>> setupJobLookup() {
        Map <String, Supplier<Job>> jobLookup = new HashMap<> ();
        jobLookup.put("scaffold", FullScaffoldJob::new);
        jobLookup.put("noop", NoOpJob::new);

        return jobLookup;
    }

    private Options setupOptions() {
        Options options = new Options();
        options.addOption(new Option("h", "help", false, "Display Help"));

        return options;
    }

    public Job.Status getJobStatus() {
        return jobStatus;
    }

    public boolean displayedHelp() {
        return displayedHelp;
    }
}
