package name.sergey.shambir;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

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
    public void testDescendingOrder() {
        String[] values = {"aa", "bb", "ab", "ba", "ab", "bb", "bb"};
        String[] expected = {"bb", "ba", "ab", "aa"};
        ArraySet<String> set = new ArraySet<>(new ArrayList<>(Arrays.asList(values)));

        assertArrayEquals(expected, toReversedArray(set));
    }

    @Test
    public void testDescendingSet() {
        String[] values = {"aa", "bb", "ab", "ba", "ab", "bb", "bb"};
        String[] expected = {"bb", "ba", "ab", "aa"};
        ArraySet<String> set = new ArraySet<>(new ArrayList<>(Arrays.asList(values)));

        assertArrayEquals(expected, toArray(set.descendingSet()));
    }

    @Test
    public void testSetWithComparator() {
        String[] values = {"aa", "bb", "ab", "ba", "ab", "bb", "bb"};
        String[] expected = {"bb", "ba", "ab", "aa"};
        Comparator reverseOrder = Collections.reverseOrder();
        ArraySet<String> set = new ArraySet<>(new ArrayList<>(Arrays.asList(values)), reverseOrder);

        assertFalse(set.isEmpty());
        assertEquals(expected.length, set.size());
        assertEquals(set.first(), "bb");
        assertEquals(set.last(), "aa");
        assertArrayEquals(expected, toArray(set));
    }

    @Test
    public void testLower() {
        ArraySet<String> empty = new ArraySet<>(new ArrayList<String>());
        assertEquals(null, empty.lower("00"));
        assertEquals(null, empty.lower("zz"));

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

    @Test
    public void testLowerWithComparator() {
        ArraySet<String> empty = new ArraySet<>(new ArrayList<String>());
        assertEquals(null, empty.lower("00"));
        assertEquals(null, empty.lower("zz"));

        String[] values = {"aa", "bb", "ab", "ba", "ab", "bb", "bb"};
        Comparator reverseOrder = Collections.reverseOrder();
        ArraySet<String> set = new ArraySet<>(new ArrayList<>(Arrays.asList(values)), reverseOrder);

        assertEquals("aa", set.lower("00"));
        assertEquals("ab", set.lower("aa"));
        assertEquals("ab", set.lower("aaa"));
        assertEquals("ba", set.lower("ab"));
        assertEquals("ba", set.lower("ac"));
        assertEquals("bb", set.lower("ba"));
        assertEquals(null, set.lower("bb"));
        assertEquals(null, set.lower("bbb"));
        assertEquals(null, set.lower("cc"));
    }

    @Test
    public void testFloor() {
        ArraySet<String> empty = new ArraySet<>(new ArrayList<String>());
        assertEquals(null, empty.floor("00"));
        assertEquals(null, empty.floor("zz"));

        String[] values = {"aa", "bb", "ab", "ba", "ab", "bb", "bb"};
        ArraySet<String> set = new ArraySet<>(new ArrayList<>(Arrays.asList(values)));

        assertEquals(null, set.floor("00"));
        assertEquals("aa", set.floor("aa"));
        assertEquals("aa", set.floor("aaa"));
        assertEquals("ab", set.floor("ab"));
        assertEquals("ab", set.floor("ac"));
        assertEquals("ba", set.floor("ba"));
        assertEquals("bb", set.floor("bb"));
        assertEquals("bb", set.floor("bbb"));
        assertEquals("bb", set.floor("cc"));
    }

    @Test
    public void testCeiling() {
        ArraySet<String> empty = new ArraySet<>(new ArrayList<String>());
        assertEquals(null, empty.ceiling("00"));
        assertEquals(null, empty.ceiling("zz"));

        String[] values = {"aa", "bb", "ab", "ba", "ab", "bb", "bb"};
        ArraySet<String> set = new ArraySet<>(new ArrayList<>(Arrays.asList(values)));

        assertEquals("aa", set.ceiling("00"));
        assertEquals("aa", set.ceiling("aa"));
        assertEquals("ab", set.ceiling("aaa"));
        assertEquals("ab", set.ceiling("ab"));
        assertEquals("ba", set.ceiling("ac"));
        assertEquals("ba", set.ceiling("ba"));
        assertEquals("bb", set.ceiling("bb"));
        assertEquals(null, set.ceiling("bbb"));
        assertEquals(null, set.ceiling("cc"));
    }

    @Test
    public void testHigher() {
        ArraySet<String> empty = new ArraySet<>(new ArrayList<String>());
        assertEquals(null, empty.higher("00"));
        assertEquals(null, empty.higher("zz"));

        String[] values = {"aa", "bb", "ab", "ba", "ab", "bb", "bb"};
        ArraySet<String> set = new ArraySet<>(new ArrayList<>(Arrays.asList(values)));

        assertEquals("aa", set.higher("00"));
        assertEquals("ab", set.higher("aa"));
        assertEquals("ab", set.higher("aaa"));
        assertEquals("ba", set.higher("ab"));
        assertEquals("ba", set.higher("ac"));
        assertEquals("bb", set.higher("ba"));
        assertEquals(null, set.higher("bb"));
        assertEquals(null, set.higher("bbb"));
        assertEquals(null, set.higher("cc"));
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

    @Test(expected = NoSuchElementException.class)
    public void testNextOnEmpty() {
        ArraySet<String> empty = new ArraySet<>(new ArrayList<String>());
        Iterator<String> iterator = empty.iterator();
        assertFalse(iterator.hasNext());
        iterator.next();
    }

    @Test
    public void testSubSetInclusiveExclusive() {
        String[] values = {"aa", "bb", "ab", "ba", "ab", "bb", "bb"};
        String[] expected = {"ba"};
        ArraySet<String> set = new ArraySet<>(new ArrayList<>(Arrays.asList(values)));
        SortedSet<String> subset = set.subSet("ba", "bb");

        assertEquals(1, subset.size());
        assertArrayEquals(expected, toArray(subset));
    }

    @Test
    public void testSubSetInclusiveInclusive() {
        String[] values = {"aa", "bb", "ab", "ba", "ab", "bb", "bb"};
        String[] expected = {"ba", "bb"};
        ArraySet<String> set = new ArraySet<>(new ArrayList<>(Arrays.asList(values)));
        SortedSet<String> subset = set.subSet("ba", true, "bb", true);

        assertEquals(2, subset.size());
        assertArrayEquals(expected, toArray(subset));
    }

    @Test
    public void testSubSetOfSubSet() {
        String[] values = {"aa", "bb", "ab", "ba", "ab", "bb", "bb"};
        String[] expected = {"ab"};
        ArraySet<String> set = new ArraySet<>(new ArrayList<>(Arrays.asList(values)));
        SortedSet<String> subset = set.subSet("ab", "bb");
        SortedSet<String> subsetOfSubset = subset.subSet("ab", "ba");

        assertEquals(1, subsetOfSubset.size());
        assertArrayEquals(expected, toArray(subsetOfSubset));
    }

    private String[] toReversedArray(NavigableSet<String> set) {
        return toArray(set.descendingIterator(), set.size());
    }

    private String[] toArray(SortedSet<String> set) {
        return toArray(set.iterator(), set.size());
    }

    private String[] toArray(Iterator<String> iterator, int size) {
        String[] result = new String[size];
        for (int i = 0; i < result.length; ++i) {
            assertTrue(iterator.hasNext());
            result[i] = iterator.next();
            assertTrue(result[i] != null);
        }
        assertFalse(iterator.hasNext());
        return result;
    }
}
