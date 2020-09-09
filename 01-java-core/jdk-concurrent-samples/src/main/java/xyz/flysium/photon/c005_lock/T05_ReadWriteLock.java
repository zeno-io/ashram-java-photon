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

package xyz.flysium.photon.c005_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * ReentrantReadWriteLock是对ReentrantLock 更进一步的扩展，实现了读锁readLock()（共享锁）和写锁writeLock()（独占锁），实现读写分离。
 *
 * @author Sven Augustus
 */
public class T05_ReadWriteLock {

  // 读和读之间不会互斥，读和写、写和读、写和写之间才会互斥，提升了读写的性能。

  static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  static ReadLock readLock = lock.readLock();
  static WriteLock writeLock = lock.writeLock();

  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        readLock.lock();
        //获取到读锁readLock，同步块
        try {
          TimeUnit.SECONDS.sleep(1);
          System.out.println("read finish !");
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          //释放读锁readLock
          readLock.unlock();
        }
      }, "r" + i).start();
    }
    for (int i = 0; i < 2; i++) {
      new Thread(() -> {
        writeLock.lock();
        //获取到写锁writeLock，同步块
        try {
          TimeUnit.SECONDS.sleep(1);
          System.out.println("write finish !");
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          //释放写锁writeLock
          writeLock.unlock();
        }
      }, "w" + i).start();
    }
  }

}
