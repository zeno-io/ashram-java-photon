/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon.c500_fiber;

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
