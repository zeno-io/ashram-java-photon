/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.juc.c102_threadpoolexecutor;

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
