package org.ps.benchmarktool;

import org.apache.commons.cli.*;
import org.ps.benchmarktool.benchmarking.BenchmarkTool;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Main {
    private static final int DEFAULT_REQUEST_COUNT = 100;
    private static final Duration DEFAULT_REQUEST_TIMEOUT = Duration.ofSeconds(40);

    public static void main(String[] arguments) {
        BenchmarkTool tool = new BenchmarkTool();

        try {
            CommandLine commandLine = parseCommandLine(arguments);
            tool.setTargetUrl(getTargetUrl(commandLine));
            tool.setRequestCount(getRequestsCount(commandLine));
            tool.setConcurrencyLevel(getConcurrencyCount(commandLine));
            tool.setTimeout(getRequestsTimeout(commandLine));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            printUsage(getCommandLineOptions());
            System.exit(1);
        }

        try {
            tool.runBenchmark();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private static URL getTargetUrl(CommandLine commandLine) throws MalformedURLException {
        final String url = commandLine.getOptionValue("url");
        if (url != null)
        {
            return new URL(url);
        }
        throw new RuntimeException("missing required argument: url");
    }

    private static int getConcurrencyCount(CommandLine commandLine) {
        final String optionValue = commandLine.getOptionValue("concurrency");
        if (optionValue != null) {
            return Integer.parseUnsignedInt(optionValue);
        }
        return Runtime.getRuntime().availableProcessors();
    }

    private static int getRequestsCount(CommandLine commandLine) {
        final String optionValue = commandLine.getOptionValue("num");
        if (optionValue != null) {
            return Integer.parseUnsignedInt(optionValue);
        }
        return DEFAULT_REQUEST_COUNT;
    }

    private static Duration getRequestsTimeout(CommandLine commandLine) {
        final String optionValue = commandLine.getOptionValue("timeout");
        if (optionValue != null) {
            return Duration.ofMillis(Integer.parseUnsignedInt(optionValue));
        }
        return DEFAULT_REQUEST_TIMEOUT;
    }

    private static CommandLine parseCommandLine(String[] arguments) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        Options options = getCommandLineOptions();

        try {
            cmd = parser.parse(options, arguments);
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
            printUsage(options);
            System.exit(1);
        }

        return cmd;
    }

    private static Options getCommandLineOptions() {
        Options options = new Options();
        Option url = new Option("url", "url", true, "sets target URL");
        options.addOption(url);

        Option num = new Option("num", "num", true, "sets target URL requests count");
        options.addOption(num);

        Option concurrency = new Option("concurrency", "concurrency", true, "sets concurrency level");
        options.addOption(concurrency);

        Option timeout = new Option("timeout", "timeout", true, "both connection and read timeouts");
        options.addOption(timeout);

        return options;
    }

    private static void printUsage(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("benchmarktool", options);
    }
}
