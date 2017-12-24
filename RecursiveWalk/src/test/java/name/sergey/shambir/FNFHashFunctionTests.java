package name.sergey.shambir;

import org.junit.Assert;
import org.junit.Test;

public class FNFHashFunctionTests extends Assert {
    @Test
    public void testEmptyArray() {
        final byte data[] = {};
        HashFunction hash = new FNVHashFunction();
        hash.feed(data, data.length);
        assertEquals(0x811c9dc5, hash.getHashValue());
    }

    @Test
    public void testZeroByteArray() {
        final byte data[] = {0};
        HashFunction hash = new FNVHashFunction();
        hash.feed(data, data.length);
        assertEquals(0x50c5d1f, hash.getHashValue());
    }
}
