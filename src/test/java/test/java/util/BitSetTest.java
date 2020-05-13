package test.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

// BitSet 可以认为是一种存储比特位的数组，它的数据结构是一个 long 数组
// 没一个元素对应着 long 类型数组某个元素的比特位。这使得它比 Boolean 对象
// 的 ArrayList 更加高效。
// 在需要使用一组标志的时候可以考虑使用位集 BitSet
public class BitSetTest {

    @Test
    void test(){
        BitSet bitSet = new BitSet();
        bitSet.set(1); // 将 1 位置设置为 “open”状态
        Assertions.assertTrue(bitSet.get(1)); // 此时 get(1) 将返回 true
        Assertions.assertFalse(bitSet.get(0)); // 由于 0 位置没有设置，所以返回 false
        bitSet.clear(1); // 将 1 位置置为“close”状态
        Assertions.assertFalse(bitSet.get(1)); // 此时 get(1) 将返回 false

        bitSet.set(0); // 将 0 位置设置为 "open"
        Assertions.assertTrue(bitSet.get(0)); // 此时 get(0) 返回 true
    }

}
