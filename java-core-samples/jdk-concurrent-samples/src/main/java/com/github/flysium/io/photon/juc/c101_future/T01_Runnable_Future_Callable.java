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

package com.github.flysium.io.photon.juc.c101_future;

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
