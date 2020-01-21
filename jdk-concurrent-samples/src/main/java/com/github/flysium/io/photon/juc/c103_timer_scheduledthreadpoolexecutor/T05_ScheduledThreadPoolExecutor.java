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

package com.github.flysium.io.photon.juc.c103_timer_scheduledthreadpoolexecutor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledThreadPoolExecutor
 *
 * @author Sven Augustus
 */
public class T05_ScheduledThreadPoolExecutor {

  public static void main(String[] args) throws IOException {
    // ScheduledExecutorService executor = Executors .newSingleThreadScheduledExecutor(); // new DelegatedScheduledExecutorService(new ScheduledThreadPoolExecutor(1));
    // ScheduledExecutorService executor = Executors.newScheduledThreadPool(8); //  super(corePoolSize, Integer.MAX_VALUE,10L, MILLISECONDS, new DelayedWorkQueue());

    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2,
        // threadFactory 新建线程的工厂
        Executors.defaultThreadFactory(),
        // RejectedExecutionHandler 当提交任务数超过maxmumPoolSize + workQueue之和时，任务会交给RejectedExecutionHandler来处理
        new ThreadPoolExecutor.CallerRunsPolicy());

    executor.scheduleAtFixedRate(() -> {
      System.out.println(Thread.currentThread().getName() + " -- "
          + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }, 0, 1, TimeUnit.SECONDS);

    executor.shutdown();

    System.in.read();
  }

}
