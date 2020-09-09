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
