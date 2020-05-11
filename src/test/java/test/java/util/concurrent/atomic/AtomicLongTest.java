package test.java.util.concurrent.atomic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongTest {

    @Test
    void test1(){
        AtomicLong atomicLong = new AtomicLong(1); // 初始化值为 1

        long value = atomicLong.addAndGet(3);// 原子操作，加 3 并返回值
        Assertions.assertEquals(4, value);// value 值为 4

        value = atomicLong.getAndAdd(3); // 原子操作，返回当前值之后再加 3
        Assertions.assertEquals(4, value); // value 返回的值仍然为 4，
        Assertions.assertEquals(7, atomicLong.get()); // 但 atomicLong 内部的值变为了 7

        // 自定义累积器对 AtomicLong 的值进行操作，然后返回。
        // 累积器对 AtomicLong 的值进行减 2 操作。
        // 累积器为 LongBinaryOperator
        // a 为原来的值，b为传入的值，也就是2，
        value = atomicLong.accumulateAndGet(2, (a, b) -> a - b); // 原子操作
        Assertions.assertEquals(5, value);

        // 返回atomicLong的值，并自定义累积器对 AtomicLong 的值进行操作。
        value = atomicLong.getAndAccumulate(1, (a, b) -> a + b + 1); // 原子操作，
        Assertions.assertEquals(5, value);
        Assertions.assertEquals(7, atomicLong.get());

        value = atomicLong.getAndSet(1); // 原子操作
        Assertions.assertEquals(7, value);
        Assertions.assertEquals(1, atomicLong.get()); // 非原子操作

        atomicLong.set(1);  // 非原子操作
        Assertions.assertEquals(1, atomicLong.get());
        atomicLong.lazySet(2); // 原子操作
        Assertions.assertEquals(2, atomicLong.get());
    }


    // AtomicLong 操作时保证线程安全
    @Test
    void test() throws InterruptedException {

        MyLongAdder adder1 = new MyLongAdder();
        MyAtomicLongAdder adder2 = new MyAtomicLongAdder();

        Runnable task1 = ()->{
            int i = 10000;
            while (i-->0) adder1.increment();
        };

        Runnable task2 = ()->{
            int i = 10000;
            while (i-->0) adder2.increment();
        };

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(task1);
        executorService.submit(task1);

        executorService.submit(task2);
        executorService.submit(task2);

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        Assertions.assertNotEquals(20000, adder1.get()); // 大概率不会等于 20000
        Assertions.assertEquals(20000, adder2.get()); // 必然等于 20000
    }

    private static class MyLongAdder{

        private long value = 0;

        public void increment(){
            value++;
        }

        public long get(){
            return value;
        }
    }

    private static class MyAtomicLongAdder{

        private AtomicLong value;

        public MyAtomicLongAdder(){
            value = new AtomicLong();
        }

        public void increment(){
            value.addAndGet(1L);
        }

        public long get(){
            return value.get();
        }
    }
}



