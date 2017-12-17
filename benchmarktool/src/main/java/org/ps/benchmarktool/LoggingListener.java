package org.ps.benchmarktool;

import org.ps.benchmarktool.http.RequestListener;

import java.time.Duration;

public class LoggingListener implements RequestListener {

    @Override
    public void onRequestComplete(Duration timeSpent, long transmittedByteCount, int httpStatusCode) {
        System.out.println("Request completed."
                           + " Duration: " + Long.toString(timeSpent.toMillis()) + " ms"
                           + ", byte count: " + Long.toString(transmittedByteCount) +
                           ", status code: " + Integer.toString(httpStatusCode));
    }

    @Override
    public void onRequestError(Exception ex) {
        ex.printStackTrace();
    }
}
