package org.ps.benchmarktool.http;

import java.time.Duration;

public interface RequestListener {
    void onRequestComplete(Duration timeSpent, long transmittedByteCount, int httpStatusCode);
    void onRequestError(RuntimeException ex);
    void setRequestsStats(int concurrencyLevel, int requestCount, Duration requestsTotalDuration);
}
