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

package xyz.flysium.photon.c102_threadpoolexecutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Executor 、ExecutorService、Runnable、Future + Callable
 *
 * @author Sven Augustus
 */
public class T01_Executor_ExecutorService {

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    // ExecutorService executor = Executors.newCachedThreadPool(); // new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    // ExecutorService executor = Executors.newFixedThreadPool(8); // new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,  new LinkedBlockingQueue<Runnable>());

    // 调用，并且不需要等待执行结果（fire-and-forget）
    // Runnable
    executor.execute(() -> {
      System.out.println(Thread.currentThread().getName() + " execute !");
    });
    System.out.println("main");

    // 提交任务，并且返回一个等待执行结果 Future，只有当调用 future.get() 是才真正阻塞等待结果
    //  Future + Callable
    Future<Long> future = executor.submit(() -> {
      System.out.println(Thread.currentThread().getName() + " submit !");
      TimeUnit.MILLISECONDS.sleep(500);
      return 1L;
    });
    System.out.println(future.get());

    executor.shutdown();
  }

}
