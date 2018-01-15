package org.ps.benchmarktool.benchmarking;

import java.net.URL;
import java.time.Duration;

class BenchmarkSettingsImpl implements BenchmarkSettings {
    private URL targetUrl;
    private int requestCount = 100;
    private int concurrencyLevel = Runtime.getRuntime().availableProcessors();
    private Duration timeout = Duration.ofMillis(100);

    @Override
    public int getRequestCount() {
        return requestCount;
    }

    @Override
    public URL getTargetUrl() {
        return targetUrl;
    }

    @Override
    public int getConcurrencyLevel() {
        return concurrencyLevel;
    }

    @Override
    public Duration getTimeout() {
        return timeout;
    }

    void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    void setTargetUrl(URL url) {
        this.targetUrl = url;
    }

    void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    void setConcurrencyLevel(int concurrencyLevel) {
        this.concurrencyLevel = concurrencyLevel;
    }
}
