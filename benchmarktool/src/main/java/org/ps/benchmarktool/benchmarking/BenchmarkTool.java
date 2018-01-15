package org.ps.benchmarktool.benchmarking;

import org.ps.benchmarktool.http.DefaultConnectionFactory;
import org.ps.benchmarktool.http.HttpConnectionFactory;
import org.ps.benchmarktool.http.HttpRequestRunner;

import java.net.URL;
import java.time.Duration;

class BenchmarkTool implements Benchmark {
    private URL targetUrl;
    private int requestCount = 100;
    private int concurrencyLevel = Runtime.getRuntime().availableProcessors();
    private Duration timeout = Duration.ofMillis(100);

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

    public BenchmarkReport run() {
        final BenchmarkReportBuilder builder = new BenchmarkReportBuilder();
        final HttpConnectionFactory connectionFactory = new DefaultConnectionFactory(this.timeout);
        final HttpRequestRunner runner = new HttpRequestRunner(connectionFactory, builder, this.concurrencyLevel);

        runner.requestUrlMultipleTimes(this.targetUrl, this.requestCount);

        return builder.getReport();
    }
}
