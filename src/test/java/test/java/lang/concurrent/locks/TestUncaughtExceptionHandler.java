package test.java.lang.concurrent.locks;

public class TestUncaughtExceptionHandler {

    void test(){

        ThreadGroup parentThreadGroup = new ThreadGroup("Parent");

        ThreadGroup threadGroup = new ThreadGroup(parentThreadGroup, "Children");



    }

}
