/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.collection;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * HashMap测试
 *
 * @author Sven Augustus
 * @version 2016年12月6日
 */
public class T02_HashMapHashConfilctTest {

  /**
   * HashMap的负载因子loadFactor
   * <p>
   * 在容量极限(threshold）一定时，负载因子越大，则实际容量（capacity）越小，即所需初始容量（initialCapacity）越小，则初始构造的hash表所占空间越小。
   * <p>
   * 增大负载因子会减少hash表的内存空间，增加查询数据的时间开销，肯定是多产生了Entry链。在发生"hash冲突"情况下（即hashCode相同 equals不同），会产生Entry链，
   * 当要查找时，遍历Entry链花的时间多。
   * <p>
   * <p>
   * 以下是简单模拟hash冲突的情况下的耗时
   */
  public static void main(String[] args) throws InterruptedException {
    final int initialCapacity = 20000;
    float loadFactor = 0.15f;
    boolean testHashConfilct = true;

    if (args.length > 0) {
      loadFactor = Float.parseFloat(args[0]);
    }
    if (args.length > 1) {
      testHashConfilct = Boolean.parseBoolean(args[1]);
    }
    float finalLoadFactor = loadFactor;
    Thread t = new Thread(testHashConfilct ? () -> {
      HashMap<Object, Object> map = new HashMap<Object, Object>(16, finalLoadFactor);
      List<String> keys = new ArrayList<>(initialCapacity);
      for (int i = 0; i < 2; i++) {
        for (int j = 0; j < initialCapacity; j++) {
          keys.add(randomSameHash(j));
        }
      }
      for (int i = 0; i < initialCapacity; i++) {
        map.put(keys.get(i), i);
      }
      for (int i = 0; i < initialCapacity; i++) {
        map.put(keys.get(initialCapacity + i), i);
      }
      long start = System.currentTimeMillis();
      for (int i = 0; i < initialCapacity; i++) {
        map.get(keys.get(initialCapacity + i));
      }
      long end = System.currentTimeMillis();
      System.out.println("map（loadFactor=" + String.valueOf(finalLoadFactor) + ",size="
          + map.size() + "）耗时：" + (end - start) + "ms");
    } :
        () -> {
          HashMap<Object, Object> map = new HashMap<Object, Object>(16, finalLoadFactor);
          for (int i = 0; i < initialCapacity; i++) {
            map.put(i + "", i);
          }
          for (int i = 0; i < initialCapacity; i++) {
            map.put(i + "", i);
          }
          long start = System.currentTimeMillis();
          for (int i = 0; i < initialCapacity; i++) {
            map.get(i + "");
          }
          long end = System.currentTimeMillis();
          System.out.println("map（loadFactor=" + String.valueOf(finalLoadFactor) + ",size="
              + map.size() + "）耗时：" + (end - start) + "ms");
        });
    t.start();
    t.join();

    MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
    MemoryUsage usage = memorymbean.getHeapMemoryUsage();
    System.out.println("INIT HEAP: " +
        String.format("%.2f", (usage.getInit() / 1024.0 / 1024.0)) + " MB");
    System.out.println("MAX HEAP: " +
        String.format("%.2f", (usage.getMax() / 1024.0 / 1024.0)) + " MB");
    System.out.println("USE HEAP: " +
        String.format("%.2f", (usage.getUsed() / 1024.0 / 1024.0)) + " MB");
    System.out.println("Full Information:");
    System.out.println("Heap Memory Usage: " +
        memorymbean.getHeapMemoryUsage());
    System.out.println("Non-Heap Memory Usage: " +
        memorymbean.getNonHeapMemoryUsage());
  }

  private static final String[] SAME_HASH_CODE = new String[]{"Aa", "BB"};
  private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

  static String randomSameHash(int times) {
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < times; i++) {
      buf.append(SAME_HASH_CODE[RANDOM.nextInt(SAME_HASH_CODE.length)]);
    }
    return buf.toString();
  }

}
