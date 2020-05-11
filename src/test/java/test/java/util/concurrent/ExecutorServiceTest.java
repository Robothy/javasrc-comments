package test.java.util.concurrent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceTest {

    @Test
    void shutdown() throws InterruptedException {

        Runnable task1 =  ()->{
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " task1 executed.");
        };

        Runnable task2 =  ()->{
            System.out.println(Thread.currentThread() + " task2 executed.");
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(task1);
        executorService.submit(task2);
        executorService.shutdown(); // 由于task1 至少执行 10ms，此时 task2 还没有执行，但由于 task2 已经提交，它仍然会执行

        // 这句会抛出 RejectedExecutionException，已经关闭的线程池不再接收新的任务
        Assertions.assertThrows(RejectedExecutionException.class, ()-> executorService.submit(task1));

        Thread.sleep(1000); // 确保线程池有足够的时间执行任务，避免 test 方法退出中断线程池执行任务
    }

    @Test
    void shutdownNow() throws InterruptedException, ExecutionException {

        Runnable task1 =  ()->{
            while (true){
                if(Thread.currentThread().isInterrupted()){ // 不忽略中断请求，如果线程被中断就退出
                    System.out.println(Thread.currentThread() + " task1 exit.");
                    break;
                }
            }
        };

        Runnable task2 =  ()->{
            System.out.println(Thread.currentThread() + " task2 executed.");
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(task1);
        executorService.submit(task2);

        // shutdownNow 会向正在执行任务的线程发起中断请求，
        // 但是线程中的任务可以忽略中断请求
        // 由于task1 至少执行 10ms，此时 task2 还没有执行，
        // 但 task2 不会再执行， task2 会返回
        List<Runnable> runnables = executorService.shutdownNow();
        Assertions.assertEquals(1, runnables.size());  // 只有 1 个元素，即 task2

        // 这句会抛出 RejectedExecutionException，已经关闭的线程池不再接收新的任务
        Assertions.assertThrows(RejectedExecutionException.class, ()->executorService.submit(task1));

        Thread.sleep(1000); // 确保线程池有足够的时间执行任务，避免 test 方法退出中断线程池执行任务
    }


    // submit 调用之后不会阻塞等待任务执行完，程序会继续往下走。
    @Test
    void isSubmitBlocking() throws InterruptedException, ExecutionException {
        Callable<Void> task = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task Finished.");
            return null;
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Void> future = executorService.submit(task);
        System.out.println("Got Futures."); // 这句话比 Task Finished 后打印，证明 submit 不会阻塞等待线程执行完
        future.get();
        System.out.println("Got Values");

        executorService.shutdown();
        executorService.awaitTermination(1,  TimeUnit.HOURS);
    }

    // 与 submit 不同， invokeAll 调用时会阻塞，直到所有的任务都结束
    @Test
    void isInvokeAllBlocking() throws InterruptedException, ExecutionException {
        Callable<Void> task = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task Finished.");
            return null;
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        List<Callable<Void>> tasks = Collections.singletonList(task);
        List<Future<Void>> futures = executorService.invokeAll(tasks);
        System.out.println("Got Futures."); // 这句话在 Task Finished 后面打印，证明 invokeAll 会阻塞，等到任务执行完。
        futures.get(0).get();
        System.out.println("Got Values");

        executorService.shutdown();
        executorService.awaitTermination(1,  TimeUnit.HOURS);
    }

    // invokeAny 调用时会阻塞，直到有一个任务完成为止。
    // 此时不再执行未执行的任务，并且向正在执行的任务发送中断请求。
    @Test
    void isInvokeAnyBlocking() throws InterruptedException, ExecutionException {
        Callable<Void> task = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //Thread.currentThread().interrupt();
                System.out.println("Caught InterruptedException");
            }
            System.out.println("A Task Finished.");
            return null;
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        List<Callable<Void>> tasks = Arrays.asList(task, task, task);
        executorService.invokeAny(tasks);
        System.out.println("Got Futures.");

        //输出顺序为
        // A Task Finished.
        // Caught InterruptedException
        // Got Futures.
        // A Task Finished.
        // 或者
        // A Task Finished.
        // Got Futures.
        // Caught InterruptedException
        // A Task Finished.
        executorService.shutdown();
        executorService.awaitTermination(1,  TimeUnit.HOURS);
    }




}
