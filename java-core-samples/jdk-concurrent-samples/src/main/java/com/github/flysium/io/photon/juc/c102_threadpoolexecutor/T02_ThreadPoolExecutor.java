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

package com.github.flysium.io.photon.juc.c102_threadpoolexecutor;

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
