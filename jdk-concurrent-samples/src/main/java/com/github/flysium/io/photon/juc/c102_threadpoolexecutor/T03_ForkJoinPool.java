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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinPool
 *
 * @author Sven Augustus
 */
public class T03_ForkJoinPool {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    MyRecursiveTask countTask = new MyRecursiveTask(1, 100);

    ForkJoinPool forkJoinPool = new ForkJoinPool();
    ForkJoinTask<Integer> result = forkJoinPool.submit(countTask);

    System.out.println("result: " + result.get());

    forkJoinPool.shutdown();
  }

}

class MyRecursiveTask extends RecursiveTask<Integer> {

  private final int start;
  private final int end;

  public MyRecursiveTask(int start, int end) {
    this.start = start;
    this.end = end;
  }

  @Override
  protected Integer compute() {
    int sum = 0;
    if (end - start < 6) {
      // 当任务很小时，直接进行计算
      for (int i = start; i <= end; i++) {
        sum += i;
      }
      System.out.println(Thread.currentThread().getName() + " count sum: " + sum);
    } else {
      // 否则，将任务进行拆分
      int mid = (end - start) / 2 + start;
      MyRecursiveTask left = new MyRecursiveTask(start, mid);
      MyRecursiveTask right = new MyRecursiveTask(mid + 1, end);

      // 执行上一步拆分的子任务
      left.fork();
      right.fork();

      // 拿到子任务的执行结果
      sum += left.join();
      sum += right.join();
    }

    return sum;
  }
}
