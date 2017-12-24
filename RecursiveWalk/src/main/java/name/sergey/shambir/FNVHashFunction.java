package name.sergey.shambir;

import java.security.InvalidParameterException;

public class FNVHashFunction implements name.sergey.shambir.HashFunction {
    private static final int FNV_32_PRIME = 0x01000193;
    private static final int FNV_32_START_VALUE = 0x811c9dc5;
    private int hashValue;

    public FNVHashFunction() {
        reset();
    }

    public void reset() {
        hashValue = FNV_32_START_VALUE;
    }

    public void feed(byte[] buffer, int count) {
        if (count > buffer.length) {
            throw new InvalidParameterException("count is greater than buffer size");
        }

        for (int i = 0; i < count; ++i) {
            hashValue *= FNV_32_PRIME;
            hashValue ^= buffer[i];
        }
    }

    public int getHashValue() {
        return hashValue;
    }
}
