package name.sergey.shambir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

class RecursiveFilesWalker implements DirectoryFilesWalker {
    private ArrayList<Path> filepaths;
    private HashSet<Path> usedPaths;

    RecursiveFilesWalker() {
        this.filepaths = new ArrayList<>();
        this.usedPaths = new HashSet<>();
    }

    public void addDirectoryFiles(Path dirpath) {
        try {
            Files.walk(dirpath).forEach(path -> {
                if (Files.isRegularFile(path) && !this.usedPaths.contains(path)) {
                    this.usedPaths.add(path);
                    this.filepaths.add(path);
                }
            });
        } catch (IOException exception) {
            throw new RuntimeException("cannot scan " + dirpath.toString() + ": " + exception.getMessage());
        }
    }

    public final ArrayList<Path> getFilePaths() {
        return this.filepaths;
    }
}
