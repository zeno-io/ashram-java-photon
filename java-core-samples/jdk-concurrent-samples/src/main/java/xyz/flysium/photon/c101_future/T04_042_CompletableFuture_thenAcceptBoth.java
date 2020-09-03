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
import java.util.concurrent.TimeoutException;

/**
 * CompletableFuture
 *
 * @author Sven Augustus
 */
public class T04_042_CompletableFuture_thenAcceptBoth {

  // thenAcceptBoth以及相关方法提供了类似的功能，当两个CompletionStage都正常完成计算的时候，就会执行提供的action，它用来组合另外一个异步的结果。
  //  <U> CompletableFuture<Void> 	thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T,? super U> action)
  //  <U> CompletableFuture<Void> 	thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T,? super U> action)
  //  <U> CompletableFuture<Void> 	thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T,? super U> action, Executor executor)

  //  runAfterBoth是当两个CompletionStage都正常完成计算的时候,执行一个Runnable，这个Runnable并不使用计算的结果。
  //      CompletableFuture<Void> 	runAfterBoth(CompletionStage<?> other,  Runnable action)

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
      return 100;
    });
    CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
      return 10;
    });
//     CompletableFuture<Integer> f2 =CompletableFuture.completedFuture(10);

//    CompletableFuture<Void> f = f1.thenAcceptBoth(f2, (x, y) -> System.out.println(x * y));
//    System.out.println(f.get());

    CompletableFuture<Void> f = f1.runAfterBoth(f2, () -> {
      System.out.println("结束！");
    });
    System.out.println(f.get());
  }

}
