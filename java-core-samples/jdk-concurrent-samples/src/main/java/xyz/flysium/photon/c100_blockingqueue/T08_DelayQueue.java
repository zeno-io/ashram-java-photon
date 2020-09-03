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

package xyz.flysium.photon.c100_blockingqueue;

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
