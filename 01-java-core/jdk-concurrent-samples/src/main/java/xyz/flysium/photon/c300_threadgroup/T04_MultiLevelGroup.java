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
 * 线程组多级关联
 *
 * @author Sven Augustus
 */
public class T04_MultiLevelGroup {

  public static void main(String[] args) throws InterruptedException {
    ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
    ThreadGroup group = new ThreadGroup(mainGroup, "组A");

    new Thread(group, (Runnable) () -> {
      System.out.println("run");
      // 线程必须在运行状态才可以受组管理
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, "线程Z").start();

    System.out.println("main线程组的子线程组个数：" + mainGroup.activeGroupCount() + ",线程数：" + mainGroup
        .activeCount());
    ThreadGroup[] listGroup = new ThreadGroup[Thread.currentThread().getThreadGroup()
        .activeGroupCount()];
    Thread.currentThread().getThreadGroup().enumerate(listGroup);
    System.out
        .println("main线程中有多少个子线程组：" + listGroup.length + " 名字为：" + listGroup[0].getName());
    Thread[] listThread = new Thread[listGroup[0].activeCount()];
    listGroup[0].enumerate(listThread);
    System.out.println("子线程组的线程：" + listThread[0].getName());
  }

}
