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
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueue 延迟队列
 *
 * @author Sven Augustus
 */
public class T08_DelayQueue {

  // DelayQueue 内部维护的是 PriorityQueue，列头的元素是最先到期的元素，若无元素到期，即使队列有元素，也无法从列头获取元素
  // 放入队列的元素需要实现 Delayed 接口，Delayed 接口继承了 Comparable 接口
  // DelayQueue不阻塞生产，但是阻塞消费。因此需要注意控制生产的速度不能比消费的速度快，否则时间一长，内存空间会被堆满
  // 内部锁为 ReentrantLock , 非公平锁模式
  // 使用场景较少，但很巧妙，比如可以使用DelayQueue管理一个超时未响应的连接队列

  public static void main(String[] args) throws InterruptedException {
    BlockingQueue<M> blockingQueue = new DelayQueue<M>();

    long now = System.currentTimeMillis();
    blockingQueue.put(new M("a", now + 1000));
    blockingQueue.put(new M("b", now + 3000));
    blockingQueue.put(new M("c", now + 500));
    blockingQueue.put(new M("d", now + 3000));
    blockingQueue.put(new M("e", now + 1500));

    System.out.println(blockingQueue);

    for (int i = 0; i < 5; i++) {
      // poll() 可能取出来都是null. 因为 DelayQueue 里面的元素只有时间到了才能取出
//      M m = blockingQueue.poll();
      M m = blockingQueue.take();
      System.out.println(m);
    }
  }

}

class M implements Delayed {

  final String name;
  final long runningTime;

  M(String name, long runningTime) {
    this.name = name;
    this.runningTime = runningTime;
  }

  @Override
  public long getDelay(TimeUnit unit) {
    return unit.convert(this.runningTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
  }

  @Override
  public int compareTo(Delayed o) {
    return (int) (getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
  }

  @Override
  public String toString() {
    return "M{" +
        "name='" + name + '\'' +
        ", runningTime=" + runningTime +
        '}';
  }
}
