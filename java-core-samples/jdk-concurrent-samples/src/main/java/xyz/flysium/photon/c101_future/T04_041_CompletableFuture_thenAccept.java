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
 * CompletableFuture 计算结果完成时的消费 (只执行，不返回新的计算)
 *
 * @author Sven Augustus
 */
public class T04_041_CompletableFuture_thenAccept {

  // 计算结果完成时的消费 (只执行，不返回新的计算)：
  //  CompletableFuture<Void> 	thenAccept(Consumer<? super T> action)
  //  CompletableFuture<Void> 	thenAcceptAsync(Consumer<? super T> action)
  //  CompletableFuture<Void> 	thenAcceptAsync(Consumer<? super T> action, Executor executor)

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    CompletableFuture<Void> f = CompletableFuture.supplyAsync(() -> {
      System.out.println(Thread.currentThread().getName() + " run ! ");
//      int i = 1 / 0;
      return 100;
    })
//        .exceptionally(e -> {
//          System.err.println(Thread.currentThread().getName() + " ---- " + e);
//          return 999;
//        })
        // thenApply方法只能用来处理正常值，但是有异常会吞掉。
        .thenAccept((v) -> {
          System.out.println(Thread.currentThread().getName() + " ---- " + v);
        });

    System.out.println(f.get());
  }

}
