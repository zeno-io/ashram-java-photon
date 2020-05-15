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

package com.github.flysium.io.photon.juc.c100_blockingqueue;

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
