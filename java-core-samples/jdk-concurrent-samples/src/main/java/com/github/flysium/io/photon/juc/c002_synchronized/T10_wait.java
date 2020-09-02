/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.juc.c002_synchronized;

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
