package org.ps.benchmarktool.benchmarking;

import org.ps.benchmarktool.http.DefaultConnectionFactory;
import org.ps.benchmarktool.http.HttpConnectionFactory;
import org.ps.benchmarktool.http.HttpRequestRunner;

import java.net.URL;
import java.time.Duration;

public class BenchmarkTool {
    private int requestCount;
    private URL targetUrl;
    private Duration timeout;
    private int concurrencyLevel;

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public void setTargetUrl(URL url) {
        this.targetUrl = url;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public void setConcurrencyLevel(int concurrencyLevel) {
        this.concurrencyLevel = concurrencyLevel;
    }

    public void runBenchmark() {
        final BenchmarkReportBuilder builder = new BenchmarkReportBuilder();
        final HttpConnectionFactory connectionFactory = new DefaultConnectionFactory(this.timeout);
        final HttpRequestRunner runner = new HttpRequestRunner(connectionFactory, builder, this.concurrencyLevel);

        runner.requestUrlMultipleTimes(this.targetUrl, this.requestCount);

        printReport(builder.getReport());
    }

    private void printReport(BenchmarkReport report) {
        StringBuilder output = new StringBuilder();
        output.append("concurrency level: ")
            .append(report.getConcurrencyLevel())
            .append("\n")
            .append("total time spent:  ")
            .append(formatDuration(report.getRequestsTotalDuration()))
            .append("\n")
            .append("request count:     ")
            .append(report.getTotalCount())
            .append("\n")
            .append("failed count:      ")
            .append(report.getFailedCount())
            .append("\n")
            .append("bytes transmitted: ")
            .append(report.getTransmittedByteCount())
            .append("\n")
            .append("requests per second: ")
            .append(report.getRequestsPerSecond())
            .append("\n")
            .append("average request time: ")
            .append(formatDuration(report.getAverageDuration()))
            .append("\n");

        final int[] percentiles = { 50, 80, 90, 95, 99, 100 };
        output.append("request time percentiles:\n");
        for (int percentile : percentiles) {
            final Duration boundValue = report.getPercentileBoundValue(percentile);
            final String line = String.format("\t%d%%:\t%s\n", percentile, formatDuration(boundValue));
            output.append(line);
        }

        System.out.println(output.toString());
    }

    private String formatDuration(Duration duration) {
        final int millis = (int)(duration.toMillis() % 1000);
        final StringBuilder builder = new StringBuilder();
        builder.append(Long.toString(duration.getSeconds()));
        if (millis != 0) {
            builder.append(".").append(String.format("%03d", millis));
        }
        builder.append(" seconds");

        return builder.toString();
    }
}
