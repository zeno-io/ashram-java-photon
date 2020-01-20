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
import java.util.concurrent.PriorityBlockingQueue;

/**
 * PriorityBlockingQueue 基于优先级的无界阻塞队列
 *
 * @author Sven Augustus
 */
public class T07_PriorityBlockingQueue {

  // PriorityBlockingQueue 内部维护的是 PriorityQueue
  // PriorityBlockingQueue 不阻塞生产，但是阻塞消费。因此需要注意控制生产的速度不能比消费的速度快，否则时间一长，内存空间会被堆满
  // 内部锁为 ReentrantLock , 非公平锁模式

  public static void main(String[] args) throws InterruptedException {
    BlockingQueue<String> blockingQueue = new PriorityBlockingQueue<>();
//  BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>(10);

    blockingQueue.put("b");
    blockingQueue.put("a");
    blockingQueue.put("d");
    blockingQueue.put("c");
    blockingQueue.put("e");

    // poll \ take 都是没问题的。区别只在于 如果为空的时候，是否阻塞，take是会阻塞等待的，poll 是直接返回null
//    String s = null;
//    while ((s = blockingQueue.poll()) != null) {
//      System.out.println(s);
//    }
    for (int i = 0; i < 5; i++) {
      String m = blockingQueue.take();
      System.out.println(m);
    }

  }


}
