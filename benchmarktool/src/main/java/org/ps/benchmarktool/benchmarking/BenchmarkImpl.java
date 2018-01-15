package org.ps.benchmarktool.benchmarking;

import org.ps.benchmarktool.http.DefaultConnectionFactory;
import org.ps.benchmarktool.http.HttpConnectionFactory;
import org.ps.benchmarktool.http.HttpRequestRunner;

class BenchmarkImpl implements Benchmark {
    private final BenchmarkSettings settings;

    BenchmarkImpl(BenchmarkSettings s) {
        settings = s;
    }

    @Override
    public BenchmarkSettings getSettings() {
        return settings;
    }

    public BenchmarkReport run() {
        final BenchmarkReportBuilder builder = new BenchmarkReportBuilder(settings);
        final HttpConnectionFactory connectionFactory = new DefaultConnectionFactory(settings.getTimeout());
        final HttpRequestRunner runner = new HttpRequestRunner(connectionFactory, builder, settings.getConcurrencyLevel());

        runner.requestUrlMultipleTimes(settings.getTargetUrl(), settings.getRequestCount());

        return builder.getReport();
    }
}
