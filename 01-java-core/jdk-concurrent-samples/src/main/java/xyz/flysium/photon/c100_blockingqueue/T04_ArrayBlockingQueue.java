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
