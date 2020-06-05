package test.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.INTERNAL;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

class HashMapTest {

    @Test
    void test(){
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");

        // compute 将计算一个新值，替换旧值，返回 get(key)
        String compute = map.compute(1, (k, v) -> k + v); // (k, v) -> k + v 返回 "1One"，替换原来的 "One"
        Assertions.assertEquals("1One", compute);
        Assertions.assertEquals("1One", map.get(1));

        // 若传入的 key 不存在，传给函数的参数为 key 和 null。
        // 并将 key 和计算出来的结果组成 entry 插入到 map中。
        compute = map.compute(4, (k, v) -> k + v);
        Assertions.assertEquals("4null", compute);
        Assertions.assertEquals("4null", map.get(4));
        Assertions.assertEquals(4, map.size()); // k = 4, v = "4null"  被插入，此时 map 中有 4 个 entry

        // 若函数的计算结果为 null，则删除 key，返回 get(key)，也就是null
        compute = map.compute(1, (k, v)-> null); // key = 1 将被删除
        Assertions.assertNull(compute);
        Assertions.assertEquals(3, map.size()); // 此时 map 中只有 3 个元素

        // 此时 1 不存在所以将 key = 1, value = "1One" 插入 map 中
        map.computeIfAbsent(1, k-> k + "One");
        Assertions.assertEquals("1One", map.get(1));


    }

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
    // 元素被 put 到 HashMap 中的时候，先对 key 求 hash，然后根据 hash % table.length 的值来确定存放到数组中的位置。
    // 而 hash % table.length 的一种更高效的替代计算方式是 hash&(table.length - 1)，前提是 (table.length - 1) 的
    // 二进制表示的低位部分全是 1，高位部分全是 0。
    // 例如: 0x0000_1111, 0x0011_1111, 0x0000_0111
    // 这样的值恰好为 2 的 n 次方减 1。所以 table.length 或者说哈希桶的数量是 2 的 n 次方。
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


    // put(K, V) 返回的值是与 K 关联的上一个 V
    @Test
    void put(){
        HashMap<String, Integer> map = new HashMap<>();

        // 此时不存在 key = "a"，所以 put 返回的值是 null
        Integer a = map.put("a", 1);
        Assertions.assertNull(a);

        // 此时存在 key = "a" 对应的 value = 1, 所以 put 方法返回的值是 1
        a = map.put("a", 2);
        Assertions.assertEquals(1, a);
    }
}