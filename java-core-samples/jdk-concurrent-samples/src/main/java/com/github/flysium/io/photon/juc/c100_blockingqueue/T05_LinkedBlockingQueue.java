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

package com.github.flysium.io.photon.juc.c100_blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * LinkedBlockingQueue 无界阻塞队列
 *
 * @author Sven Augustus
 */
public class T05_LinkedBlockingQueue {

  // LinkedBlockingQueue 内部维护一个链表，两个指针 head, last
  // 默认容量可以不指定，不指定则为无界阻塞队列（长度为Integer.MAX_VALUE)）
  // 生产和消费采用不同的锁对象 takeLock putLock，非公平锁模式

  public static void main(String[] args) {
    BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(10);
//  BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>();

    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        for (int j = 0; j < 2; j++) {
          Object o = null;
          try {
            o = blockingQueue.take();
//            o = blockingQueue.poll(1, TimeUnit.SECONDS);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println(Thread.currentThread().getName() + " 消费: " + o);
        }
      }, "c" + i).start();
    }
    for (int i = 0; i < 2; i++) {
      new Thread(() -> {
        for (int j = 0; j < 10; j++) {
          try {
            String e = Thread.currentThread().getName() + " " + j;
            blockingQueue.put(e);
//            blockingQueue.offer(e, 1, TimeUnit.SECONDS);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }, "p" + i).start();
    }
  }


}
