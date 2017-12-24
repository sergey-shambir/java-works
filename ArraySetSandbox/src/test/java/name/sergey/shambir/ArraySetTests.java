package name.sergey.shambir;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ArraySetTests extends Assert {
    @Test
    public void testValuesOrder() {
        String[] values = {"aa", "bb", "ab", "ba", "ab", "bb", "bb"};
        String[] expected = {"aa", "ab", "ba", "bb"};
        ArraySet<String> set = new ArraySet<>(new ArrayList<>(Arrays.asList(values)));

        assertFalse(set.isEmpty());
        assertEquals(expected.length, set.size());
        assertEquals(set.first(), "aa");
        assertEquals(set.last(), "bb");
        assertArrayEquals(expected, toArray(set));
    }

    @Test
    public void testLower() {
        String[] values = {"aa", "bb", "ab", "ba", "ab", "bb", "bb"};
        ArraySet<String> set = new ArraySet<>(new ArrayList<>(Arrays.asList(values)));

        assertEquals(null, set.lower("00"));
        assertEquals(null, set.lower("aa"));
        assertEquals("aa", set.lower("aaa"));
        assertEquals("aa", set.lower("ab"));
        assertEquals("ab", set.lower("ac"));
        assertEquals("ab", set.lower("ba"));
        assertEquals("ba", set.lower("bb"));
        assertEquals("bb", set.lower("bbb"));
        assertEquals("bb", set.lower("cc"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testPollFirst() {
        String[] values = {"aa", "bb", "ab", "ba", "ab", "bb", "bb"};
        ArraySet<String> set = new ArraySet<>(new ArrayList<>(Arrays.asList(values)));
        set.pollFirst();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testPollLast() {
        String[] values = {"aa", "bb", "ab", "ba", "ab", "bb", "bb"};
        ArraySet<String> set = new ArraySet<>(new ArrayList<>(Arrays.asList(values)));
        set.pollLast();
    }

    private String[] toArray(ArraySet<String> set) {
        String[] result = new String[set.size()];
        Iterator<String> iterator = set.iterator();
        for (int i = 0; i < result.length; ++i) {
            result[i] = iterator.next();
            assertTrue(result[i] != null);
        }
        return result;
    }
}
