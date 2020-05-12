package test.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.INTERNAL;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

class HashMapTest {

    // HashMap 通过 tableSizeFor 方法来保证底层数组的长度总是 2 的 n 次方
    @Test
    void tableSizeFor() {
        int MAXIMUM_CAPACITY = 1 << 30; // 2的30次方

        // 因为 HashMap 中 tableSizeFor(int cap) 函数的访问修饰符是 default，所以这里直接把源码复制出来测试
        // tableSizeFor 函数的作用是根据 capacity 计算出 HashMap 底层实现数组的长度，这个长度大于等于 cap 且
        // 是 2 的 n 次方。
        Function<Integer, Integer> tableSizeFor = (Integer cap) -> {
            int n = cap - 1;
            n |= n >>> 1;
            n |= n >>> 2;
            n |= n >>> 4;
            n |= n >>> 8;
            n |= n >>> 16;
            return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
        };

        List<Integer> capacities = Arrays.asList(0, 1, 2, 3, 5, 6, 100, 0x7fff_ffff);
        List<Integer> expect = Arrays.asList(1, 1, 2, 4, 8, 8, 128, 0x4000_0000);
        Assertions.assertArrayEquals(expect.toArray(), capacities.stream().map(tableSizeFor).toArray());
    }

    // 之所以将底层数组的长度设置为 2 的 n 次方，是为了提高将元素 key 的散列值映射到到数组上（table）的效率。
    // 元素 put 到 HashMap 中的时候，先对 key 求 hash，然后根据 hash 的值来确定存放到数组中的位置。
    // 通常可以采用 hash % table.length 的值来确定数组的下标。这个计算更高效的一种算法是 hash&(table.length - 1)，
    // 而要保证这种算法可行的前提是 (table.length - 1) 的二进制表示的低位部分全是 1，高位部分全是 0。
    // 例如: 0x0000_1111, 0x0011_1111, 0x0000_0111
    // 这样的值恰好为 2 的 n 次方减 1.
    // 所以，为了使 hash&(table.length - 1) 可用，table.length 就得是 2 的 n 次方。
    // 将底层数组长度设置为 2 的 n 次方的目的是高效地为新增的元素选择 table 中的位置。
    @Test
    void capacity(){

        // 假设一组元素的哈希值，在 HashMap 中由 hash(Object) 得到
        List<Integer> hashCodes = Arrays.asList(1, 2, 3, 4, 5, 6, 8, 9, 10);

        // 假设 HashMap 底层 table 的大小为 4，非 2 的 2 次幂 0x0000_0100
        int tableSize = 4;

        BiFunction<Integer, Integer, Integer> mapHashCodeToTableIndex =
                (hashCode, tabSize)-> hashCode % tableSize;
        BiFunction<Integer, Integer, Integer> mapHashCodeToTableIndex2 =
                (hashCode, tabSize)-> hashCode & (tabSize - 1);

        for (Integer hashCode : hashCodes) { // 计算出来的结果一致
            Assertions.assertEquals(mapHashCodeToTableIndex.apply(hashCode, tableSize),
                    mapHashCodeToTableIndex2.apply(hashCode, tableSize));
        }
    }

}