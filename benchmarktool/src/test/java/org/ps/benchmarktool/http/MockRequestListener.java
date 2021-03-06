package org.ps.benchmarktool.http;

import java.net.HttpURLConnection;
import java.time.Duration;
import java.util.ArrayList;

public class MockRequestListener implements RequestListener {
    private RuntimeException exception;
    private final ArrayList<Duration> timeSpentList = new ArrayList<>();
    private final ArrayList<Long> byteCounts = new ArrayList<>();
    private final ArrayList<Integer> statusCodes = new ArrayList<>();

    final int getSucceedRequestCount() {
        return (int)statusCodes.stream().filter((Integer code) -> code == HttpURLConnection.HTTP_OK).count();
    }

    final Duration getLastTimeSpent() {
        maybeRethrow();
        return this.timeSpentList.get(this.timeSpentList.size() - 1);
    }

    final long getLastByteCount() {
        maybeRethrow();
        return this.byteCounts.get(this.byteCounts.size() - 1);
    }

    final int getLastStatusCode() {
        maybeRethrow();
        return this.statusCodes.get(this.statusCodes.size() - 1);
    }

    @Override
    public void onRequestComplete(Duration timeSpent, long transmittedByteCount, int httpStatusCode) {
        synchronized (this) {
            this.timeSpentList.add(timeSpent);
            this.byteCounts.add(transmittedByteCount);
            this.statusCodes.add(httpStatusCode);
        }
    }

    @Override
    public void onRequestError(RuntimeException ex) {
        synchronized (this) {
            this.exception = ex;
        }
    }

    @Override
    public void onRequestTimeout() {
        // nothing
    }

    @Override
    public void setTotalDuration(Duration requestsTotalDuration) {
        // nothing
    }

    private void maybeRethrow() {
        if (this.exception != null) {
            throw this.exception;
        }
    }
}
