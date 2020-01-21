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
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * FutureTask (Future + Runnable, 构造参数 Callable)
 *
 * @author Sven Augustus
 */
public class T02_FutureTask {

  // FutureTask类是Future 的一个实现，并实现了Runnable

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    FutureTask<Long> futureTask = new FutureTask<Long>(new Callable<Long>() {

      @Override
      public Long call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " call !");
        TimeUnit.MILLISECONDS.sleep(500);
        return 1L;
      }
    });
    new Thread(futureTask).start();

    System.out.println("main");

    System.out.println(futureTask.get());
  }

}
