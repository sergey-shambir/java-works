package org.ps.benchmarktool.http;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkerService implements Executor {
    private ExecutorService executor;

    public WorkerService(int concurrencyLevel) {
        assert concurrencyLevel > 0;
        executor = Executors.newFixedThreadPool(concurrencyLevel);
    }

    public void execute(Runnable runnable) {
    }
}
