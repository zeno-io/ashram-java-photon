/*
 * Apache License 2.0
 *
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.juc.c001_thread;

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
