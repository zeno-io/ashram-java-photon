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

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ArrayBlockingQueue 有界阻塞队列
 *
 * @author Sven Augustus
 */
public class T04_ArrayBlockingQueue {

  // ArrayBlockingQueue 内部维护一个定长的数组，两个整数 putIndex, takeIndex
  // 默认容量必须指定，为有界阻塞队列
  // 生产和消费都是同用一个锁对象，默认非公平锁（可指定公平策略），只是采用两个条件变量

  public static void main(String[] args) {
    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(10);
//    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(10, true);

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
