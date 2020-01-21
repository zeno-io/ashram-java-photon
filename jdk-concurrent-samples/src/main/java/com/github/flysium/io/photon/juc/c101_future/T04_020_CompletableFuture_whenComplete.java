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
 * CompletableFuture 计算结果完成时的处理(执行，并返回同样的计算)
 *
 * @author Sven Augustus
 */
public class T04_020_CompletableFuture_whenComplete {

  // 计算结果完成时的处理(执行，并返回同样的计算)：
  //  CompletableFuture<T> 	whenComplete(BiConsumer<? super T,? super Throwable> action)
  //  CompletableFuture<T> 	whenCompleteAsync(BiConsumer<? super T,? super Throwable> action)
  //  CompletableFuture<T> 	whenCompleteAsync(BiConsumer<? super T,? super Throwable> action, Executor executor)
  //  CompletableFuture<T>  exceptionally(Function<Throwable,? extends T> fn)

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> {
      System.out.println(Thread.currentThread().getName() + " run ! ");
      int i = 1 / 0;
      return 100;
    })
        // 在之前的CompletableFuture执行的线程中执行。
//        .whenComplete((v, e) -> {
//          if (e == null) {
//            System.out.println(Thread.currentThread().getName() + " ---- " + v);
//          } else {
//            System.err.println(e);
//          }
//        })
        // 会在新的线程池中执行
//        .whenCompleteAsync((v, e) -> {
//           if (e == null) {
//             System.out.println(Thread.currentThread().getName() + " ---- " + v);
//           } else {
//             System.err.println(Thread.currentThread().getName() + " ---- " + e);
//           }
//         })
        .exceptionally((e) -> {
          System.err.println(Thread.currentThread().getName() + " ---- " + e);
          return 288;
        });

    System.out.println(f.get());
  }

}
