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

package xyz.flysium.photon.c101_future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Runnable、Future + Callable
 *
 * @author Sven Augustus
 */
public class T01_Runnable_Future_Callable {

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        try {
          System.out.println(Thread.currentThread().getName() + " run !");
          TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    Callable<Long> callable = new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " call !");
        TimeUnit.MILLISECONDS.sleep(500);
        return 1L;
      }
    };

    new Thread(runnable, "t1").start();

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Future future = executor.submit(callable);

    System.out.println("main");

    System.out.println(future.isDone());
    // 阻塞等待结果
    System.out.println(future.get());
    // 阻塞等待，如果在超时时间内获得结果就直接返回，如果超时还没有结果将抛出异常
//    try {
//      System.out.println(future.get(200, TimeUnit.MILLISECONDS));
//      System.out.println(future.get(600, TimeUnit.MILLISECONDS));
//    } catch (TimeoutException e) {
//      e.printStackTrace();
//    }

    System.out.println(future.isDone());

    executor.shutdown();
  }

}
