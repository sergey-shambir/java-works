package name.sergey.shambir;

interface HashFunction {
    void reset();
    void feed(byte[] buffer, int count);
    int getHashValue();
}
