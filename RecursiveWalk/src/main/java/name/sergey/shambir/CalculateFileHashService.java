package name.sergey.shambir;

import java.nio.file.Path;

public interface CalculateFileHashService {
    void calculateHash(Path filepath);
    int getHashValue(Path filepath);
}
