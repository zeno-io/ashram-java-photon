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

package xyz.flysium.photon.c001_thread;

/**
 * 线程状态 NEW > RUNNABLE (BLOCKED  \ WAITING \ TIMED_WAITING)  > TERMINATED
 *
 * @author Sven Augustus
 */
public class T09_ThreadState {

  static class MyThread extends Thread {

    private final Object lockObject;
    private final boolean flag;

    public MyThread(Object lockObject, boolean flag) {
      super();
      this.lockObject = lockObject;
      this.flag = flag;
      this.setName(flag ? "线程t1" : "线程t2");
      System.out.println(
          Thread.currentThread().getName() + " " + this.getName() + " 构造方法状态：" + this
              .getState());
    }

    @Override
    public void run() {
      synchronized (lockObject) {
        System.out.println(Thread.currentThread().getName()
            + " run()方法状态：" + Thread.currentThread().getState());
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (flag) {
          try {
            lockObject.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        } else {
          lockObject.notify();
        }
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    Object lockObject = new Object();

    MyThread t1 = new MyThread(lockObject, true);
    System.out.println(Thread.currentThread().getName() + " 线程t1 start()前状态：" + t1.getState());
    t1.start();
    Thread.sleep(100);
    MyThread t2 = new MyThread(lockObject, false);
    System.out.println(Thread.currentThread().getName() + " 线程t2 start()前状态：" + t1.getState());
    t2.start();
    Thread.sleep(1000);
    System.out.println(Thread.currentThread().getName() + " 线程t1 运行时状态：" + t1.getState());
    System.out.println(Thread.currentThread().getName() + " 线程t2 运行时状态：" + t2.getState());
    Thread.sleep(4000);
    System.out.println(Thread.currentThread().getName() + " 线程t1 运行后状态：" + t1.getState());
    System.out.println(Thread.currentThread().getName() + " 线程t2 运行后状态：" + t1.getState());
    t2.join();
    t1.join();
    System.out.println(Thread.currentThread().getName() + " 线程t1 运行后状态：" + t1.getState());
    System.out.println(Thread.currentThread().getName() + " 线程t2 运行后状态：" + t1.getState());
  }

}
