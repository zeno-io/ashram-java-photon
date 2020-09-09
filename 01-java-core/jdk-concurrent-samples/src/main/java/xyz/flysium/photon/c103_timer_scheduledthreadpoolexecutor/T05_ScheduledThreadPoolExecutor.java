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

package xyz.flysium.photon.c103_timer_scheduledthreadpoolexecutor;

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
