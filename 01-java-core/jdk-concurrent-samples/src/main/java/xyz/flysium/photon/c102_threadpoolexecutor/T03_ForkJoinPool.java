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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ForkJoinPool
 *
 * @author Sven Augustus
 */
public class T03_ForkJoinPool {

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, IOException {
    MyRecursiveTask countTask = new MyRecursiveTask(1, 100);

    ForkJoinPool forkJoinPool = new ForkJoinPool(1);
    ForkJoinTask<Integer> result = forkJoinPool.submit(countTask);
    forkJoinPool.shutdown();

    System.out.println("result: " + result.get());
    System.out.println("stealCount: " + forkJoinPool.getStealCount());
//    new Thread(() -> {
//      while (!forkJoinPool.isTerminated()) {
//        Method m=null;
//        MethodHandles.lookup().unreflect(m);
//      }
//    }).start();

    System.in.read();
  }

}

class MyRecursiveTask extends RecursiveTask<Integer> {

  private Logger logger = LoggerFactory.getLogger(getClass());
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
      logger.debug(" count direct sum: " + sum);
    } else {
      // 否则，将任务进行拆分
      int mid = (end - start) / 2 + start;
      MyRecursiveTask left = new MyRecursiveTask(start, mid);
      MyRecursiveTask right = new MyRecursiveTask(mid + 1, end);

      logger.debug(" fork ->");
      // 执行上一步拆分的子任务
      left.fork();
      right.fork();

      // 拿到子任务的执行结果
      sum += left.join();
      sum += right.join();
      logger.debug("               -> count join sum: " + sum);
    }

    return sum;
  }
}
