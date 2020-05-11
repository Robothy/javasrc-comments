package test.java.lang.concurrent.locks;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {


    public static void main(String[] args) throws InterruptedException {
        int initialAmount = 1000;
        Account account = new Account(initialAmount);

        int threadNum = 10;

        int[] transferredAmounts = new int[threadNum];

        Random random = new Random();
        Thread[] threads = new Thread[threadNum];
        for (int i=0; i<threadNum; i++){
            int finalI = i;
            Thread thread = new Thread(() -> {
                transferredAmounts[finalI] = account.transfer(10);
            });
            thread.start();
            threads[i] = thread;
        }

        for(int i=0; i<threadNum; i++) threads[i].join();

        int sum = 0;
        for (int i=0; i<threadNum; i++){
            sum += transferredAmounts[i];
        }

        //Assertions.assertEquals(initialAmount, account.amount + sum);
    }

    private static class Account{

        private ReentrantLock reentrantLock;

        private int amount;

        public Account(int amount){
            this.amount = amount;
            reentrantLock = new ReentrantLock();
        }

        public int transfer(int transferAmount){
            reentrantLock.lock();
            try{
                if(this.amount < transferAmount){
                    System.out.printf(Thread.currentThread().getName() + ": Insufficient Amount! balance: %d, transfer amount: %d .\n", this.amount, transferAmount);
                    return 0;
                }else {
                    this.amount -= transferAmount;
                    System.out.printf(Thread.currentThread().getName() + ": Balance Amount: %d, balance: %d\n", amount, transferAmount);
                }
            }finally {
                reentrantLock.unlock();
            }
            return transferAmount;
        }
    }

}

