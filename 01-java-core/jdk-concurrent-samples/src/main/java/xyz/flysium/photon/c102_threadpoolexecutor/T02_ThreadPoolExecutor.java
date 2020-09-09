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

package xyz.flysium.photon.c102_threadpoolexecutor;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * ThreadPoolExecutor
 *
 * @author Sven Augustus
 */
public class T02_ThreadPoolExecutor {

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
        // corePoolSize 核心线程数
        2,
        // maximumPoolSize 最大线程数
        4,
        // keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间；
        30,
        // TimeUnit keepAliveTime时间单位
        TimeUnit.SECONDS,
        // workQueue 阻塞任务队列
        new ArrayBlockingQueue<>(4),
        // threadFactory 新建线程的工厂
        Executors.defaultThreadFactory(),
        // RejectedExecutionHandler 当提交任务数超过 maxmumPoolSize + workQueue之和时，任务会交给RejectedExecutionHandler来处理
        new ThreadPoolExecutor.CallerRunsPolicy()
    ) {
      @Override
      protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        System.out.println(this.getQueue());
      }
    };
    for (int i = 0; i < 20; i++) {
      final int t = i;
      threadPoolExecutor.execute(new Runnable() {
        @Override
        public void run() {
          System.out.println(Thread.currentThread().getName() + " run : " + t);
          try {
            System.in.read();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

        @Override
        public String toString() {
          return "task-" + String.valueOf(t);
        }
      });
    }

    // 如果不再需要新任务，请适当关闭threadPoolExecutor并拒绝新任务
    threadPoolExecutor.shutdown();
  }

}
