package org.ps.benchmarktool.benchmarking;

import java.time.Duration;
import java.util.ArrayList;

public class BenchmarkReport {
    private ArrayList<Duration> timeSpentList;
    private boolean isDirty;
    private long transmittedByteCount;
    private int succeedCount;
    private int failedCount;
    private int requestCount;
    private int concurrencyLevel;
    private Duration requestsTotalDuration;

    public BenchmarkReport() {
        this.timeSpentList = new ArrayList<>();
        this.requestsTotalDuration = Duration.ZERO;
    }

    /**
     * Returns percentile of requests time spent corresponding to given ratio.
     * @param ratio - ratio in range [0..1]
     * @return
     */
    public final Duration getPercentileBoundValue(int percentile) {
        normalize();
        percentile = Math.max(0, Math.min(100, percentile));

        final int index = this.timeSpentList.size() * percentile / 100 - 1;
        if (index >= 0) {
            return this.timeSpentList.get(Math.max(0, index));
        }
        return Duration.ZERO;
    }

    public final Duration getAverageDuration() {
        normalize();

        if (this.timeSpentList.isEmpty()) {
            return Duration.ZERO;
        }

        final Duration total =
            this.timeSpentList.stream().reduce(Duration.ZERO, (duration, duration2) -> duration.plus(duration2));
        return total.dividedBy((long)this.timeSpentList.size());
    }

    public final int getConcurrencyLevel() {
        return this.concurrencyLevel;
    }

    public final Duration getRequestsTotalDuration() {
        return this.requestsTotalDuration;
    }

    public final float getRequestsPerSecond() {
        if (this.requestCount == 0) {
            return 0;
        }
        return 1000.f * (float)this.requestCount / (float)this.requestsTotalDuration.toMillis();
    }

    public final long getTransmittedByteCount() {
        return this.transmittedByteCount;
    }

    public final int getTotalCount() {
        return this.succeedCount + this.failedCount;
    }

    public final int getFailedCount() {
        return this.failedCount;
    }

    public void setRequestsStats(int concurrencyLevel, int requestCount, Duration requestsTotalDuration) {
        this.concurrencyLevel = concurrencyLevel;
        this.requestCount = requestCount;
        this.requestsTotalDuration = requestsTotalDuration;
    }

    public void addRequest(boolean succeed, long byteCount, Duration duration) {
        this.isDirty = true;
        if (succeed) {
            this.succeedCount += 1;
        } else {
            this.failedCount += 1;
        }
        this.transmittedByteCount += byteCount;
        this.timeSpentList.add(duration);
    }

    private final void normalize() {
        if (this.isDirty) {
            this.timeSpentList.sort(null);
            this.isDirty = false;
        }
    }
}
