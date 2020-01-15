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

package com.github.flysium.io.photon.juc.catalog;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import org.junit.Test;

/**
 * 为计算结果建立高效、可伸缩的高速缓存
 *
 * @author Sven Augustus
 * @version 2017年1月11日
 */
public class MemoizerDemoTest {

	@Test
	public void Test3() throws InterruptedException {
		_mytest(new Memoizer3<Integer, Integer>(computable));
	}

	@Test
	public void Test2() throws InterruptedException {
		_mytest(new Memoizer2<Integer, Integer>(computable));
	}

	@Test
	public void Test1() throws InterruptedException {
		_mytest(new Memoizer1<Integer, Integer>(computable));
	}

	private final Computable<Integer, Integer> computable = new Computable<Integer, Integer>() {

		@Override
		public Integer compute(Integer arg) {
			System.out.println(Thread.currentThread().getName() + " 进入计算-参数：" + arg);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return arg * 2;
		}
	};

	private void _mytest(final MemoizerComputable<Integer, Integer> memoizer)
			throws InterruptedException {
		final CountDownLatch countDownLatch = new CountDownLatch(1);

		int[] arg = new int[]{1, 2, 3, 1, 2, 3, 1, 2, 3, 4};
		Thread[] threads = new Thread[arg.length];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new RunThread("线程-" + String.valueOf(i), countDownLatch, memoizer, arg[i]);
			threads[i].start();
		}
		long start = System.currentTimeMillis();
		countDownLatch.countDown();
		for (int i = 0; i < threads.length; i++) {
			threads[i].join();
		}
		System.out.println("耗时：" + (System.currentTimeMillis() - start) + "ms");
	}
}

/**
 * 缓存实现3：ConcurrentHashMap + FutureTask + putIfAbsent
 *
 * 并行计算，性能不错，没有重复计算
 */
class Memoizer3<A, V> implements MemoizerComputable<A, V> {

	private final ConcurrentMap<A, RunnableFuture<V>> cache = new java.util.concurrent.ConcurrentHashMap<A, RunnableFuture<V>>();
	private final Computable<A, V> computable;

	public Memoizer3(Computable<A, V> computable) {
		this.computable = computable;
	}

	@Override
	public V compute(final A arg) {
		RunnableFuture<V> f = cache.get(arg);
		if (f == null) {
			final FutureTask<V> ft = new FutureTask<V>(new Callable<V>() {
				@Override
				public V call() throws Exception {
					return computable.compute(arg);
				}
			});
			/**
			 * putIfAbsent避免重复计算，如果已经存在计算FutureTask则跳过，等待阻塞返回计算结果
			 */
			f = cache.putIfAbsent(arg, ft);
			if (f == null) {
				f = ft;
				/**
				 * 真正计算的步骤
				 */
				/*
				 * new Thread() {
				 *
				 * @Override public void run() { ft.run(); }
				 *
				 * }.start();
				 */
				ft.run();// 与以上注释的效果一样
			}
		}
		try {
			return f.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

/**
 * 缓存实现2：ConcurrentHashMap
 *
 * 并行计算，性能不错，但是可能重复计算
 */
class Memoizer2<A, V> implements MemoizerComputable<A, V> {

	private final ConcurrentMap<A, V> cache = new java.util.concurrent.ConcurrentHashMap<A, V>();
	private final Computable<A, V> computable;

	public Memoizer2(Computable<A, V> computable) {
		this.computable = computable;
	}

	@Override
	public V compute(A arg) {
		V result = cache.get(arg);
		if (result == null) {
			result = computable.compute(arg);
			cache.put(arg, result);
		}
		return result;
	}

}

/**
 * 缓存实现1：HashMap + synchronized 同步块
 *
 * 不会重复计算，但是串行计算，性能低下
 */
class Memoizer1<A, V> implements MemoizerComputable<A, V> {

	private final Map<A, V> cache = new HashMap<A, V>();
	private final Computable<A, V> computable;

	public Memoizer1(Computable<A, V> computable) {
		this.computable = computable;
	}

	@Override
	public V compute(A arg) {
		V result = cache.get(arg);
		if (result == null) {
			synchronized (this) {
				result = cache.get(arg);
				if (result == null) {
					result = computable.compute(arg);
					cache.put(arg, result);
				}
			}
		}
		return result;
	}

}

class RunThread extends Thread {

	private final CountDownLatch countDownLatch;
	private final MemoizerComputable<Integer, Integer> memoizer;
	private final int arg;

	public RunThread(CountDownLatch countDownLatch, MemoizerComputable<Integer, Integer> memoizer,
			Integer arg) {
		super();
		this.countDownLatch = countDownLatch;
		this.memoizer = memoizer;
		this.arg = arg;
	}

	public RunThread(String name, CountDownLatch countDownLatch,
			MemoizerComputable<Integer, Integer> memoizer,
			Integer arg) {
		this(countDownLatch, memoizer, arg);
		this.setName(name);
	}

	public void run() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println(Thread.currentThread().getName() + " 获得结果:" + memoizer.compute(arg));
		}
	}

}

/**
 * 高速缓存接口
 *
 * @param <A>
 *            参数类型
 * @param <V>
 *            结果类型
 */
interface MemoizerComputable<A, V> extends Computable<A, V> {

}

/**
 * 计算接口
 *
 * @param <A>
 *            参数类型
 * @param <V>
 *            结果类型
 */
interface Computable<A, V> {

	public V compute(A arg);
}