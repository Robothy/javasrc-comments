package test.java.util.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class CompletableFutureTest {

    // 常用 API
    @Test
    void test() throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> completableFuture = CompletableFuture.completedFuture(1)
                .thenApply(n -> {   // thenApply 参数是一个 BiFunction， 返回值任意
                    n++;
                    System.out.println(Thread.currentThread() + ", n = " + n); // n = 2， 线程为当前线程
                    return n;
                })
                .thenApplyAsync(n -> {  // 异步执行，执行器默认为一个 ForkJoinPool
                    n++;
                    System.out.println(Thread.currentThread() + ", n = " + n); // n = 3，线程为 ForkJoinPool中的一个线程
                    return n;
                })
                .thenApplyAsync(n -> { // 异步执行，并指定执行器
                    n++;
                    System.out.println(Thread.currentThread() + ", n = " + n); // n = 4，线程为线程池中的某个线程
                    return n;
                }, Executors.newFixedThreadPool(5))
                .thenApply(n -> {
                    n++;
                    System.out.println(Thread.currentThread() + ", n = " + n); // n = 5, 线程为
                    return n;
                })
                .thenApply(n -> {
                    n++;
                    System.out.println(Thread.currentThread() + ", n = " + n);
                    return n;
                })
                .thenApply(n -> {
                    n++;
                    System.out.println(Thread.currentThread() + ", n = " + n);
                    return n;
                })
                .thenCompose(n -> { // 与 thenApply 方法不同，thenCompose 的参数是一个返回 CompletableFuture 的函数
                    n++;
                    System.out.println(Thread.currentThread() + ", n = " + n);
                    return CompletableFuture.completedFuture(n);
                }).thenApply(n -> {
                    n++;
                    System.out.println(Thread.currentThread() + ", n = " + n);
                    return n;
                });
        Integer n = completableFuture.get();
    }

    // 得到 CompletableFuture
    @Test
    void runAsync() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        CompletableFuture.supplyAsync(() -> "Hello", executorService).thenAccept(s -> {
            System.out.println(Thread.currentThread() + s);
        });
        // 并行运行
        CompletableFuture.supplyAsync(() -> "World", executorService).thenAccept(s -> {
            System.out.println(Thread.currentThread() + s);
        });

    }

    @Test
    void combine() throws ExecutionException, InterruptedException {

        CompletableFuture.completedFuture(1)
                .thenCombine(CompletableFuture.completedFuture(1), Integer::sum)
                .thenAcceptBoth(CompletableFuture.completedFuture(1), (a, b) -> {
                    System.out.println(a + b); // 3
                });

    }


}
