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

package xyz.flysium.photon.c021_Exercises_pc;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock + condition
 *
 * @author Sven Augustus
 */
public class T02_lock_condition {

  static class MyContainer {

    /*volatile*/ LinkedList list = new LinkedList();
    private final int max;

    static Lock lock = new ReentrantLock();
    static Condition notFull = lock.newCondition();
    static Condition notEmpty = lock.newCondition();

    public MyContainer(int max) {
      this.max = max;
    }

    public void put(Object o) {
      lock.lock();
      try {
        while (list.size() == max) {
          try {
            notFull.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        list.addLast(o);
        System.out.println("生产：" + o);
        // 通知消费者线程
        notEmpty.signalAll();
      } finally {
        lock.unlock();
      }
    }

    public Object get() {
      lock.lock();
      try {
        while (list.size() == 0) {
          try {
            notEmpty.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        Object t = list.removeFirst();
        // 通知生产者线程
        notFull.signalAll();
        return t;
      } finally {
        lock.unlock();
      }
    }

    public /*synchronized*/ int getCount() {
      return list.size();
    }
  }

  /*volatile*/ static MyContainer container = new MyContainer(10);

  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        for (int j = 0; j < 2; j++) {
          Object o = container.get();
          System.out.println(Thread.currentThread().getName() + " 消费: " + o);
        }
      }, "c" + i).start();
    }
    for (int i = 0; i < 2; i++) {
      new Thread(() -> {
        for (int j = 0; j < 10; j++) {
          container.put(Thread.currentThread().getName() + " " + j);
        }
      }, "p" + i).start();
    }
  }

}
