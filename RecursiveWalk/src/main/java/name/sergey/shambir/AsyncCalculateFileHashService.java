package name.sergey.shambir;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncCalculateFileHashService implements CalculateFileHashService, FileHashObserver {
    ExecutorService executorService;
    HashFunctionFactory hashFunctionFactory;
    ConcurrentHashMap<Path, Integer> hashes;
    boolean running;

    AsyncCalculateFileHashService(HashFunctionFactory hashFunctionFactory) {
        int processorsNum = Runtime.getRuntime().availableProcessors();
        this.executorService = Executors.newFixedThreadPool(2 * processorsNum);
        this.hashFunctionFactory = hashFunctionFactory;
        this.hashes = new ConcurrentHashMap<>();
    }

    @Override
    public void calculateHash(Path filepath) {
        this.running = true;
        CalculateFileHashTask task = new CalculateFileHashTask(filepath);
        task.setHashObserver(this);
        task.setHashFunction(hashFunctionFactory.newHashFunction());
        this.executorService.execute(task);
    }

    @Override
    public int getHashValue(Path filepath) {
        if (running) {
            await();
        }
        return this.hashes.get(filepath);
    }

    @Override
    public void setFileHash(Path filepath, int hashValue) {
        hashes.put(filepath, hashValue);
    }

    private void await() {
        this.executorService.shutdown();
        try {
            this.executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            this.executorService.shutdownNow();
            Thread.currentThread().interrupt();
        } finally {
            this.running = false;
        }
    }
}
