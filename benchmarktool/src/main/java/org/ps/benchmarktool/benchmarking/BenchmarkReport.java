package org.ps.benchmarktool.benchmarking;

import java.time.Duration;

public interface BenchmarkReport {
    Duration getPercentileBoundValue(int percentile);
    Duration getAverageDuration();
    int getConcurrencyLevel();
    Duration getRequestsTotalDuration();
    float getRequestsPerSecond();
    long getTransmittedByteCount();
    int getTotalCount();
    int getFailedCount();
    int getRequestsKilledByTimeoutCount();
    BenchmarkSettings getSettings();
}
