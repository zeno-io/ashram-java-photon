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
import java.util.concurrent.SynchronousQueue;

/**
 * SynchronousQueue 无缓冲的等待队列
 *
 * @author Sven Augustus
 */
public class T09_SynchronousQueue {

  // SynchronousQueue 是无缓冲的等待队列
  // SynchronousQueue 实际上不是一个真正的队列，因为它并不能存储元素，因此 put 和 take 会一直阻塞当前线程，每一个插入操作都必须等待另一个线程的删除操作

  public static void main(String[] args) throws InterruptedException {
    BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
//    BlockingQueue<String> blockingQueue = new SynchronousQueue<>(true);

    new Thread(() -> {
      // while (true) {
      try {
        System.out.println(blockingQueue.take());
        // TimeUnit.MILLISECONDS.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      // }
    }).start();

    // SynchronousQueue 是无缓冲的等待队列， 因此put 会一直阻塞，等待消费者取走才会结束
    blockingQueue.put("a");
    // blockingQueue.put("ab");
  }

}
