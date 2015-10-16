package io.sprint0.cli;

import io.sprint0.cli.configuration.ConfigurationStore;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private Job.Status jobStatus;
    private boolean displayedHelp = false;

    public Main(String[] args) throws ParseException {
        Map<String, Supplier<Job>> jobLookup = setupJobLookup();
        Options options = setupOptions();

        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);

        if (commandLine.hasOption("h") || commandLine.getArgList().isEmpty()) {
            showHelp(options);
            return;
        }

        ConfigurationStore configurationStore = new ConfigurationStore();

        String command = commandLine.getArgList().get(0);
        if (jobLookup.containsKey(command)) {
            Job job = jobLookup.get(command).get();
            job.setConfigurationStore(configurationStore    );
            jobStatus = job.execute(commandLine);
            LOGGER.info("Job status : " + jobStatus);
        } else {
            showHelp(options);
        }
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main(args);
        LOGGER.info("Completing sprint0 : " + main.getJobStatus());
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
