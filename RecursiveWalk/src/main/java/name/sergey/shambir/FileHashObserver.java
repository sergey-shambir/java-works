package name.sergey.shambir;

import java.nio.file.Path;

public interface FileHashObserver { void setFileHash(Path filepath, int hashValue); }
