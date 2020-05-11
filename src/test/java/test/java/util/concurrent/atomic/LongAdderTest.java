package test.java.util.concurrent.atomic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

// LongAdder 提供了线程安全且速度相对较快的加法操作。
// LongAdder 为不同的线程分配了不同的计数单元（存放在 Cell[] 中），
// 不同线程调用 increment 操作时，相应地不同的计数单元的值发生变化，
// 即不同线程不共享变量。
//
// 最后要获取统计值是，调用 sum 方法，LongAdder将会对不同计数单元的值
// 累加再返回。
public class LongAdderTest {

    @Test
    void test() throws InterruptedException {

        MyLongAdder adder1 = new MyLongAdder();
        LongAdder adder2 = new LongAdder();

        Runnable task1 = ()->{  // 对自定义地非线程安全的 MyLongAdder 执行操作
            int i = 10000;
            while (i-->0) adder1.increment();
        };

        Runnable task2 = ()->{ // 对线程安全的 LongAdder 执行操作
            int i = 10000;
            while (i-->0) adder2.increment();
        };

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(task1); // 两个线程同时对同一个非线程安全的 MyLongAdder 执行操作
        executorService.submit(task1);

        executorService.submit(task2);// 两个线程同时对同一个线程安全的 LongAdder 执行操作
        executorService.submit(task2);

        // 等待执行结束
        executorService.shutdown(); // shutdown 会等待已经提交的任务执行完成，但是不阻塞
        executorService.awaitTermination(1, TimeUnit.MINUTES); // 阻塞，直到执行器被关闭或者时间到了

        Assertions.assertNotEquals(20000, adder1.get()); // MyLongAdder 非线程安全，一般不会等于 20000
        Assertions.assertEquals(20000, adder2.sum()); // LongAdder 线程安全，结果必然等于 20000
    }

}

class MyLongAdder{

    private long value = 0;

    public void increment(){
        value++;
    }

    public long get(){
        return value;
    }
}
