package org.ps.benchmarktool.benchmarking;

import org.ps.benchmarktool.http.DefaultConnectionFactory;
import org.ps.benchmarktool.http.HttpConnectionFactory;
import org.ps.benchmarktool.http.HttpRequestRunner;

class BenchmarkTool implements Benchmark {
    private final BenchmarkSettings settings;

    BenchmarkTool(BenchmarkSettings s) {
        settings = s;
    }

    public BenchmarkReport run() {
        final BenchmarkReportBuilder builder = new BenchmarkReportBuilder(settings);
        final HttpConnectionFactory connectionFactory = new DefaultConnectionFactory(settings.getTimeout());
        final HttpRequestRunner runner = new HttpRequestRunner(connectionFactory, builder, settings.getConcurrencyLevel());

        runner.requestUrlMultipleTimes(settings.getTargetUrl(), settings.getRequestCount());

        return builder.getReport();
    }
}
