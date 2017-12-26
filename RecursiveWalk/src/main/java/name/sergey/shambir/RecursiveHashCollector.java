package name.sergey.shambir;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

class RecursiveHashCollector {
    private DirectoryFilesWalker directoryFilesWalker;
    private CalculateFileHashService calculateFileHashService;
    private Path inputPath;
    private Path outputPath;

    void setDirectoryFilesWalker(DirectoryFilesWalker directoryFilesWalker) {
        this.directoryFilesWalker = directoryFilesWalker;
    }

    void setCalculateFileHashService(CalculateFileHashService calculateFileHashService) {
        this.calculateFileHashService = calculateFileHashService;
    }

    void setInputPath(Path inputPath) {
        this.inputPath = inputPath;
    }

    void setOutputPath(Path outputPath) {
        this.outputPath = outputPath;
    }

    void collectHashes() {
        ArrayList<Path> inputs = parseInputFilePaths();
        for (Path path : inputs) {
            calculateFileHashService.calculateHash(path);
        }
        writeOutputFile(inputs);
    }

    private ArrayList<Path> parseInputFilePaths() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.inputPath.toString()));
            String line;
            while ((line = reader.readLine()) != null) {
                // ignore empty lines
                if (line.length() > 0) {
                    this.directoryFilesWalker.addDirectoryFiles(Paths.get(line));
                }
            }
            reader.close();
        } catch (IOException ex) {
            throw new RuntimeException("fatal I/O error: " + ex.getMessage());
        }
        return this.directoryFilesWalker.getFilePaths();
    }

    private void writeOutputFile(ArrayList<Path> inputs) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(this.outputPath.toString()));
            for (Path path : inputs) {
                final int hashValue = calculateFileHashService.getHashValue(path);
                final String line = String.format("%08x %s\n", hashValue, path.toString());
                out.write(line);
            }
            out.write("\n");
            out.close();
        } catch (IOException ex) {
            throw new RuntimeException("fatal I/O error: " + ex.getMessage());
        }
    }
}
