package test.java.lang.concurrent;

public class ThreadLocalOOMTest {

    private static int _1MB = 1024 * 1024;

    private static ThreadLocal<Byte[]> threadLocal;

    public static void main(String[] args){
        for(int i=0; i<10; i++){
            threadLocal = ThreadLocal.withInitial(() -> new Byte[_1MB]);
            threadLocal.get();
            threadLocal.remove();
        }
    }
}