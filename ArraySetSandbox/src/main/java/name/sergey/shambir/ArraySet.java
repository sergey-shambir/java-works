package name.sergey.shambir;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class ArraySet<E extends Comparable<E>> extends AbstractSet<E> implements NavigableSet<E> {
    private class ArraySetData {
        private ArrayList<E> items;
        private Comparator<? super E> comparator;
    }

    private final ArraySetData data;
    private int fromIndex = 0;
    private int toIndex = 0;

    private class SetIterator implements Iterator<E> {
        private final ArrayList<E> items;
        private int fromIndex;
        private final int endIndex;
        private final int step;

        private SetIterator(ArrayList<E> items, int from, int to) {
            this.items = items;
            this.fromIndex = from;
            this.step = (from > to && to != -1) ? -1 : 1;
            this.endIndex = to + this.step;
        }

        @Override
        public boolean hasNext() {
            return this.fromIndex != this.endIndex;
        }

        @Override
        public E next() {
            if (hasNext()) {
                E result = this.items.get(this.fromIndex);
                this.fromIndex += this.step;
                return result;
            }
            throw new NoSuchElementException();
        }
    }

    public ArraySet(ArrayList<E> items) {
        this.data = new ArraySetData();
        initData(items, null);
    }

    public ArraySet(ArrayList<E> items, Comparator<? super E> comparator) {
        this.data = new ArraySetData();
        initData(items, comparator);
    }

    private ArraySet(ArraySetData data, int fromIndex, int toIndex) {
        this.data = data;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    private void initData(ArrayList<E> items, Comparator<? super E> comparator) {
        if (comparator != null) {
            this.data.items =
                items.stream().sorted(comparator).distinct().collect(Collectors.toCollection(ArrayList::new));
        } else {
            this.data.items = items.stream().sorted().distinct().collect(Collectors.toCollection(ArrayList::new));
        }
        this.data.comparator = comparator;
        this.fromIndex = 0;
        this.toIndex = this.data.items.size() - 1;
    }

    @Override
    public E lower(E e) {
        final int index = lowerIndex(e);
        if (index == -1) {
            return null;
        }
        return this.data.items.get(index);
    }

    @Override
    public E floor(E e) {
        final int index = floorIndex(e);
        if (index == -1) {
            return null;
        }
        return this.data.items.get(index);
    }

    @Override
    public E ceiling(E e) {
        final int index = ceilingIndex(e);
        if (index == -1) {
            return null;
        }
        return this.data.items.get(index);
    }

    @Override
    public E higher(E e) {
        final int index = higherIndex(e);
        if (index == -1) {
            return null;
        }
        return this.data.items.get(index);
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
        return new SetIterator(this.data.items, this.fromIndex, this.toIndex);
    }

    @Override
    public NavigableSet<E> descendingSet() {
        return new ArraySet<>(this.data, this.toIndex, this.fromIndex);
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new SetIterator(this.data.items, this.toIndex, this.fromIndex);
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        final int toInclusiveIndex = floorIndex(toElement);
        final int toExclusiveIndex = lowerIndex(toElement);
        final int fromInclusiveIndex = ceilingIndex(fromElement);
        final int fromExclusiveIndex = higherIndex(fromElement);

        if (toInclusiveIndex == -1 || fromInclusiveIndex == -1) {
            throw new IllegalArgumentException("fromElement and toElement should be in set");
        }

        if (compareIndexes(fromInclusiveIndex, toInclusiveIndex) > 0) {
            throw new IllegalArgumentException("fromElement cannot be greater than toElement");
        }

        final int toIndex = toInclusive ? toInclusiveIndex : toExclusiveIndex;
        final int fromIndex = fromInclusive ? fromInclusiveIndex : fromExclusiveIndex;

        switch (compareIndexes(fromIndex, toIndex)) {
            case -1:
            case 0:
                return new ArraySet<>(this.data, fromIndex, toIndex);

            default:
                return new ArraySet<>(new ArrayList<E>());
        }
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        return subSet(first(), true, toElement, inclusive);
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        return subSet(fromElement, inclusive, last(), true);
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.data.comparator;
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        return headSet(toElement, false);
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return tailSet(fromElement, true);
    }

    @Override
    public E first() {
        return this.data.items.get(this.fromIndex);
    }

    @Override
    public E last() {
        return this.data.items.get(this.toIndex);
    }

    @Override
    public int size() {
        return Math.abs(this.fromIndex - this.toIndex) + 1;
    }

    private int compareIndexes(int from, int to) {
        final int sign = this.isReversed() ? -1 : 1;
        return sign * Integer.compare(from, to);
    }

    private int compareElements(E lhs, E rhs) {
        final int sign = this.isReversed() ? -1 : 1;
        if (this.data.comparator != null) {
            return sign * this.data.comparator.compare(lhs, rhs);
        }
        return sign * lhs.compareTo(rhs);
    }

    private int lowerIndex(E e) {
        final int nextIndex = findFirstUsingBinarySearch((E other) -> compareElements(other, e) >= 0);
        if (nextIndex == -1) {
            return isEmpty() ? -1 : this.toIndex;
        }
        if (nextIndex == this.fromIndex) {
            return -1;
        }
        final int step = isReversed() ? -1 : 1;
        return nextIndex - step;
    }

    private int floorIndex(E e) {
        final int nextIndex = findFirstUsingBinarySearch((E other) -> compareElements(other, e) > 0);
        if (nextIndex == -1) {
            return isEmpty() ? -1 : this.toIndex;
        }
        if (nextIndex == this.fromIndex) {
            return -1;
        }
        final int step = isReversed() ? -1 : 1;
        return nextIndex - step;
    }

    private int ceilingIndex(E e) {
        final int index = findFirstUsingBinarySearch((E other) -> compareElements(other, e) >= 0);
        if (index == -1) {
            return -1;
        }
        return index;
    }

    private int higherIndex(E e) {
        final int index = findFirstUsingBinarySearch((E other) -> compareElements(other, e) > 0);
        if (index == -1) {
            return -1;
        }
        return index;
    }

    /**
     * Finds index of first element (or last for reversed set) which satisfies given predicate
     * Assumes that all elements after first also satisfy predicate - otherwise can return wrong result.
     * Complexity: O(log2(N))
     * @param predicate - predicate to check
     * @return index of first element that satisfies or -1
     */
    private int findFirstUsingBinarySearch(Predicate<E> predicate) {
        if (this.data.items.isEmpty()) {
            return -1;
        }

        int low = fromIndex;
        int high = toIndex;
        int mediumAddendum = this.isReversed() ? 1 : 0;

        // No such element.
        if (!predicate.test(this.data.items.get(high))) {
            return -1;
        }

        while (low < high) {
            final int medium = ((low + high) >>> 1) + mediumAddendum;
            if (predicate.test(this.data.items.get(medium))) {
                high = medium;
            } else {
                low = medium + 1;
            }
        }
        return low;
    }

    private boolean isReversed() {
        return this.fromIndex > this.toIndex;
    }
}
