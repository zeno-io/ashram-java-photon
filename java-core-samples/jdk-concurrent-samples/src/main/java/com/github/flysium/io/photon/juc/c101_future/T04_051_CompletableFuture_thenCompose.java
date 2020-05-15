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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * CompletableFuture 对计算结果的组合
 *
 * @author Sven Augustus
 */
public class T04_051_CompletableFuture_thenCompose {

  // 对计算结果的组合 A +--> B +---> C：
  //  <U> CompletableFuture<U> 	thenCompose(Function<? super T,? extends CompletionStage<U>> fn)
  //  <U> CompletableFuture<U> 	thenComposeAsync(Function<? super T,? extends CompletionStage<U>> fn)
  //  <U> CompletableFuture<U> 	thenComposeAsync(Function<? super T,? extends CompletionStage<U>> fn, Executor executor)

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    long start = System.currentTimeMillis();

    CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
      millSleep(500);
      return 100;
    });
    CompletableFuture<String> f = f1.thenCompose(i -> {
      return CompletableFuture.supplyAsync(() -> {
        millSleep(300);
        return (i * 10) + "";
      });
    });
    System.out.println(f.get());

    System.out.println((System.currentTimeMillis() - start) + "ms");
  }

  private static void millSleep(long timeout) {
    try {
      TimeUnit.MILLISECONDS.sleep(timeout);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
