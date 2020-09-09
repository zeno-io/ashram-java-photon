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
public class T04_012_CompletableFuture_complete {

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    CompletableFuture<Integer> f = new CompletableFuture<>();

    new Thread(() -> {
      //可以通过下面的代码完成一个计算，触发客户端的等待：
      f.complete(100);

      //当然你也可以抛出一个异常，而不是一个成功的计算结果：
      f.completeExceptionally(new Exception());
    }).start();

    //上面的代码中future没有关联任何的Callback、线程池、异步任务等，如果客户端调用future.get就会一致傻等下去。
    System.out.println(f.get());
  }

}
