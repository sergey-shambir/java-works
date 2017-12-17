package org.ps.benchmarktool.http;

import java.time.Duration;

public interface RequestListener {
    void onRequestComplete(Duration timeSpent, long transmittedByteCount, int httpStatusCode);
    void onRequestError(Exception ex);
}
