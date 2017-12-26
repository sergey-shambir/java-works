package name.sergey.shambir;

import java.nio.file.Paths;

class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.printf("Usage: RecursiveWalk <input file> <output file>\n");
            System.exit(1);
        } else {
            try {
                calculateHashes(args[0], args[1]);
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
    }

    private static void calculateHashes(String inputPath, String outputPath) {
        RecursiveHashCollector collector = new RecursiveHashCollector();
        collector.setDirectoryFilesWalker(new RecursiveFilesWalker());
        collector.setCalculateFileHashService(new AsyncCalculateFileHashService(new FNVHashFunctionFactory()));
        collector.setInputPath(Paths.get(inputPath));
        collector.setOutputPath(Paths.get(outputPath));
        collector.collectHashes();
    }
}
