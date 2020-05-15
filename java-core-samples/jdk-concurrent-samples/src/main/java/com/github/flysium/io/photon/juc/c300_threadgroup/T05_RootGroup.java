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

import java.util.LinkedList;

/**
 * 获取根线程组
 *
 * @author Sven Augustus
 */
public class T05_RootGroup {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("线程：" + Thread.currentThread().getName() + " 所在的线程组名为："
				+ Thread.currentThread().getThreadGroup().getName());
		LinkedList<ThreadGroup> parentList = new LinkedList<ThreadGroup>();
		ThreadGroup root = Thread.currentThread().getThreadGroup();
		ThreadGroup endless = root;
		parentList.add(endless);

		while (endless != null) {
			root = endless;
			endless = endless.getParent();
			parentList.add(endless);
		}
		for (int i = 0; i < parentList.size(); i++) {
			ThreadGroup p = parentList.get(i);
			if (p != null) {
				System.out.println("线程组level=" + i + "，名称为：" + p.getName());
			}
		}
		System.out.println("根线程组的名称是：" + root.getName());
	}

}
