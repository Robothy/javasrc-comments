package test.java.util.concurrent.atomic;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.LongAccumulator;

// Accumulator 原理和 Adder 几乎一样，但功能更加强大，它提供了用户自定义的 "accumulator"
// 可以认为 Adder 是 Accumulator 的一种特殊实现
public class LongAccumulatorTest {

    @Test
    void test(){
        LongAccumulator longAccumulator = new LongAccumulator(Long::sum,0);
        longAccumulator.accumulate(10);
        System.out.println(longAccumulator.get());
    }

}
