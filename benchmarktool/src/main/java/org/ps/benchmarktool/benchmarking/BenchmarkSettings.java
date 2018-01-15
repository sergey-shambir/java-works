package org.ps.benchmarktool.benchmarking;

import java.net.URL;
import java.time.Duration;

public interface BenchmarkSettings {
    int getRequestCount();
    URL getTargetUrl();
    int getConcurrencyLevel();
    Duration getTimeout();
}
