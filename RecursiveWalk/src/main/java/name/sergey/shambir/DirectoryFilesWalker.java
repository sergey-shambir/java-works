package name.sergey.shambir;

import java.nio.file.Path;
import java.util.ArrayList;

public interface DirectoryFilesWalker {
    void addDirectoryFiles(Path dirpath);
    ArrayList<Path> getFilePaths();
}
