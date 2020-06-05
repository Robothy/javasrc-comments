package test.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ListTest {

    @Test
    void listIterator(){
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);
        integerList.add(5);

        ListIterator<Integer> integerListIterator = integerList.listIterator();

        // ListIterator.remove 方法依赖于 next 或 previous
        Assertions.assertThrows(IllegalStateException.class, integerListIterator::remove);

        integerListIterator.next();   // 迭代器从第 1 个元素左侧移动到了第 1 个元素右侧
        integerListIterator.remove(); // 移除了第一个元素
        Assertions.assertArrayEquals(new Integer[]{2, 3,4, 5}, integerList.toArray(new Integer[0]));

        // 此时迭代器的左侧已经没有元素了，调用 previous 抛出 NoSuchElementException
        Assertions.assertThrows(NoSuchElementException.class, integerListIterator::previous);

        integerListIterator.next();     // 2|3 4 5
        integerListIterator.next();     // 2 3|4 5
        integerListIterator.previous(); // 2|3 4 5
        integerListIterator.remove();   // 将删除元素 3 ，因为迭代器刚刚越过了元素 3
        // 迭代器的 remove 方法总是删除迭代器刚刚越过的元素
        Assertions.assertArrayEquals(new Integer[]{2, 4, 5}, integerList.toArray(new Integer[0])); // 2|4 5

        // 注意是刚刚越过的元素，不是左侧，也不是右侧的元素。
        // 例如，两次调用 remove 中间没有调用 next 或者 previous 将抛出异常
        Assertions.assertThrows(IllegalStateException.class, integerListIterator::remove);

        // add 方法总是在迭代器的左侧添加元素
        integerListIterator.add(6); // 2|4 5 -> 2 6|4 5
        Assertions.assertArrayEquals(new Integer[]{2, 6, 4, 5}, integerList.toArray(new Integer[0]));

        // 并且 add 方法可以连续多次添加
        integerListIterator.add(7); // 2 6 7|4 5
        Assertions.assertArrayEquals(new Integer[]{2, 6, 7, 4, 5}, integerList.toArray(new Integer[0]));

        integerListIterator.next(); // 2 6 7 4|5
        integerListIterator.add(8); // 2 6 7 4 8|5
        Assertions.assertArrayEquals(new Integer[]{2, 6, 7, 4, 8, 5}, integerList.toArray(new Integer[0]));

        integerListIterator.previous(); // 2 6 7 4|8 5
        integerListIterator.add(9); // 2 6 7 4 9|8 5
        Assertions.assertArrayEquals(new Integer[]{2, 6, 7, 4, 9, 8, 5}, integerList.toArray(new Integer[0]));
    }

    @Test
    void modifyListWhenIterate(){
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);

        ListIterator<Integer> integerListIterator = integerList.listIterator();
        integerListIterator.next();
        integerList.add(5);
        //若被另一迭代器或自身某个方法修改，抛出 ConcurrentModificationException
        Assertions.assertThrows(ConcurrentModificationException.class, integerListIterator::next);


    }


}
