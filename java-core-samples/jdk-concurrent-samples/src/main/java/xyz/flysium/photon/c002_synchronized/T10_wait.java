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

package xyz.flysium.photon.c002_synchronized;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * wait and notify
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T10_wait {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final Object o = new Object();

  public void x() {
    synchronized (o) {
      logger.debug(Thread.currentThread().getName()
          + ", time=" + System.nanoTime() + " -->  enter x~");
      try {
        o.wait();
        logger.debug(Thread.currentThread().getName()
            + ", time=" + System.nanoTime() + " wait~");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      logger.debug(Thread.currentThread().getName()
          + ", time=" + System.nanoTime() + " -->  get lock again~");
    }
    logger.debug(Thread.currentThread().getName()
        + ", time=" + System.nanoTime() + " exit  x~");
  }

  public void y() {
    synchronized (o) {
      logger.debug(Thread.currentThread().getName()
          + ", time=" + System.nanoTime() + " -->  enter y~");
      logger.debug(Thread.currentThread().getName()
          + ", time=" + System.nanoTime() + " gogogo~");
    }
    logger.debug(Thread.currentThread().getName()
        + ", time=" + System.nanoTime() + " exit  y~");
  }

  public void notifyThread() {
    synchronized (o) {
      logger.debug("->notifyThread " + Thread.currentThread().getName()
          + ", time=" + System.nanoTime() + " -->  enter y~");
      o.notify();
      logger.debug("->notifyThread " + Thread.currentThread().getName()
          + ", time=" + System.nanoTime() + " notify~");
    }
    logger.debug("->notifyThread " + Thread.currentThread().getName()
        + ", time=" + System.nanoTime() + " exit  y~");
  }

  public static void main(String[] args) throws InterruptedException {
    final T10_wait t10Wait = new T10_wait();
    for (int i = 0; i < 5; i++) {
      new Thread(t10Wait::x, "tx-" + i).start();
    }
    for (int i = 0; i < 5; i++) {
      new Thread(t10Wait::y, "ty-" + i).start();
    }
    // 睡眠1秒，然后再唤醒
    for (int i = 0; i < 5; i++) {
      TimeUnit.SECONDS.sleep(1);
      t10Wait.notifyThread();
    }
  }
}
