package org.ps.benchmarktool.benchmarking;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;

public class BenchmarkReportTest extends Assert {

    @Test
    public void testEmptyReport() {
        final BenchmarkReportBuilder builder = new BenchmarkReportBuilder();
        builder.setRequestsStats(1, 0, Duration.ZERO);
        final BenchmarkReport report = builder.getReport();

        assertEquals(Duration.ZERO, report.getAverageDuration());
        assertEquals(Duration.ZERO, report.getRequestsTotalDuration());
        assertEquals(1, report.getConcurrencyLevel());
        assertEquals(0, report.getRequestsPerSecond(), 0.01);
        assertEquals(0, report.getTransmittedByteCount(), 0.01);
        assertEquals(0, report.getTotalCount());
        assertEquals(0, report.getFailedCount());

        assertEquals(Duration.ZERO, report.getPercentileBoundValue(1));
        assertEquals(Duration.ZERO, report.getPercentileBoundValue(100));
    }

    @Test
    public void testReportBuildingError() {
        final BenchmarkReportBuilder builder = new BenchmarkReportBuilder();
        builder.onRequestError(new RuntimeException("simulated error"));
        builder.onRequestComplete(Duration.ofSeconds(2), 10, 200);
        builder.setRequestsStats(1, 0, Duration.ZERO);

        boolean thrown = false;
        try
        {
            builder.getReport();
        }
        catch (RuntimeException ex)
        {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testReportBuildingForEventRequestsCount() {
        // Sorted: 1, 1, 7, 29, 50, 99, 100, 307, 2000, 5000
        Duration[] durations = {
                Duration.ofMillis(1), Duration.ofMillis(50), Duration.ofMillis(99),
                Duration.ofMillis(307), Duration.ofMillis(2000), Duration.ofMillis(5000),
                Duration.ofMillis(7), Duration.ofMillis(29), Duration.ofMillis(1),
                Duration.ofMillis(100)
        };

        final BenchmarkReportBuilder builder = new BenchmarkReportBuilder();
        for (Duration duration : durations) {
            builder.onRequestComplete(duration, 50, 200);
        }
        builder.setRequestsStats(2, durations.length, Duration.ofSeconds(5));
        final BenchmarkReport report = builder.getReport();

        assertEquals(Duration.ofNanos(759400000), report.getAverageDuration());
        assertEquals(Duration.ofSeconds(5), report.getRequestsTotalDuration());
        assertEquals(2, report.getConcurrencyLevel());
        assertEquals(2, report.getRequestsPerSecond(), 0.01);
        assertEquals(500, report.getTransmittedByteCount(), 0.01);
        assertEquals(durations.length, report.getTotalCount());
        assertEquals(0, report.getFailedCount());

        assertEquals(Duration.ZERO, report.getPercentileBoundValue(-1));
        assertEquals(Duration.ZERO, report.getPercentileBoundValue(0));
        assertEquals(Duration.ZERO, report.getPercentileBoundValue(1));
        assertEquals(Duration.ofMillis(50), report.getPercentileBoundValue(50));
        assertEquals(Duration.ofMillis(100), report.getPercentileBoundValue(75));
        assertEquals(Duration.ofMillis(2000), report.getPercentileBoundValue(90));
        assertEquals(Duration.ofMillis(5000), report.getPercentileBoundValue(100));
        assertEquals(Duration.ofMillis(5000), report.getPercentileBoundValue(120));
    }


    @Test
    public void testReportBuildingForOddRequestsCount() {
        // Sorted: 1, 1, 7, 29, 50, 99, 100, 170, 307, 2000, 5000
        Duration[] durations = {
                Duration.ofMillis(1), Duration.ofMillis(50), Duration.ofMillis(99),
                Duration.ofMillis(307), Duration.ofMillis(2000), Duration.ofMillis(5000),
                Duration.ofMillis(7), Duration.ofMillis(29), Duration.ofMillis(1),
                Duration.ofMillis(170), Duration.ofMillis(100)
        };

        final BenchmarkReportBuilder builder = new BenchmarkReportBuilder();
        for (Duration duration : durations) {
            builder.onRequestComplete(duration, 50, 200);
        }
        builder.setRequestsStats(2, durations.length, Duration.ofSeconds(5));
        final BenchmarkReport report = builder.getReport();

        assertEquals(705, report.getAverageDuration().toMillis());
        assertEquals(Duration.ofSeconds(5), report.getRequestsTotalDuration());
        assertEquals(2, report.getConcurrencyLevel());
        assertEquals(2.2, report.getRequestsPerSecond(), 0.01);
        assertEquals(550, report.getTransmittedByteCount(), 0.01);
        assertEquals(durations.length, report.getTotalCount());
        assertEquals(0, report.getFailedCount());

        assertEquals(Duration.ZERO, report.getPercentileBoundValue(-1));
        assertEquals(Duration.ZERO, report.getPercentileBoundValue(0));
        assertEquals(Duration.ZERO, report.getPercentileBoundValue(1));
        assertEquals(Duration.ofMillis(50), report.getPercentileBoundValue(50));
        assertEquals(Duration.ofMillis(170), report.getPercentileBoundValue(75));
        assertEquals(Duration.ofMillis(307), report.getPercentileBoundValue(90));
        assertEquals(Duration.ofMillis(5000), report.getPercentileBoundValue(100));
        assertEquals(Duration.ofMillis(5000), report.getPercentileBoundValue(120));
    }
}
