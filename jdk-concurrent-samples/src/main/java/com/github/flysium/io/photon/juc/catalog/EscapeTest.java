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

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * 逸出
 *
 * @author Sven Augustus
 * @version 2016年11月28日
 */
public class EscapeTest {

	/** 监听器，模拟事件 */
	interface EventListener {

		void onEvent(Object event);
	}

	/** 某被监视对象 */
	class EventSource<T> {

		private final List<T> eventListeners;

		public EventSource() {
			eventListeners = new ArrayList<T>();
		}

		/** 注册监听器 */
		public synchronized void registerListener(T eventListener) {
			this.eventListeners.add(eventListener);
			/** 唤醒监视本对象的所有线程 */
			this.notifyAll();
		}

		public synchronized List<T> retrieveListeners() throws InterruptedException {
			List<T> dest = null;
			/** 如果监听器为空，所有线程等待被唤醒 */
			if (eventListeners.size() <= 0) {
				this.wait();
			}
			dest = new ArrayList<T>(eventListeners.size());
			dest.addAll(eventListeners);
			return dest;
		}
	}

	class ListenerRunnable implements Runnable {

		private EventSource<EventListener> source;

		public ListenerRunnable(EventSource<EventListener> source) {
			this.source = source;
		}

		public void run() {
			List<EventListener> listeners = null;
			try {
				listeners = this.source.retrieveListeners();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (EventListener listener : listeners) {
				listener.onEvent(new Object());
			}
		}

	}

	/** 逸出 */
	private class ThisEscape {

		public final int id;
		public final String name;

		public ThisEscape(EventSource<EventListener> source) {
			id = 1;
			source.registerListener(new EventListener() {

				public void onEvent(Object event) {
					System.out.println("id: " + ThisEscape.this.id);
					System.out.println("name: " + ThisEscape.this.name);
				}
			});
			/** 调用sleep模拟其他耗时的初始化操作 */
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			name = "flysqrlboy";
		}
	}

	/** 安全构建 */
	private static class ThisSafe {

		public final int id;
		public final String name;
		public final EventListener l;

		private ThisSafe() {
			id = 1;
			l = new EventListener() {

				public void onEvent(Object event) {
					System.out.println("id: " + ThisSafe.this.id);
					System.out.println("name: " + ThisSafe.this.name);
				}
			};
			/** 调用sleep模拟其他耗时的初始化操作 */
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			name = "flysqrlboy";
		}

		public static ThisSafe getInstance(EventSource<EventListener> source) {
			ThisSafe safe = new ThisSafe();
			source.registerListener(safe.l);
			return safe;
		}
	}

	@SuppressWarnings("unused")
	@Test
	public void test() {
		EventSource<EventListener> source = new EventSource<EventListener>();
		ListenerRunnable listRun = new ListenerRunnable(source);
		Thread thread = new Thread(listRun);
		thread.start();
		ThisEscape escape1 = new ThisEscape(source);
	}

	@Test
	public void test2() {
		EventSource<EventListener> source = new EventSource<EventListener>();
		ListenerRunnable listRun = new ListenerRunnable(source);
		Thread thread = new Thread(listRun);
		thread.start();
		ThisSafe.getInstance(source);
	}

}
