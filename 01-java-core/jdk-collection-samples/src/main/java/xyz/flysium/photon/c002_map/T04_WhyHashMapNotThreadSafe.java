package xyz.flysium.photon.c002_map;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

/**
 *
 * Why HashMap is not thread-safe ?
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T04_WhyHashMapNotThreadSafe {

    private static final String[] SAME_HASH_CODE = new String[] { "Aa", "BB" };

    public static void main(String[] s) throws InterruptedException, ExecutionException, TimeoutException {
        boolean b = batchTest(HashMap.class, 5);
        System.out.println("HashMap is " + (b ? "ThreadSafe" : "Not ThreadSafe"));

        b = batchTest(ConcurrentHashMap.class, 5);
        System.out.println("ConcurrentHashMap is " + (b ? "ThreadSafe" : "Not ThreadSafe"));
    }

    private static boolean batchTest(Class<? extends Map> clazz, int times)
        throws InterruptedException, ExecutionException, TimeoutException {
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(128), Executors.defaultThreadFactory(), new CallerRunsPolicy());

        final AtomicBoolean[] res = new AtomicBoolean[times];
        for (int i = 0; i < res.length; i++) {
            res[i] = new AtomicBoolean(false);
        }
        CompletableFuture.allOf(getAllTaskFutures(times, (batchIndex) -> {
            Map<String, Object> map = null;
            try {
                map = clazz.getDeclaredConstructor(int.class).newInstance(128);
            }
            catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            CompletableFuture<Void> f = test(map, 128);
            try {
                f.get(5, TimeUnit.SECONDS);
            }
            catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
            if (map.size() != 128) {
                // TODO 注意map的数量多次运行，可能是 128，可能是 126，可能卡住了(陷入死循环)？？？
                System.err.println(clazz.getSimpleName() + " is Not ThreadSafe !, size=" + map.size());
            }
            else {
                res[batchIndex].compareAndSet(false, true);
            }
            return null;
        }, executor)).join();
        executor.shutdown();
        boolean success = true;
        for (AtomicBoolean b : res) {
            if (Boolean.FALSE == b.get()) {
                success = false;
                break;
            }
        }
        return success;
    }

    private static CompletableFuture<Void> test(Map<String, Object> map, int elementSize) {
        String[] keys = new String[elementSize];
        for (int i = 0; i < elementSize; i++) {
            String x = Integer.toBinaryString(i);
            StringBuilder buf = new StringBuilder();
            for (int j = x.length() - 1; j >= 0; j--) {
                final int c = x.charAt(j) - '0';
                buf.append(SAME_HASH_CODE[c]);
            }
            keys[i] = buf.toString();
        }
        return CompletableFuture.allOf(getAllTaskFutures(elementSize, (index) -> {
            map.put(keys[index], Thread.currentThread().getId());
            return null;
        }, null));
    }

    private static <T> CompletableFuture<T>[] getAllTaskFutures(int elementSize, Function<Integer, T> supplier,
        Executor executor) {
        CompletableFuture<T>[] futures = new CompletableFuture[elementSize];

        for (int i = 0; i < elementSize; i++) {
            int index = i;
            futures[i] = executor == null ? CompletableFuture.supplyAsync(() -> {
                return supplier.apply(index);
            }) : CompletableFuture.supplyAsync(() -> {
                return supplier.apply(index);
            }, executor);
        }
        return futures;
    }

}
