package xyz.flysium.photon.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * TODO description
 *
 * @author zeno
 */
public final class TestSupport {

  private TestSupport() {
  }

  public static String toMillisString(long nanoTime) {
    long ms = nanoTime / 1000000;
    return ms + ((nanoTime == ms) ? "" : (". " + (nanoTime - ms))) + " ms ";
  }

  public static <T> MyConsumer<T>[] combine(MyConsumer<T> c, int times) {
    return combine(null, c, times);
  }

  public static <T> MyConsumer<T>[] combine(MyConsumer<T> c1, int times1, MyConsumer<T> c2,
    int times2) {
    return combine(combine(null, c1, times1), c2, times2);
  }

  public static <T> MyConsumer<T>[] combine(MyConsumer<T>[] list, MyConsumer<T> c, int times) {
    int srcLength = 0;
    if (list == null) {
      list = new MyConsumer[times];
    } else {
      srcLength = list.length;
      MyConsumer<T>[] newList = new MyConsumer[times + srcLength];
      System.arraycopy(list, 0, newList, 0, srcLength);
      list = newList;
    }
    for (int i = 0; i < times; i++) {
      list[srcLength + i] = c;
    }
    return list;
  }

  public static <T, U> MyBiConsumer<T, U>[] combine(MyBiConsumer<T, U> c, int times) {
    return combine(null, c, times);
  }

  public static <T, U> MyBiConsumer<T, U>[] combine(MyBiConsumer<T, U> c1, int times1,
    MyBiConsumer<T, U> c2,
    int times2) {
    return combine(combine(null, c1, times1), c2, times2);
  }

  public static <T, U> MyBiConsumer<T, U>[] combine(MyBiConsumer<T, U>[] list, MyBiConsumer<T, U> c,
    int times) {
    int srcLength = 0;
    if (list == null) {
      list = new MyBiConsumer[times];
    } else {
      srcLength = list.length;
      MyBiConsumer<T, U>[] newList = new MyBiConsumer[times + srcLength];
      System.arraycopy(list, 0, newList, 0, srcLength);
      list = newList;
    }
    for (int i = 0; i < times; i++) {
      list[srcLength + i] = c;
    }
    return list;
  }

  @SafeVarargs
  public static <T> long testWithMyConsumerOneTime(
    Supplier<T> instSupplier,
    MyConsumer<T>... list)
    throws InterruptedException {
    return testWithMyConsumer(1, true, instSupplier, list);
  }

  @SafeVarargs
  public static <T> long testWithMyConsumerOneTime(boolean createEachTime,
    Supplier<T> instSupplier,
    MyConsumer<T>... list)
    throws InterruptedException {
    return testWithMyConsumer(1, createEachTime, instSupplier, list);
  }

  @SafeVarargs
  public static <T> long testWithMyConsumer(int times,
    Supplier<T> instSupplier,
    MyConsumer<T>... list)
    throws InterruptedException {
    return testWithMyConsumer(times, true, instSupplier, list);
  }

  @SafeVarargs
  public static <T> long testWithMyConsumer(int times, boolean createEachTime,
    Supplier<T> instSupplier,
    MyConsumer<T>... list)
    throws InterruptedException {
    MyBiFunction<T, Void, Void>[] l = new MyBiFunction[list.length];
    Arrays.stream(list).map(x -> {
      return (MyBiFunction<T, Void, Void>) (t, u) -> {
        x.accept(t);
        return null;
      };
    }).collect(Collectors.toList()).toArray(l);
    return testWithMyBiFunction(times, createEachTime, instSupplier, null, l);
  }

  @SafeVarargs
  public static <T, U> long testWithMyBiConsumer(int times, boolean createEachTime,
    Supplier<T> instSupplier, U u,
    MyBiConsumer<T, U>... list)
    throws InterruptedException {
    MyBiFunction<T, U, Void>[] l = new MyBiFunction[list.length];
    Arrays.stream(list).map(x -> (MyBiFunction<T, U, Void>) (t, u0) -> {
      x.accept(t, u0);
      return null;
    }).collect(Collectors.toList()).toArray(l);
    return testWithMyBiFunction(times, createEachTime, instSupplier, u, l);
  }

  @SafeVarargs
  public static <T, U, R> long testWithMyBiFunction(int times, boolean createEachTime,
    Supplier<T> instSupplier, U u,
    MyBiFunction<T, U, R>... list)
    throws InterruptedException {
    final int maxPoolSize = list.length;
    final ThreadPoolExecutor executor = new ThreadPoolExecutor(
      maxPoolSize, maxPoolSize,
      60, TimeUnit.SECONDS,
      new LinkedBlockingQueue<>(),
      Executors.defaultThreadFactory(),
      new AbortPolicy());

    long start = 0L;
    if (!createEachTime) {
      final List<Callable<R>> collect = getCallables(instSupplier, u, list);
      start = System.nanoTime();
      for (int i = 0; i < times; i++) {
        executor.invokeAll(collect);
      }
    } else {
      start = System.nanoTime();
      for (int i = 0; i < times; i++) {
        final List<Callable<R>> collect = getCallables((Supplier<T>) instSupplier, u,
          (MyBiFunction<T, U, R>[]) list);
        executor.invokeAll(collect);
      }
    }
    executor.shutdown();
    return (System.nanoTime() - start);
  }

  private static <T, U, R> List<Callable<R>> getCallables(final Supplier<T> instSupplier, final U u,
    final MyBiFunction<T, U, R>[] list) {
    final T finalInst = instSupplier.get();
    return Arrays.stream(list).map(x -> (Callable<R>) () -> {
      try {
        return x.apply(finalInst, u);
      } catch (Throwable throwable) {
        throwable.printStackTrace();
      }
      return null;
    }).collect(Collectors.toList());
  }

  @FunctionalInterface
  public static interface MyConsumer<T> {

    void accept(T t) throws Throwable;
  }

  @FunctionalInterface
  public static interface MyBiConsumer<T, U> {

    void accept(T t, U u) throws Throwable;
  }

  @FunctionalInterface
  public static interface MyFunction<T, R> {

    R apply(T t) throws Throwable;
  }


  @FunctionalInterface
  public static interface MyBiFunction<T, U, R> {

    R apply(T t, U u) throws Throwable;
  }
}
