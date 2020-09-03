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
 * CompletableFuture 管理多个Future的结果
 *
 * @author Sven Augustus
 */
public class T04_011_CompletableFuture_join_get {

  // 像 Future 一样通过阻塞或者轮询的方式获得结果，尽管这种方式不推荐使用。

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
      System.out.println(Thread.currentThread().getName() + " call !");
      return 100;
    });
    System.out.println("main");
//    System.out.println( future.join());
    System.out.println(future.get());

    try {
      CompletableFuture<Integer> exceptionFuture = CompletableFuture.supplyAsync(() -> {
        int i = 1 / 0;
        return 100;
      });
//exceptionFuture.join();
      exceptionFuture.get();
    } catch (RuntimeException e) {
      e.printStackTrace();
    }

  }

}
