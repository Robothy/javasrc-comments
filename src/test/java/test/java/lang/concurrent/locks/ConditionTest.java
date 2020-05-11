package test.java.lang.concurrent.locks;

import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {

    public static void main(String[] args){
        int initialAmount = 1000;
        int threadNum = 10;
        int[] transferredAmount = new int[threadNum];
        for(int i=0; i<threadNum; i++){

        }

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
                    System.err.printf("Insufficient Amount! balance: %d, transfer amount: %d .\n", this.amount, transferAmount);
                    return 0;
                }else {
                    this.amount -= transferAmount;
                    System.out.printf("Balance Amount: %d, balance: %d", amount, transferAmount);
                }
            }finally {
                reentrantLock.unlock();
            }
            return transferAmount;
        }
    }

}
