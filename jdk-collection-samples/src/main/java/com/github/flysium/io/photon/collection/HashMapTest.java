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

package com.github.flysium.io.photon.collection;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.HashMap;

/**
 * HashMap测试
 *
 * @author Sven Augustus
 * @version 2016年12月6日
 */
public class HashMapTest {

	/**
	 * HashMap的负载因子loadFactor
	 *
	 * 在容量极限(threshold）一定时，负载因子越大，则实际容量（capacity）越小，
	 * 即所需初始容量（initialCapacity）越小，则初始构造的hash表所占空间越小。
	 *
	 * 增大负载因子会减少hash表的内存空间，增加查询数据的时间开销，肯定是多产生了Entry链。 在发生"hash冲突"情况下（即hashCode相同
	 * equals不同），会产生Entry链， 当要查找时，遍历Entry链花的时间多。
	 *
	 *
	 * 以下是简单模拟hash冲突的情况下的耗时
	 */

	public static void main(String[] args) {
		final int initialCapacity = 100000;
		Thread t1 = new Thread(new MyRunnable(initialCapacity, .95f));
		Thread t2 = new Thread(new MyRunnable(initialCapacity, .15f));
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
		MemoryUsage usage = memorymbean.getHeapMemoryUsage();
		System.out.println(
				"INIT HEAP: " + String.format("%.2f", (usage.getInit() / 1024.0 / 1024.0)) + " MB");
		System.out.println(
				"MAX HEAP: " + String.format("%.2f", (usage.getMax() / 1024.0 / 1024.0)) + " MB");
		System.out.println(
				"USE HEAP: " + String.format("%.2f", (usage.getUsed() / 1024.0 / 1024.0)) + " MB");
		System.out.println("Full Information:");
		System.out.println("Heap Memory Usage: " + memorymbean.getHeapMemoryUsage());
		System.out.println("Non-Heap Memory Usage: " + memorymbean.getNonHeapMemoryUsage());
	}

	static class MyRunnable implements Runnable {

		private final int initialCapacity;
		private final float loadFactor;

		public MyRunnable(int initialCapacity, float loadFactor) {
			super();
			this.initialCapacity = initialCapacity;
			this.loadFactor = loadFactor;
		}

		@Override
		public void run() {
			HashMap<Object, Object> map = new HashMap<Object, Object>(16, loadFactor);
			/**
			 * 制造hash冲突场景的方法仍然有些问题，以后有待改善
			 */
			for (int i = 0; i < initialCapacity; i++) {
				map.put(String.valueOf(i), i);
			}
			for (int i = 0; i < initialCapacity; i++) {
				map.put(i, i);
			}
			long start = System.currentTimeMillis();
			for (int i = 0; i < initialCapacity; i++) {
				map.get(String.valueOf(i));
			}
			;
			long end = System.currentTimeMillis();
			System.out.println(
					"map（loadFactor=" + String.valueOf(loadFactor) + "）耗时：" + (end - start) + "ms");
		}

	}
}
