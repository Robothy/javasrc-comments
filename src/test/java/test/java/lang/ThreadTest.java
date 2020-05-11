package test.java.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ThreadTest {

    // 测试，线程不同步将导致数据出错
    @RepeatedTest(500)
    void threadTest() throws InterruptedException {
        Account account = new Account();
        Pay alipay = new Pay(account, 1000);
        Pay wechatPay = new Pay(account, 1000);
        alipay.start();
        wechatPay.start();
        alipay.join();
        wechatPay.join();
        // 可能失败，两个线程同时扣款
        Assertions.assertEquals(1000, account.amount);
    }

    //使用 join 阻塞线程
    @RepeatedTest(500)
    void threadTestWithJoin() throws InterruptedException {
        Account account = new Account();
        Pay alipay = new Pay(account, 1000);
        Pay wechatPay = new Pay(account, 1000);
        alipay.start();
        wechatPay.start();

        alipay.join();
        wechatPay.join();
        // 由于 withdraw 方法由 synchronized 修饰，因此不会出现线程不同步问题。
        // 全部成功
        Assertions.assertEquals(1000, account.amount);
    }

    // 使用 synchronized 关键字修饰方法，实现同步
    // 使用 synchronized 修饰方法时，锁住的是整个对象，不同对象之间访问静态成员变量，还是会出现线程不同步的问题
    @Test
    void testSynchronize() throws InterruptedException {
        SynchronizedAccount account = new SynchronizedAccount();
        SynchronizedPay alipay = new SynchronizedPay(account, 1000);
        SynchronizedPay wechatPay = new SynchronizedPay(account, 1000);
        alipay.start();
        wechatPay.start();

        alipay.join();
        wechatPay.join();
        // 由于 withdraw 方法由 synchronized 修饰，因此不会出现线程不同步问题。
        // 全部成功
        Assertions.assertEquals(1000, account.amount);
    }

    class Pay extends Thread{
        private Account account;
        private int amount;
        public Pay(Account account, int amount){
            this.account = account;
            this.amount = amount;
        }

        @Override
        public void run() {
            account.withdraw(amount);
        }
    }

    class Account{
        public int amount = 3000;
        public int withdraw(int amt){
            amount -= amt;
            return amt;
        }
    }

    class SynchronizedPay extends Thread{
        private SynchronizedAccount account;
        private int amount;

        public SynchronizedPay(SynchronizedAccount account, int amount) {
            this.account = account;
            this.amount = amount;
        }

        @Override
        public void run() {
            account.withdraw(amount);
        }
    }

    class SynchronizedAccount{
        public int amount = 3000;
        public synchronized int withdraw(int amt){
            amount -= amt;
            return amt;
        }
    }

}

