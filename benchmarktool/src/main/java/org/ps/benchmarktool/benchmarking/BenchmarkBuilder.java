package org.ps.benchmarktool.benchmarking;

import org.apache.commons.cli.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BenchmarkBuilder {
    public class InvalidCommandLineArgumentsException extends RuntimeException {
        private final Options options;
        InvalidCommandLineArgumentsException(Options o, ParseException cause) {
            super(cause.getMessage(), cause);
            options = o;
        }

        public final Options getOptions() {
            return options;
        }
    }

    private final BenchmarkTool tool = new BenchmarkTool();

    public void acceptCommandLineArguments(String[] arguments) throws RuntimeException {
        CommandLineParser parser = new DefaultParser();
        Options options = getCommandLineOptions();

        try {
            CommandLine cmd = parser.parse(options, arguments);
            fillFromCommandLineOptions(cmd);
        } catch (ParseException ex) {
            throw new InvalidCommandLineArgumentsException(options, ex);
        }
    }

    public Benchmark build() {
        return tool;
    }

    private void fillFromCommandLineOptions(CommandLine cl) throws RuntimeException {
        setupCommandLineParameter(() -> cl.getOptionValue("url"), this::setTargetUrl);
        setupCommandLineParameter(() -> cl.getOptionValue("num"), this::setRequestCount);
        setupCommandLineParameter(() -> cl.getOptionValue("concurrency"), this::setConcurrencyLevel);
        setupCommandLineParameter(() -> cl.getOptionValue("timeout"), this::setTimeout);
    }

    private void setTargetUrl(String url) throws RuntimeException {
        try {
            tool.setTargetUrl(new URL(url));
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid url", e);
        }
    }

    private interface Setter {
        void set(String value) throws RuntimeException;
    }

    private interface ParameterProvider {
        String get();
    }

    private void setupCommandLineParameter(ParameterProvider pp, Setter s) throws RuntimeException {
        final String v = pp.get();
        if (v != null) {
            s.set(v);
        }
    }

    private void setRequestCount(String num) throws RuntimeException {
        try {
            tool.setRequestCount(Integer.parseUnsignedInt(num));
        } catch (NumberFormatException e) {
            throw new RuntimeException("requests count is not a number", e);
        }
    }

    private void setConcurrencyLevel(String concurrency) throws RuntimeException {
        try {
            tool.setConcurrencyLevel(Integer.parseUnsignedInt(concurrency));
        } catch (NumberFormatException e) {
            throw new RuntimeException("concurrency level is not a number", e);
        }
    }

    private void setTimeout(String timeout) throws RuntimeException {
        try {
            tool.setTimeout(Duration.ofMillis(Integer.parseUnsignedInt(timeout)));
        } catch (NumberFormatException e) {
            throw new RuntimeException("timeout is not a number", e);
        }
    }

    private static Options getCommandLineOptions() {
        Options options = new Options();
        options.addOption(new Option("u", "url", true, "sets target URL"));
        options.addOption(new Option("n", "num", true, "sets target URL requests count"));
        options.addOption(new Option("c", "concurrency", true, "sets concurrency level"));
        options.addOption(new Option("t", "timeout", true, "both connection and read timeouts in milliseconds"));

        return options;
    }
}
