package test.java.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CollectionsTest {

    @Test
    void sort() {
        List<Integer> integers = Arrays.asList(1, 3, 2);
        Collections.sort(integers);
        assertArrayEquals(Arrays.asList(1, 2, 3).toArray(), integers.toArray());
    }

    @Test
    void testSortWithComparator() {
        List<Integer> integers = Arrays.asList(1, 3, 2);
        Collections.sort(integers, Comparator.reverseOrder());
        assertArrayEquals(Arrays.asList(3, 2, 1).toArray(), integers.toArray());
    }

    @Test
    void binarySearch() {
        List<String> strings = Arrays.asList("a", "b", "d", "e");
        int dIndex = Collections.binarySearch(strings, "d");
        assertEquals(2, dIndex);
        int xIndex = Collections.binarySearch(strings, "x");
        assertEquals(-5, xIndex);

        int cIndex = Collections.binarySearch(strings, "c");
        assertEquals(-3, cIndex); // -2 - 1 = -3
    }

    @Test
    void binarySearchWithComparator() {

    }

    @Test
    void reverse() {
        List<Integer> integers = Arrays.asList(1, 2, 3);
        Collections.reverse(integers);
        assertArrayEquals(new Integer[]{3, 2, 1}, integers.toArray());
    }

    @Test
    void shuffle() {
        List<String> strings = Arrays.asList("a", "b", "c");
        // 对 list 进行随机排序
        Collections.shuffle(strings);
    }

    @Test
    void testShuffle() {
    }

    @Test
    void swap() {
    }

    @Test
    void fill() {
    }

    @Test
    void copy() {
    }

    @Test
    void min() {
    }

    @Test
    void testMin() {
    }

    @Test
    void max() {
    }

    @Test
    void testMax() {
    }

    @Test
    void rotate() {
    }

    @Test
    void replaceAll() {
    }

    @Test
    void indexOfSubList() {
    }

    @Test
    void lastIndexOfSubList() {
    }

    @Test
    void unmodifiableCollection() {
    }

    @Test
    void unmodifiableSet() {
    }

    @Test
    void unmodifiableSortedSet() {
    }

    @Test
    void unmodifiableNavigableSet() {
    }

    @Test
    void unmodifiableList() {
    }

    @Test
    void unmodifiableMap() {
    }

    @Test
    void unmodifiableSortedMap() {
    }

    @Test
    void unmodifiableNavigableMap() {
    }

    @Test
    void synchronizedCollection() {
    }

    @Test
    void testSynchronizedCollection() {
    }

    @Test
    void synchronizedSet() {
    }

    @Test
    void testSynchronizedSet() {
    }

    @Test
    void synchronizedSortedSet() {
    }

    @Test
    void synchronizedNavigableSet() {
    }

    @Test
    void synchronizedList() {
    }

    @Test
    void testSynchronizedList() {
    }

    @Test
    void synchronizedMap() {
    }

    @Test
    void synchronizedSortedMap() {
    }

    @Test
    void synchronizedNavigableMap() {
    }

    @Test
    void checkedCollection() {
    }

    @Test
    void zeroLengthArray() {
    }

    @Test
    void checkedQueue() {
    }

    @Test
    void checkedSet() {
    }

    @Test
    void checkedSortedSet() {
    }

    @Test
    void checkedNavigableSet() {
    }

    @Test
    void checkedList() {
    }

    @Test
    void checkedMap() {
    }

    @Test
    void checkedSortedMap() {
    }

    @Test
    void checkedNavigableMap() {
    }

    @Test
    void emptyIterator() {
    }

    @Test
    void emptyListIterator() {
    }

    @Test
    void emptyEnumeration() {
    }

    @Test
    void emptySet() {
    }

    @Test
    void emptySortedSet() {
    }

    @Test
    void emptyNavigableSet() {
    }

    @Test
    void emptyList() {
    }

    @Test
    void emptyMap() {
    }

    @Test
    void emptySortedMap() {
    }

    @Test
    void emptyNavigableMap() {
    }

    @Test
    void singleton() {
    }

    @Test
    void singletonIterator() {
    }

    @Test
    void singletonSpliterator() {
    }

    @Test
    void singletonList() {
    }

    @Test
    void singletonMap() {
    }

    @Test
    void nCopies() {
    }

    @Test
    void reverseOrder() {
    }

    @Test
    void testReverseOrder() {
    }

    @Test
    void enumeration() {
    }

    @Test
    void list() {
    }

    @Test
    void eq() {
    }

    @Test
    void frequency() {
        List<Integer> integers = Arrays.asList(1, 3, 4, 4, 2, 1);
        assertEquals(1, Collections.frequency(integers, 3));
        assertEquals(2, Collections.frequency(integers, 1));
        assertEquals(0, Collections.frequency(integers, 9));
    }

    @Test
    void disjoint() {
    }

    @Test
    void addAll() {
    }

    @Test
    void newSetFromMap() {
    }

    @Test
    void asLifoQueue() {
    }
}