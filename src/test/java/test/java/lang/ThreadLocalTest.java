package test.java.lang;

import org.junit.jupiter.api.Test;

// ThreadLocal 对象为每个线程单独分配一个指定对象
public class ThreadLocalTest {

    ThreadLocal<A> localA = ThreadLocal.withInitial(A::new);

    @Test
    void test() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread() + ": " + localA.get().hashCode());
            System.out.println(Thread.currentThread() + ": " + localA.get().hashCode());
            System.out.println(Thread.currentThread() + ": " + localA.get().hashCode());
        });

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread() + ": " + localA.get().hashCode());
            System.out.println(Thread.currentThread() + ": " + localA.get().hashCode());
            System.out.println(Thread.currentThread() + ": " + localA.get().hashCode());
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }


}

class A {

}