package name.sergey.shambir;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArraySet<E extends Comparable<E>> extends AbstractSet<E> implements NavigableSet<E> {
    private final ArrayList<E> data;
    private final Comparator<? super E> comparator;

    public ArraySet(ArrayList<E> data) {
        this.data = getSortedUnique(data, null);
        this.comparator = null;
    }

    public ArraySet(ArrayList<E> data, Comparator<? super E> comparator) {
        this.data = getSortedUnique(data, comparator);
        this.comparator = comparator;
    }

    private ArrayList<E> getSortedUnique(ArrayList<E> data, Comparator<? super E> comparator) {
        return data.stream().sorted().distinct().collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public E lower(E e) {
        final int nextIndex = findFirstUsingBinarySearch((E other) -> compareElements(other, e) >= 0);
        switch (nextIndex) {
            case -1:
                return isEmpty() ? null : last();
            case 0:
                return null;
            default:
                return this.data.get(nextIndex - 1);
        }
    }

    @Override
    public E floor(E e) {
        final int nextIndex = findFirstUsingBinarySearch((E other) -> compareElements(other, e) > 0);
        switch (nextIndex) {
            case -1:
                return isEmpty() ? null : last();
            case 0:
                return null;
            default:
                return this.data.get(nextIndex - 1);
        }
    }

    @Override
    public E ceiling(E e) {
        final int index = findFirstUsingBinarySearch((E other) -> compareElements(other, e) >= 0);
        if (index == -1) {
            return null;
        }
        return this.data.get(index);
    }

    @Override
    public E higher(E e) {
        final int index = findFirstUsingBinarySearch((E other) -> compareElements(other, e) > 0);
        if (index == -1) {
            return null;
        }
        return this.data.get(index);
    }

    @Override
    public E pollFirst() {
        throw new UnsupportedOperationException("cannot poll element from immutable set");
    }

    @Override
    public E pollLast() {
        throw new UnsupportedOperationException("cannot poll element from immutable set");
    }

    @Override
    public Iterator<E> iterator() {
        return this.data.iterator();
    }

    @Override
    public NavigableSet<E> descendingSet() {
        return null; // TODO: implement
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            private int left = size();

            @Override
            public boolean hasNext() {
                return left > 0;
            }

            @Override
            public E next() {
                if (left > 0) {
                    --left;
                    return data.get(left - 1);
                }
                throw new NoSuchElementException();
            }
        };
    }

    @Override
    public NavigableSet<E> subSet(E e, boolean b, E e1, boolean b1) {
        return null; // TODO: implement
    }

    @Override
    public NavigableSet<E> headSet(E e, boolean b) {
        return null; // TODO: implement
    }

    @Override
    public NavigableSet<E> tailSet(E e, boolean b) {
        return null; // TODO: implement
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    @Override
    public SortedSet<E> subSet(E e, E e1) {
        return null; // TODO: implement
    }

    @Override
    public SortedSet<E> headSet(E e) {
        return null; // TODO: implement
    }

    @Override
    public SortedSet<E> tailSet(E e) {
        return null; // TODO: implement
    }

    @Override
    public E first() {
        return this.data.get(0);
    }

    @Override
    public E last() {
        return this.data.get(size() - 1);
    }

    @Override
    public int size() {
        return this.data.size();
    }

    private int compareElements(E lhs, E rhs) {
        if (comparator != null) {
            return comparator.compare(lhs, rhs);
        }
        return lhs.compareTo(rhs);
    }

    /**
     * Finds index of first element which satisfies given predicate
     * Assumes that all elements after first also satisfy predicate - otherwise can return wrong result.
     * Complexity: O(log2(N))
     * @param predicate - predicate to check
     * @return index of first element that satisfies or -1
     */
    private int findFirstUsingBinarySearch(Predicate<E> predicate) {
        if (this.data.isEmpty()) {
            return -1;
        }

        int low = 0;
        int high = this.data.size() - 1;

        // No such element.
        if (!predicate.test(this.data.get(high))) {
            return -1;
        }

        while (low < high) {
            int i = (low + high) >>> 1;
            if (predicate.test(this.data.get(i))) {
                high = i;
            } else {
                low = i + 1;
            }
        }
        return low;
    }
}
