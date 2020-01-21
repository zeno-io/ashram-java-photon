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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * CompletableFuture 管理多个Future的结果
 *
 * @author Sven Augustus
 */
public class T04_011_CompletableFuture_join_get {

  // 像 Future 一样通过阻塞或者轮询的方式获得结果，尽管这种方式不推荐使用。

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
      System.out.println(Thread.currentThread().getName() + " call !");
      return 100;
    });
    System.out.println("main");
//    System.out.println( future.join());
    System.out.println(future.get());

    try {
      CompletableFuture<Integer> exceptionFuture = CompletableFuture.supplyAsync(() -> {
        int i = 1 / 0;
        return 100;
      });
//exceptionFuture.join();
      exceptionFuture.get();
    } catch (RuntimeException e) {
      e.printStackTrace();
    }

  }

}
