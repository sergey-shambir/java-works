package name.sergey.shambir;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CalculateFileHashTaskTests extends Assert {
    @Test
    public void testInvalidFileHash() {
        try {
            File file = File.createTempFile("test_file", ".tmp");
            Path filepath = Paths.get(file.getAbsolutePath());
            CalculateFileHashTask task = new CalculateFileHashTask(filepath);
            task.run();
            assertEquals(0, task.getHashValue());
        } catch (Exception ex) {
            fail("tests internal error: " + ex.getMessage());
        }
    }

    @Test
    public void testExistingFileHash() {
        String content = "The quick brown fox jumps over the lazy dog";
        byte[] contentBytes = content.getBytes();
        try {
            File file = File.createTempFile("test_file", ".tmp");

            final FileOutputStream stream = new FileOutputStream(file.getAbsoluteFile());
            stream.write(contentBytes);
            stream.close();

            FNVHashFunction hash = new FNVHashFunction();
            hash.reset();
            hash.feed(contentBytes, contentBytes.length);

            Path filepath = Paths.get(file.getAbsolutePath());
            CalculateFileHashTask task = new CalculateFileHashTask(filepath);
            task.setHashFunction(new FNVHashFunction());
            task.run();
            assertEquals(hash.getHashValue(), task.getHashValue());
        } catch (Exception ex) {
            fail("tests internal error: " + ex.getMessage());
        }
    }
}
