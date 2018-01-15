package org.ps.benchmarktool.benchmarking;

import java.net.URL;
import java.time.Duration;

class TestUtils {
    static BenchmarkSettings createTestBenchMarkSettings(int concurrency, int requestsCount) {
        return new BenchmarkSettings() {
            @Override
            public int getRequestCount() {
                return requestsCount;
            }

            @Override
            public URL getTargetUrl() {
                try {
                    return new URL("http://ya.ru");
                } catch (Exception e) {
                    return null;
                }

            }

            @Override
            public int getConcurrencyLevel() {
                return concurrency;
            }

            @Override
            public Duration getTimeout() {
                return Duration.ZERO;
            }
        };
    }
}
