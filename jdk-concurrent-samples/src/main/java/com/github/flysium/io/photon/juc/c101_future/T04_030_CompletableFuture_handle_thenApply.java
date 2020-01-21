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
 * CompletableFuture 计算结果完成时的转换 (执行，并返回新的计算)
 *
 * @author Sven Augustus
 */
public class T04_030_CompletableFuture_handle_thenApply {

  // 计算结果完成时的转换 (执行，并返回新的计算)：
  //  <U> CompletableFuture<U> 	thenApply(Function<? super T,? extends U> fn)
  //  <U> CompletableFuture<U> 	thenApplyAsync(Function<? super T,? extends U> fn)
  //  <U> CompletableFuture<U> 	thenApplyAsync(Function<? super T,? extends U> fn, Executor executor)

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> {
      System.out.println(Thread.currentThread().getName() + " run ! ");
//      int i = 1 / 0;
      return 100;
    })
        // thenApply方法只能用来处理正常值，因此一旦有异常就会抛出。
//        .thenApply((v) -> {
//          return v * 2;
//        })
        .handleAsync((v, e) -> {
          if (e == null) {
            return v * 2;
          } else {
            System.err.println(Thread.currentThread().getName() + " ---- " + e);
          }
          System.out.println(Thread.currentThread().getName() + " ---- " + v);
          return v;
        })
        .exceptionally(e -> {
          return 999;
        });

    System.out.println(f.get());

//    f.thenApply((v) -> {
//      return v * 2;
//    });
//    System.out.println(f.get());
  }

}
