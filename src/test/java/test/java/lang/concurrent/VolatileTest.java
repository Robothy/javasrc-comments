package test.java.lang.concurrent;

import org.junit.jupiter.api.Test;

public class VolatileTest {

    @Test
    void test(){
        WithoutVolatile withoutVolatile = new WithoutVolatile();
        withoutVolatile.start();
        for (;;){
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
            if(withoutVolatile.flag){
                System.out.println("HHHH");
            }
        }
    }

    static class WithoutVolatile extends Thread {
        private boolean  flag;
        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            flag = true;
        }
    }

}
