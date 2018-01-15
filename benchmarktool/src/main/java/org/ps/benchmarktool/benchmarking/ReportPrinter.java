package org.ps.benchmarktool.benchmarking;

import java.io.PrintStream;
import java.time.Duration;
import java.util.Arrays;

public class ReportPrinter {
    private final int[] PERCENTILES = new int[]{50, 80, 90, 95, 99, 100};
    private final PrintStream stream;

    public ReportPrinter(PrintStream ps) {
        stream = ps;
    }

    public void print(BenchmarkReport report) {
        BenchmarkSettings settings = report.getSettings();
        stream.printf("url:                     %s\n", settings.getTargetUrl().toString());
        stream.printf("concurrency level:       %s\n", settings.getConcurrencyLevel());
        stream.printf("total time spent:        %s\n", formatDuration(report.getRequestsTotalDuration()));
        stream.printf("request count:           %d\n", report.getTotalCount());
        stream.printf("killed by timeout count: %d (timeout %s)\n", report.getRequestsKilledByTimeoutCount(), settings.getTimeout().toString());
        stream.printf("failed count:            %d\n", report.getFailedCount());
        stream.printf("bytes transmitted:       %d\n", report.getTransmittedByteCount());
        stream.printf("requests per second:     %f\n", report.getRequestsPerSecond());
        stream.printf("average request time:    %s\n", formatDuration(report.getAverageDuration()));

        stream.println("request time percentiles:");
        Arrays.stream(PERCENTILES).forEach(percentile -> this.printPercentile(report, percentile));
    }

    private void printPercentile(BenchmarkReport report, int percentile) {
        stream.printf("\t%d%%:\t%s\n", percentile, formatDuration(report.getPercentileBoundValue(percentile)));
    }

    private String formatDuration(Duration duration) {
        final int millis = (int) (duration.toMillis() % 1000);
        final StringBuilder builder = new StringBuilder();
        builder.append(Long.toString(duration.getSeconds()));
        if (millis != 0) {
            builder.append(".").append(String.format("%03d", millis));
        }
        builder.append(" seconds");

        return builder.toString();
    }
}
