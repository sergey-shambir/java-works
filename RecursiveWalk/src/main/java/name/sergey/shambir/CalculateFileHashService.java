package name.sergey.shambir;

import java.nio.file.Path;

interface CalculateFileHashService {
    void calculateHash(Path filepath);
    int getHashValue(Path filepath);
}
