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
