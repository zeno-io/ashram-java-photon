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

package com.github.flysium.io.photon.juc.c005_lock;

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
