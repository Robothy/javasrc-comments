package test.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

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

        // ListIterator.add 方法也依赖于 next 或者 previous
        integerListIterator.add();

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


        integerListIterator.add(6);
        integerListIterator.add(7);
        integerListIterator.add(8);
        integerListIterator.add(9);

        System.out.println(integerList);
    }

}
