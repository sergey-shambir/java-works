package name.sergey.shambir;

import java.io.FileInputStream;
import java.nio.file.Path;

class CalculateFileHashTask implements Runnable {
    private static final int OPTIMAL_IO_BUFFER_SIZE = 63 * 1024;

    private final Path filepath;
    private FileHashObserver observer;
    private int hashValue;
    private name.sergey.shambir.HashFunction hashFunction;

    public CalculateFileHashTask(Path filepath) {
        this.filepath = filepath;
    }

    public void setHashObserver(FileHashObserver observer) {
        this.observer = observer;
    }

    public void setHashFunction(name.sergey.shambir.HashFunction hashFunction) {
        this.hashFunction = hashFunction;
    }

    public void run() {
        try {
            final FileInputStream stream = new FileInputStream(filepath.toString());
            final byte[] buffer = new byte[OPTIMAL_IO_BUFFER_SIZE];
            int readCount;

            this.hashFunction.reset();
            while ((readCount = stream.read(buffer)) != -1) {
                this.hashFunction.feed(buffer, readCount);
            }
            stream.close();
            this.hashValue = this.hashFunction.getHashValue();
        } catch (Exception ex) {
            hashValue = 0;
        }
        if (this.observer != null) {
            observer.setFileHash(this.filepath, this.hashValue);
        }
    }

    int getHashValue() {
        return this.hashValue;
    }
}
