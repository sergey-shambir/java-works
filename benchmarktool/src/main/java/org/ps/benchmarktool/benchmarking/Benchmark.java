package org.ps.benchmarktool.benchmarking;

public interface Benchmark {
    BenchmarkSettings getSettings();
    BenchmarkReport run();
}
