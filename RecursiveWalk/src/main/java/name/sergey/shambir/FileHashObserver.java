package name.sergey.shambir;

import java.nio.file.Path;

interface FileHashObserver {
    void setFileHash(Path filepath, int hashValue);
}
