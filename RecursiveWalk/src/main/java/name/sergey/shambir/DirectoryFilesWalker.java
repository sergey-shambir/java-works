package name.sergey.shambir;

import java.nio.file.Path;
import java.util.ArrayList;

interface DirectoryFilesWalker {
    void addDirectoryFiles(Path directoryPath);
    ArrayList<Path> getFilePaths();
}
