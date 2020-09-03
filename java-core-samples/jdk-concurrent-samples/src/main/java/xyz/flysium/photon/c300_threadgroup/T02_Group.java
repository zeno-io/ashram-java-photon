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
 * 线程组测试
 *
 * @author Sven Augustus
 */
public class T02_Group {

  public static void main(String[] args) throws InterruptedException {
    Runnable r = () -> {
      try {
        while (!Thread.currentThread().isInterrupted()) {
          System.out.println("ThreadName=" + Thread.currentThread().getName());
          Thread.sleep(3000);
        }
      } catch (InterruptedException e) {
        // e.printStackTrace();
      }
    };
    ThreadGroup group = new ThreadGroup("sven's thread group");
    Thread t1 = new Thread(group, r, "t1");
    Thread t2 = new Thread(group, r, "t2");
    t1.start();
    t2.start();
    Thread.sleep(1000);
    System.out.println("线程组的活动线程数：" + group.activeCount());
    System.out.println("线程组的名称：" + group.getName());
    Thread.sleep(10000);
    t1.interrupt();
    t2.interrupt();
  }

}
