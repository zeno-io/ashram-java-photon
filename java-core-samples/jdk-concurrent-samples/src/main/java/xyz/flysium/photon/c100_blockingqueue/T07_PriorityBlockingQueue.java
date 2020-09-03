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
