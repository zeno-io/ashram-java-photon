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
 * CompletableFuture 当任意一个CompletableFuture计算完成的时候就会执行
 *
 * @author Sven Augustus
 */
public class T04_060_CompletableFuture_xxxEither {

  // 当任意一个CompletableFuture计算完成的时候就会执行：

  //  acceptEither方法是当任意一个CompletionStage完成的时候，action这个消费者就会被执行。这个方法返回CompletableFuture<Void>
  //  CompletableFuture<Void> 	acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action)
  //  CompletableFuture<Void> 	acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action)
  //  CompletableFuture<Void> 	acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor)

  // applyToEither方法是当任意一个CompletionStage完成的时候，fn会被执行，它的返回值会当作新的CompletableFuture<U>的计算结果。
  //  <U> CompletableFuture<U> 	applyToEither(CompletionStage<? extends T> other, Function<? super T,U> fn)
  //  <U> CompletableFuture<U> 	applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T,U> fn)
  //  <U> CompletableFuture<U> 	applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T,U> fn, Executor executor)

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
      millSleep(500);
      return 100;
    });
    CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
      millSleep(300);
      return 10;
    });

    CompletableFuture<Void> f = f1.acceptEither(f2, (v) -> System.out.println("-------" + v));
    System.out.println(f.get());

    CompletableFuture<String> u = f1.applyToEither(f2, (x) -> "-" + x);
    System.out.println(u.get());
  }

  private static void millSleep(long timeout) {
    try {
      TimeUnit.MILLISECONDS.sleep(timeout);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
