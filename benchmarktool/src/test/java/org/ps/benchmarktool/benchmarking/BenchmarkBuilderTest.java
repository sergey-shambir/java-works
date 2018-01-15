package org.ps.benchmarktool.benchmarking;

import org.ps.benchmarktool.benchmarking.BenchmarkBuilder.InvalidCommandLineArgumentsException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;

public class BenchmarkBuilderTest extends Assert {
    @Test
    public void testBuildFromCliWithShortForm() {
        String[] args = {"--u=http://my-url.net", "--n=32", "--c=24", "--t=38"};
        BenchmarkBuilder bb = new BenchmarkBuilder();
        bb.acceptCommandLineArguments(args);
        BenchmarkSettings bs = bb.build().getSettings();

        assertEquals("http://my-url.net", bs.getTargetUrl().toString());
        assertEquals(32, bs.getRequestCount());
        assertEquals(24, bs.getConcurrencyLevel());
        assertEquals(38, bs.getTimeout().toMillis());
    }

    @Test
    public void testBuildFromCliWithLongForm() {
        String[] args = {"--url=http://my-url-2.net", "--num=2", "--concurrency=27", "--timeout=44"};
        BenchmarkBuilder bb = new BenchmarkBuilder();
        bb.acceptCommandLineArguments(args);
        BenchmarkSettings bs = bb.build().getSettings();

        assertEquals("http://my-url-2.net", bs.getTargetUrl().toString());
        assertEquals(2, bs.getRequestCount());
        assertEquals(27, bs.getConcurrencyLevel());
        assertEquals(44, bs.getTimeout().toMillis());
    }

    @Test
    public void testBuildFromYamlFile() {
        try {
            TemporaryFolder folder = new TemporaryFolder();
            folder.create();
            File file = folder.newFile("defaults.yml");
            PrintStream fw = new PrintStream(file);
            fw.println("url: http://test-1155.com");
            fw.println("num: 105");
            fw.println("concurrency: 33");
            fw.println("timeout: 4");

            BenchmarkBuilder bb = new BenchmarkBuilder();
            bb.acceptYamlFile(file.getPath());
            BenchmarkSettings bs = bb.build().getSettings();

            assertEquals("http://test-1155.com", bs.getTargetUrl().toString());
            assertEquals(105, bs.getRequestCount());
            assertEquals(33, bs.getConcurrencyLevel());
            assertEquals(4, bs.getTimeout().toMillis());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidUrlThrowsRuntimeException() {
        new BenchmarkBuilder().acceptCommandLineArguments(new String[]{"--u=ololo"});
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidRequestCountThrowsRuntimeException() {
        new BenchmarkBuilder().acceptCommandLineArguments(new String[]{"--n=ololo"});
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidConcurrencyLevelThrowsRuntimeException() {
        new BenchmarkBuilder().acceptCommandLineArguments(new String[]{"--c=ololo"});
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidTimeoutThrowsRuntimeException() {
        new BenchmarkBuilder().acceptCommandLineArguments(new String[]{"--t=ololo"});
    }

    @Test(expected = InvalidCommandLineArgumentsException.class)
    public void testInvalidArgumentThrowsException() {
        new BenchmarkBuilder().acceptCommandLineArguments(new String[]{"--urlecs"});
    }

    @Test(expected = InvalidCommandLineArgumentsException.class)
    public void testInvalidArgumentThrowsExceptionItHasNotNullOptions() {
        try {
            new BenchmarkBuilder().acceptCommandLineArguments(new String[]{"--urlecs"});
        } catch (InvalidCommandLineArgumentsException e) {
            assertNotNull(e.getOptions());
            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void testBuildWithoutTargetUrlThrowsException() {
        new BenchmarkBuilder().build();
    }

    @Test
    public void testAcceptNotExistentFileWorksSilently() {
        try {
            new BenchmarkBuilder().acceptYamlFile("some-not-existent-file");
            assertTrue(true);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}