package org.ps.benchmarktool.benchmarking;

import org.ps.benchmarktool.http.RequestListener;

import java.net.HttpURLConnection;
import java.time.Duration;

public class BenchmarkReportBuilder implements RequestListener {
    private RuntimeException lastException;
    private BenchmarkReport report;

    public BenchmarkReportBuilder() {
        this.report = new BenchmarkReport();
    }

    public BenchmarkReport getReport() throws RuntimeException {
        if (this.lastException != null) {
            throw this.lastException;
        }
        return this.report;
    }

    @Override
    public void onRequestComplete(Duration timeSpent, long transmittedByteCount, int httpStatusCode) {
        final boolean succeed =
            (httpStatusCode >= HttpURLConnection.HTTP_OK && httpStatusCode < HttpURLConnection.HTTP_BAD_REQUEST);
        synchronized (this) {
            this.report.addRequest(succeed, transmittedByteCount, timeSpent);
        }
    }

    @Override
    public void onRequestError(RuntimeException ex) {
        synchronized (this) {
            this.lastException = ex;
        }
    }

    @Override
    public void setRequestsStats(int concurrencyLevel, int requestCount, Duration requestsTotalDuration) {
        this.report.setRequestsStats(concurrencyLevel, requestCount, requestsTotalDuration);
    }
}