package org.ps.benchmarktool.http;

import java.time.Duration;

public class MockRequestListener implements RequestListener {
    private Exception lastException;
    private Duration lastTimeSpent;
    private long lastByteCount;
    private int lastStatusCode;

    final boolean wasSuccessful() {
        return (this.lastException == null);
    }

    final Duration getLastTimeSpent() {
        return this.lastTimeSpent;
    }

    final long getLastByteCount() {
        return this.lastByteCount;
    }

    final int getLastStatusCode() {
        return this.lastStatusCode;
    }

    @Override
    public void onRequestComplete(Duration timeSpent, long transmittedByteCount, int httpStatusCode) {
        this.lastException = null;
        this.lastTimeSpent = timeSpent;
        this.lastByteCount = transmittedByteCount;
        this.lastStatusCode = httpStatusCode;
    }

    @Override
    public void onRequestError(Exception ex) {
        this.lastException = ex;
        this.lastTimeSpent = null;
        this.lastByteCount = 0;
        this.lastStatusCode = 0;
    }
}
