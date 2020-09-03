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

package xyz.flysium.photon.c300_threadgroup;

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
