package test.java.util.concurrent;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExecutorCompletionServiceTest {

    // 通过 ExecutorCompletionService 来提交任务可以按照任务的完成顺序返回任务执行结果。
    @Test
    void test() throws InterruptedException, ExecutionException {

        // 定义5个任务，分别延时 n * 100 毫秒，并返回 n
        // 很显然，这 5 个任务在并发执行的情况下， n 为 1 的任务最先完成的可能性最大。
        List<Callable<Integer>> tasks = Stream.of(5, 4, 3, 2, 1).map(e -> (Callable<Integer>) () -> {
            Thread.sleep(e * 100);
            return e;
        }).collect(Collectors.toList());

        // 创建容量为 5 的固定容量线程池，保证 5 个任务分配到不同的线程中执行。
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // 对照组，通过普通的方式提交
        List<Future<Integer>> futures = executorService.invokeAll(tasks);
        for (Future<Integer> future : futures) { // 大概率输出 5, 4, 3, 2, 1
            System.out.println(future.get());
        }

        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executorService);

        // 实验组，通过 ExecutorCompletionService 提交
        for (Callable<Integer> task : tasks) {
            completionService.submit(task);
        }

        for(int i=0; i<tasks.size(); i++){ // 大概率输出 1 2 3 4 5
            // 调用 take 放法时，当前线程阻塞，直到completionService中有任务完成。
            Future<Integer> take = completionService.take();
            System.out.println(take.get());
        }

        // 如果 completionService 中没有任务了，调用 take 也会阻塞
        // 代码将“卡”一直在这里。
        completionService.take();

        // poll 方法不会阻塞，会立即返回结果，有任务完成了返回 Future 对象，否则返回 null
    }

}
