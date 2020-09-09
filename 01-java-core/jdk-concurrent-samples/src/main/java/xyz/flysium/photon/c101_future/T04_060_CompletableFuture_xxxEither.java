/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon.c101_future;

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
