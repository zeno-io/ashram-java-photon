package com.github.flysium.io.photon.juc.c500_fiber;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.SuspendableRunnable;
import java.time.Instant;
import java.util.concurrent.ExecutionException;

/**
 * Fiber
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T01_Thread_vs_Fiber {

  public static final int N_THREADS = 100000;

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    System.out.println(runAsThread());
    System.out.println(runAsFiber());
  }

  public static int calc(int initialValue) {
    int result = initialValue;
    for (int i = 0; i < 1000; i++) {
      for (int j = 0; j < 200; j++) {
        result += i;
      }
    }
    return result;
  }

  private static long runAsThread() throws InterruptedException {
    Runnable run = () -> {
      calc(100);
    };
    long start = Instant.now().toEpochMilli();

    Thread[] threads = new Thread[N_THREADS];
    for (int i = 0; i < N_THREADS; i++) {
      threads[i] = new Thread(run);
    }
    for (int i = 0; i < N_THREADS; i++) {
      threads[i].start();
    }
    for (int i = 0; i < N_THREADS; i++) {
      threads[i].join();
    }
    return Instant.now().toEpochMilli() - start;
  }

  private static long runAsFiber() throws ExecutionException, InterruptedException {
    SuspendableRunnable run = () -> {
      calc(100);
    };
    long start = Instant.now().toEpochMilli();

    Fiber<Void>[] fibers = new Fiber[N_THREADS];
    for (int i = 0; i < N_THREADS; i++) {
      fibers[i] = new Fiber<Void>(run);
    }
    for (int i = 0; i < N_THREADS; i++) {
      fibers[i].start();
    }
    for (int i = 0; i < N_THREADS; i++) {
      fibers[i].join();
    }
    return Instant.now().toEpochMilli() - start;
  }

}
