package org.ps.benchmarktool;

import org.apache.commons.cli.*;
import org.ps.benchmarktool.benchmarking.Benchmark;
import org.ps.benchmarktool.benchmarking.BenchmarkBuilder;
import org.ps.benchmarktool.benchmarking.BenchmarkBuilder.InvalidArgumentsException;
import org.ps.benchmarktool.benchmarking.BenchmarkReport;
import org.ps.benchmarktool.benchmarking.BenchmarkReportPrinter;

// TODO: create settings.properties with argument defaults
// TODO: time-outed requests should be accounted as failed
// TODO: fix static analyzer warnings
// TODO: benchmark different local servers with "hello world" server
//       e.g. Node.js, Ruby (Jekyll), Apache, etc.

class Main {
    private static final String DEFAULTS_PATH = "defaults.yml";

    public static void main(String[] arguments) {
        try {
            BenchmarkReport report = prepareBenchmarkTool(arguments).run();
            (new BenchmarkReportPrinter(System.out)).print(report);
        } catch (InvalidArgumentsException e) {
            System.err.println(e.getMessage());
            printUsage(e.getOptions());
            System.exit(1);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private static Benchmark prepareBenchmarkTool(String[] arguments) throws RuntimeException {
        BenchmarkBuilder builder = new BenchmarkBuilder();
        builder.acceptYamlFile(DEFAULTS_PATH);
        builder.acceptCommandLineArguments(arguments);

        return builder.build();
    }

    private static void printUsage(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("benchmarktool", options);
    }
}
