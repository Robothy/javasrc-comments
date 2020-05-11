package test.java.lang;

import org.junit.jupiter.api.Test;

public class DeadLockTest {

    //死锁测试
    @Test
    void testDeadLock() throws InterruptedException {

        A a = new A(1, 2);

        Thread t1 = new Thread(() -> {
            try {
                a.method1();    // method1 先获得了对象 a = 1 的锁，等着获取对象 b = 2 的锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.interrupt();

        Thread t2 = new Thread(() -> {
            try {
                a.method2();    // method2 先获取了对象 b = 2 的锁，等着获取对象 a = 1 的锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    static class A {

        private final Integer a;
        private final Integer b;

        public A(Integer a, Integer b){
            this.a = a;
            this.b = b;
        }

        public void method1() throws InterruptedException {
            synchronized (a){
                Thread.sleep(100);
                synchronized (b){

                }
            }
        }

        public void method2() throws InterruptedException {
            synchronized (b){
                Thread.sleep(100);
                synchronized (a){
                }
            }
        }
    }

}
