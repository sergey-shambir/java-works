package org.ps.benchmarktool.benchmarking;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.apache.commons.cli.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

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

    class InvalidYamlFileException extends RuntimeException {
        InvalidYamlFileException(YamlException e) {
            super("invalid yaml file: " + e.getMessage(), e);
        }
    }

    private final BenchmarkSettingsImpl settings = new BenchmarkSettingsImpl();

    public void acceptYamlFile(String path) throws RuntimeException {
        try {
            YamlReader reader = new YamlReader(new FileReader(path));
            Map data = reader.read(Map.class);
            if (data != null) {
                fillFromMap(data);
            }
        } catch (YamlException e) {
            throw new InvalidYamlFileException(e);
        } catch (FileNotFoundException e) {
            // ignore
        }
    }

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

    public Benchmark build() throws RuntimeException {
        validateSettings(settings);
        return new BenchmarkTool(settings);
    }

    private void validateSettings(BenchmarkSettings settings) throws RuntimeException {
        if (settings.getTargetUrl() == null) {
            throw new RuntimeException("url is a required parameter");
        }
    }

    private void fillFromMap(Map m) throws RuntimeException {
        setupParameter(stringOrNull(m.get("url")), this::setTargetUrl);
        setupParameter(stringOrNull(m.get("num")), this::setRequestCount);
        setupParameter(stringOrNull(m.get("concurrency")), this::setConcurrencyLevel);
        setupParameter(stringOrNull(m.get("timeout")), this::setTimeout);
    }

    private void fillFromCommandLineOptions(CommandLine cl) throws RuntimeException {
        setupParameter(() -> cl.getOptionValue("url"), this::setTargetUrl);
        setupParameter(() -> cl.getOptionValue("num"), this::setRequestCount);
        setupParameter(() -> cl.getOptionValue("concurrency"), this::setConcurrencyLevel);
        setupParameter(() -> cl.getOptionValue("timeout"), this::setTimeout);
    }

    private ParameterProvider stringOrNull(Object o) {
        return () -> o instanceof String ? o.toString() : null;
    }

    private interface Setter {
        void set(String value) throws RuntimeException;
    }

    private interface ParameterProvider {
        String get();
    }

    private void setupParameter(ParameterProvider pp, Setter s) throws RuntimeException {
        final String v = pp.get();
        if (v != null && !v.isEmpty()) {
            s.set(v);
        }
    }

    private void setTargetUrl(String url) throws RuntimeException {
        try {
            settings.setTargetUrl(new URL(url));
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid url", e);
        }
    }

    private void setRequestCount(String num) throws RuntimeException {
        try {
            settings.setRequestCount(Integer.parseUnsignedInt(num));
        } catch (NumberFormatException e) {
            throw new RuntimeException("requests count is not a number", e);
        }
    }

    private void setConcurrencyLevel(String concurrency) throws RuntimeException {
        try {
            settings.setConcurrencyLevel(Integer.parseUnsignedInt(concurrency));
        } catch (NumberFormatException e) {
            throw new RuntimeException("concurrency level is not a number", e);
        }
    }

    private void setTimeout(String timeout) throws RuntimeException {
        try {
            settings.setTimeout(Duration.ofMillis(Integer.parseUnsignedInt(timeout)));
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
