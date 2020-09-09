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

/**
 * 线程组的自动归属测试
 * <p>
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
