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
