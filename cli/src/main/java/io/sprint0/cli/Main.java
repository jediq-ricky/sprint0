package io.sprint0.cli;

import io.sprint0.cli.jobs.FullScaffoldJob;
import io.sprint0.cli.jobs.Job;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class Main {

    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    public Main(String[] args) throws ParseException {
        Options options = setupOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);

        if (commandLine.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("sprint0", options);
            return;
        }

        if (commandLine.getArgList().contains("scaffold")) {
            scaffold(commandLine);
        }

    }

    public static void main(String[] args) throws Exception {
        new Main(args);
    }

    private Options setupOptions() {
        Options options = new Options();
        options.addOption(new Option("h", "help", false, "Display Help"));

        return options;
    }

    private void scaffold(CommandLine commandLine) {
        Job job = new FullScaffoldJob();
        Job.Status status = job.execute(commandLine);

        logger.info("status = " + status);
    }
}
