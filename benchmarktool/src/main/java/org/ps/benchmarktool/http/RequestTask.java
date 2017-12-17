package org.ps.benchmarktool.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

public class RequestTask implements Runnable {
    private final HttpConnectionFactory factory;
    private final RequestListener listener;
    private final URL url;
    private long readByteCount;
    private int httpStatusCode;

    /**
     * @param factory - object which can create connection for given URL
     * @param listener - object which listens complete/exception events, will be called concurrently
     * @param url - url which should be requested
     */
    public RequestTask(HttpConnectionFactory factory, RequestListener listener, URL url) {
        this.factory = factory;
        this.listener = listener;
        this.url = url;
    }

    public void run() {
        HttpURLConnection conn = null;
        try {
            final long startTimeNano = System.nanoTime();
            conn = factory.openConnection(url);
            readResponse(conn);
            final long durationNano = System.nanoTime() - startTimeNano;
            listener.onRequestComplete(Duration.ofNanos(durationNano), this.readByteCount, this.httpStatusCode);
        } catch (Exception ex) {
            listener.onRequestError(ex);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private void readResponse(HttpURLConnection conn) throws IOException {
        InputStream in = conn.getInputStream();
        long maxSkipSize = Long.MAX_VALUE;
        this.readByteCount = in.skip(maxSkipSize);
        this.httpStatusCode = conn.getResponseCode();
    }
}
