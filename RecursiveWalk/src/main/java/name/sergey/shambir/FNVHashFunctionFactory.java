package name.sergey.shambir;

public class FNVHashFunctionFactory implements HashFunctionFactory {
    @Override
    public HashFunction newHashFunction() {
        return new FNVHashFunction();
    }
}
