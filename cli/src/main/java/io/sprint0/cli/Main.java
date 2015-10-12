package io.sprint0.cli;

import io.sprint0.cli.jobs.FullScaffoldJob;
import io.sprint0.cli.jobs.Job;
import org.apache.commons.cli.*;

/**
 *
 */
public class Main {

    public static void main(String[] args) throws Exception {
        new Main(args);
    }

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
            Job job = new FullScaffoldJob();
            Job.Status status = job.execute(commandLine);

            System.out.println("status = " + status);
        }

    }

    private Options setupOptions() {
        Options options = new Options();
        options.addOption(new Option("h", "help", false, "Display Help"));

        return options;
    }

    private void scaffold() {
        System.out.println("Welcome to sprint0, thanks for calling: sprint0 -scaffold");
    }
}
