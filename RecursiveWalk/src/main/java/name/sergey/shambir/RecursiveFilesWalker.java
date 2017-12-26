package name.sergey.shambir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

class RecursiveFilesWalker implements DirectoryFilesWalker {
    private final ArrayList<Path> filePaths;
    private final HashSet<Path> usedPaths;

    RecursiveFilesWalker() {
        this.filePaths = new ArrayList<>();
        this.usedPaths = new HashSet<>();
    }

    public void addDirectoryFiles(Path directoryPath) {
        try {
            Files.walk(directoryPath).forEach(path -> {
                if (Files.isRegularFile(path) && !this.usedPaths.contains(path)) {
                    this.usedPaths.add(path);
                    this.filePaths.add(path);
                }
            });
        } catch (IOException exception) {
            throw new RuntimeException("cannot scan " + directoryPath.toString() + ": " + exception.getMessage());
        }
    }

    public final ArrayList<Path> getFilePaths() {
        return this.filePaths;
    }
}
