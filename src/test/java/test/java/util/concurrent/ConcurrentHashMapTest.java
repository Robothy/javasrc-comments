package test.java.util.concurrent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentHashMapTest {


    // 即使使用了 ConcurrentHashMap，下面这种写法仍然非线程安全
    @Test
    void test() throws InterruptedException {
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        String key = "count";

        // 对 count 元素的值做 10000 次加 1 操作。
        Runnable task = ()->{
            for(int i=0; i<10000; i++){ // 因为循环体里面的三句代码非原子操作
                Integer count = concurrentHashMap.get(key);
                concurrentHashMap.put(key, count == null ? 1 : count + 1);
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(task);
        executorService.execute(task);
        executorService.execute(task);
        executorService.execute(task);
        executorService.execute(task);
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        Assertions.assertTrue(executorService.isShutdown());
        Assertions.assertNotEquals(50000, concurrentHashMap.get(key));
    }

    // 老版本 SDK 的正确写法，使用 replace 方法
    @Test
    void replace() throws InterruptedException {
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        String key = "count";
        concurrentHashMap.put(key, 0);
        Runnable task = ()->{
            for(int i=0; i<10000; i++){ // 因为循环体里面的三句代码非原子操作
                Integer oldValue;
                do {
                    oldValue = concurrentHashMap.get(key);
                    // replace 方法是原子操作，它会去比较传入的 oldValue 和 key 映射的实际 value，
                    // 如果一致，则执行操作，返回 true
                    // 否则，返回 false
                }while (!concurrentHashMap.replace(key, oldValue, oldValue + 1));


                // 或者写成
                // while (!concurrentHashMap.replace(key, concurrentHashMap.get(key), concurrentHashMap.get(key)+1));
                // 这是因为第 2 个 get 拿到的数据比第 1 个 get 拿到的数据更新，而 replace 对数据进行验证用的是第 1 个 get 拿到的数据
                // 如果第 1 个 get 拿到的数据验证通过，那第 2 个 get 拿到的数据肯定也能验证通过。
                // 不过更倾向于上一种写法，不需要两次 get
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(task);
        executorService.execute(task);
        executorService.execute(task);
        executorService.execute(task);
        executorService.execute(task);

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        Assertions.assertTrue(executorService.isShutdown());
        Assertions.assertEquals(50000, concurrentHashMap.get(key));
    }

    // 现在的 SDK 有更简洁的写法。
    @Test
    void compute() throws InterruptedException {
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        String key = "count";

        Runnable task = ()->{
            for(int i=0; i<10000; i++){ // compute 调用本本身就是一个原子操作，它接受元素的 key 和一个 BiFunction
                concurrentHashMap.compute(key, (k,  v)-> v == null ? 1 : v+1);
                //或者使用 computeIfAbsent 加 computeIfPresent
                //concurrentHashMap.computeIfAbsent(key, (k)->1);
                //concurrentHashMap.computeIfPresent(key, (k, v)-> v+1);

                // 或者使用 merge
                // concurrentHashMap.merge(key, 1, (existingValue, newValue)-> existingValue + newValue);
                // 或者写成 concurrentHashMap.merge(key, 1, Integer::sum);
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(task);
        executorService.execute(task);
        executorService.execute(task);

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        Assertions.assertTrue(executorService.isShutdown());
        Assertions.assertEquals(30000, concurrentHashMap.get(key));

    }

    @Test
    void t(){
        List<Integer> integers = new ArrayList<Integer>(){{
            add(1);
            add(2);
            add(3);
        }};

        for (Integer integer : integers) {
            //integers.remove(1);
        }

        for (int i = 0; i < integers.size(); ) {
            integers.remove(i++);
        }

        System.out.println(integers);
    }

}
