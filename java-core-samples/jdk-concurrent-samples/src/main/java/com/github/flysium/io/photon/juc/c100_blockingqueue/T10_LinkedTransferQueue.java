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

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * LinkedTransferQueue 无缓冲的等待队列
 *
 * @author Sven Augustus
 */
public class T10_LinkedTransferQueue {

  // LinkedTransferQueue 实际上是 ConcurrentLinkedQueue、SynchronousQueue（公平模式）和LinkedBlockingQueue的超集
  // LinkedTransferQueue 性能比 LinkedBlockingQueue 更高（没有锁操作），比 SynchronousQueue能存储更多的元素。

  public static void main(String[] args) throws InterruptedException {
    TransferQueue<String> transferQueue = new LinkedTransferQueue<>();

    new Thread(() -> {
      // while (true) {
      try {
        System.out.println(transferQueue.take());
        // TimeUnit.MILLISECONDS.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      // }
    }).start();

    // 使用 transfer ，你可以认为如  SynchronousQueue 一样
    transferQueue.transfer("a");
    // transferQueue.put("ab");

/*    new Thread(() -> {
      try {
        System.out.println(transferQueue.take());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();*/
  }

}
