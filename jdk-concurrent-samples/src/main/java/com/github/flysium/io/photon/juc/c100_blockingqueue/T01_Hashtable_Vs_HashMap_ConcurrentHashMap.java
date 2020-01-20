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

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiConsumer;

/**
 * Hashtable、HashMap、ConcurrentHashMap
 *
 * @author Sven Augustus
 */
public class T01_Hashtable_Vs_HashMap_ConcurrentHashMap {

  //---- Hashtable put ------347
  //---- Hashtable get ------16472
  //---- SynchronizedMap put ------434
  //---- SynchronizedMap get ------31250
  //---- ConcurrentHashMap put ------750
  //---- ConcurrentHashMap get ------1419

  static final int nThreads = 100;
  static final int SIZE = 1_000_000;
  static final int GAP = SIZE / nThreads;
  static UUID[] keys = new UUID[SIZE];
  static Long[] values = new Long[SIZE];

  static {
    // Initialize
    for (int i = 0; i < keys.length; i++) {
      keys[i] = UUID.randomUUID();
    }
    Random random = new Random();
    for (int i = 0; i < values.length; i++) {
      values[i] = (long) random.nextInt(1000000);
    }
  }

  public static void main(String[] args) throws InterruptedException {
    Hashtable<UUID, Long> hashtable = new Hashtable<UUID, Long>(11);
    Map<UUID, Long> map = new HashMap<UUID, Long>(8);
    Map<UUID, Long> synchronizedMap = Collections.synchronizedMap(new HashMap<UUID, Long>(8));
    Map<UUID, Long> concurrentHashMap = new ConcurrentHashMap<UUID, Long>(8);

    mapCount(hashtable);
    //      mapCount(map);
    mapCount(synchronizedMap);
    mapCount(concurrentHashMap);
  }

  private static void mapCount(final Map<UUID, Long> map) throws InterruptedException {
    System.out.println("---- " + map.getClass().getSimpleName() + " put ------" +
        timeTasks(nThreads, GAP, (start, end) -> {
          for (int j = start; j < end; j++) {
            map.put(keys[j], values[j]);
          }
        }));
    if (map.size() != SIZE) {
      System.err.println("---- " + map.getClass().getSimpleName() + " size ------" + map.size());
    }
    System.out.println("---- " + map.getClass().getSimpleName() + " get ------" +
        timeTasks(nThreads, GAP, (start, end) -> {
          for (int j = 0; j < SIZE; j++) {
            map.get(keys[j]);
          }
        }));
  }

  private static long timeTasks(int nThreads, int gap, BiConsumer<Integer, Integer> consumer)
      throws InterruptedException {
    CountDownLatch startSignal = new CountDownLatch(1);
    CountDownLatch doneSignal = new CountDownLatch(nThreads);

    for (int i = 0; i < nThreads; i++) {
      final int start = i * gap;
      final int end = start + gap;
      new Thread(() -> {
        try {
          startSignal.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        consumer.accept(start, end);

        doneSignal.countDown();
      }).start();
    }
    startSignal.countDown();
    long start = System.currentTimeMillis();
    doneSignal.await();
    return System.currentTimeMillis() - start;
  }

}
