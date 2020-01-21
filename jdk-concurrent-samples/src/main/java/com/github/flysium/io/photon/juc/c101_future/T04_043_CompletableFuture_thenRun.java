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
 * CompletableFuture
 *
 * @author Sven Augustus
 */
public class T04_043_CompletableFuture_thenRun {

  // 当计算完成的时候会执行一个Runnable,与thenAccept不同，Runnable并不使用CompletableFuture计算的结果。
  //  CompletableFuture<Void> 	thenRun(Runnable action)
  //  CompletableFuture<Void> 	thenRunAsync(Runnable action)
  //  CompletableFuture<Void> 	thenRunAsync(Runnable action, Executor executor)

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
      return 100;
    });

    CompletableFuture<Void> f = f1.thenRun(() -> {
      System.out.println(Thread.currentThread().getName() + " run!");
    });

    System.out.println(f.get());
  }

}
