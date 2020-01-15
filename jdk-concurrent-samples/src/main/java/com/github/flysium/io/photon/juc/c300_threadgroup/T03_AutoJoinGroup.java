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

package com.github.flysium.io.photon.juc.c300_threadgroup;

/**
 * 线程组的自动归属测试
 *
 * 如果实例化了一个线程组x，如果不指定归属的父线程组，则线程组x会自动归属为当前线程的线程组， 也就是隐式地在当前线程的线程组中添加一个子线程组x
 *
 * @author Sven Augustus
 */
public class T03_AutoJoinGroup {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("A处线程：" + Thread.currentThread().getName() + " 中有线程组数量："
				+ Thread.currentThread().getThreadGroup().activeGroupCount());
		ThreadGroup group = new ThreadGroup("新的组");
		System.out.println("A处线程：" + Thread.currentThread().getName() + " 中有线程组数量："
				+ Thread.currentThread().getThreadGroup().activeGroupCount());
		ThreadGroup[] threadGroup = new ThreadGroup[Thread.currentThread().getThreadGroup()
				.activeGroupCount()];
		Thread.currentThread().getThreadGroup().enumerate(threadGroup);
		for (int i = 0; i < threadGroup.length; i++) {
			System.out.println("第一个线程组名称为：" + threadGroup[i].getName());
		}
	}

}
