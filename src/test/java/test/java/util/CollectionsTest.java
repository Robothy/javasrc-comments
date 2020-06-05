package test.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

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
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("12");
//        strings.add("34");
//        String[] is = (String[])(strings.toArray());
//        System.out.println(is);

        Integer[] ints = new Integer[]{1, 2, 3};
        Object[] objs = ints;
        ints = (Integer[]) objs;
        System.out.println(ints);
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

    // toArray(Object[] arrayToFill) 是浅复制元素返回，
    // toArray 可以认为是 List 结构和数组结构的桥梁。
    @Test
    void toArray() {
        List<Element> elementList = new ArrayList<>();
        elementList.add(new Element("Hello"));
        Element[] elementArray = elementList.toArray(new Element[0]);
        Assertions.assertEquals(elementList.get(0), elementArray[0]);
        Assertions.assertSame(elementList.get(0), elementArray[0]);

        elementArray[0].name = "World";
        Assertions.assertEquals(elementList.get(0), elementArray[0]);
    }

    // toArray() 不能进行强制转化，至于为什么，还没有搞清楚
    @Test
    void toArray2() {

        //List<Integer> integers = Arrays.asList(1000, 1002, 1003);
        List<Integer> integers = new ArrayList<>();
        integers.add(1000);
        integers.add(1002);
        integers.add(1003);
        Integer[] arr = (Integer[]) integers.toArray();

        Object[] newArray = new Object[]{"AAA","BBB"};
        String[]q=(String[])(newArray);


        List<Element> elementList = new ArrayList<>();
        elementList.add(new Element("hello"));

        Assertions.assertThrows(ClassCastException.class, ()->{
            Element[] elementArray = (Element[]) elementList.toArray(); // 不能直接强转数组
        });

        // 但是可以对单个元素进行强转
        Element element = (Element) elementList.toArray()[0];
        Assertions.assertEquals("hello", element.name);

        // 所以，要从 Collection 中获取集合的数组结构，可以有两种方式

        // 方式一，调用 toArray() 的变体 toArray(Object[] arrayToFill)
        Element[] elementArray = elementList.toArray(new Element[0]);
        Assertions.assertEquals("hello", elementArray[0].name);

        // 方式二，调用 toArray()，然后通过循环对返回的数组中的各个元素进行强转
        elementArray = new Element[elementList.size()];
        Object[] objectArray = elementList.toArray();
        for(int i=0; i<elementList.size(); i++){
            elementArray[i] = (Element)objectArray[i];
        }
        Assertions.assertEquals("hello", elementArray[0].name);

    }

    static class Element {
        String name;

        Element(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Element)) return false;
            if (this.name == null) return false;
            return this.name.equals(((Element) obj).name);
        }
    }

}