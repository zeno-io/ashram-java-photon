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

package xyz.flysium.photon.c100_blockingqueue;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;

/**
 * Vector、List、Queue
 *
 * @author Sven Augustus
 */
public class T02_Vector_Vs_List_Queue {

  //------- Vector ------ 798
  //------- LinkedList ------ 93
  //------- ConcurrentLinkedQueue ------ 88

  static final int nThreads = 10;
  static final int SIZE = 100_000;

  public static void main(String[] args) throws InterruptedException {
    Vector<String> vector = new Vector<>(SIZE);
    List<String> list = new LinkedList<String>();
    Queue<String> queue = new ConcurrentLinkedQueue<String>();
    for (int i = 0; i < SIZE; i++) {
      vector.add("票" + i);
    }
    for (int i = 0; i < SIZE; i++) {
      list.add("票" + i);
    }
    for (int i = 0; i < SIZE; i++) {
      queue.add("票" + i);
    }

    listCount(vector);
    listCount(list);
    queueCount(queue);
  }

  private static void listCount(List<String> list) throws InterruptedException {
    count(list.getClass().getSimpleName(), (sold) -> {
      return timeTasks(() -> {
        while (true) {
          synchronized (list) {
            if (list.size() <= 0) {
              break;
            }
            String ticket = list.remove(0);
            if (!sold.add(ticket)) {
              System.err.println(ticket);
            }
          }
        }
      });
    });
  }

  private static void queueCount(Queue<String> queue) throws InterruptedException {
    count(queue.getClass().getSimpleName(), (sold) -> {
      return timeTasks(() -> {
        while (true) {
          String ticket = queue.poll();
          if (ticket == null) {
            break;
          }
          if (!sold.add(ticket)) {
            System.err.println(ticket);
          }
        }
      });
    });
  }

  private static void count(String desc, Function<Set<String>, Long> function)
      throws InterruptedException {
    Set<String> sold = new ConcurrentSkipListSet<String>();
    long timeSpend = function.apply(sold);
    System.out.println("------- " + desc + " ------ " + timeSpend);
    if (sold.size() != SIZE) {
      System.err.println("------- " + desc + " ------ 已出售数目有误：" + sold.size());
    }
  }

  private static long timeTasks(Runnable runnable) {
    CountDownLatch startSignal = new CountDownLatch(1);
    CountDownLatch doneSignal = new CountDownLatch(nThreads);
    for (int i = 0; i < nThreads; i++) {
      new Thread(() -> {
        try {
          startSignal.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        try {
          runnable.run();
        } finally {
          doneSignal.countDown();
        }
      }).start();
    }
    startSignal.countDown();
    long start = System.currentTimeMillis();
    try {
      doneSignal.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return System.currentTimeMillis() - start;
  }

}
