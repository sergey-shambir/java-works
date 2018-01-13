package org.ps.benchmarktool.http;

import java.net.URL;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HttpRequestRunner {
    private final RequestListener requestListener;
    private final HttpConnectionFactory httpConnectionFactory;
    private final int concurrencyLevel;
    private final ExecutorService executorService;

    public HttpRequestRunner(HttpConnectionFactory httpConnectionFactory, RequestListener requestListener,
                             int concurrencyLevel) {
        assert concurrencyLevel > 0;
        this.httpConnectionFactory = httpConnectionFactory;
        this.requestListener = requestListener;
        this.concurrencyLevel = concurrencyLevel;
        this.executorService = Executors.newFixedThreadPool(concurrencyLevel);
    }

    public void requestUrlMultipleTimes(URL url, int requestCount) {
        final long startTimeNano = System.nanoTime();
        for (int i = 0; i < requestCount; ++i) {
            RequestTask task = new RequestTask(this.httpConnectionFactory, this.requestListener, url);
            this.executorService.execute(task);
        }
        await();

        final Duration totalDuration = Duration.ofNanos(System.nanoTime() - startTimeNano);
        this.requestListener.setRequestsStats(concurrencyLevel, requestCount, totalDuration);
    }

    private void await() {
        this.executorService.shutdown();
        try {
            this.executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            this.executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
