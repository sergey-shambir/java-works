package org.ps.benchmarktool.benchmarking;

import org.junit.Assert;
import org.junit.Test;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.time.Duration;

public class BenchmarkReportPrinterTest extends Assert {
    @Test
    public void testPrintReturnsNotEmptyText() {
        BenchmarkSettings settings = TestUtils.createTestBenchMarkSettings(1, 0);
        final BenchmarkReportBuilder builder = new BenchmarkReportBuilder(settings);
        builder.setTotalDuration(Duration.ZERO);
        final BenchmarkReport report = builder.getReport();

        StringWriter sw = new StringWriter();
        OutputStream os = new OutputStream() {
            @Override
            public void write(int b) {
                sw.write(b);
            }
        };
        BenchmarkReportPrinter printer = new BenchmarkReportPrinter(new PrintStream(os) {
            @Override
            public String toString() {
                return sw.toString();
            }
        });
        printer.print(report);
        assertNotEquals("", sw.toString());
    }
}