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

package xyz.flysium.photon;

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
