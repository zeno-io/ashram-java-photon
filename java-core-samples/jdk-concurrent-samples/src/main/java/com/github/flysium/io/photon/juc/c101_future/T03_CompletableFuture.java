/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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
 * CompletableFuture 管理多个Future的结果
 *
 * @author Sven Augustus
 */
public class T03_CompletableFuture {

  // 像 Future 一样通过阻塞或者轮询的方式获得结果，尽管这种方式不推荐使用。

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    long start = System.currentTimeMillis();

    CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
      millSleep(1000);
      System.out.println("f1");
      return 100;
    });
    CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
      millSleep(500);
      System.out.println("f2");
      return 50;
    });
    CompletableFuture<Integer> f3 = CompletableFuture.supplyAsync(() -> {
      millSleep(1500);
      System.out.println("f3");
      return 80;
    });

    // 所有任务完成
    CompletableFuture.allOf(f1, f2, f3).join();

    // 获得最早完成的任务结果
//    System.out.println( CompletableFuture.anyOf(f1, f2, f3).join());

    // 以下可以把多个 future 链接起来, 并且可以轻松组合计算
//    System.out.println(f1.thenCombine(f2, (x, y) -> x + y).thenCombine(f3, (t, z) -> t + z).get());

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
