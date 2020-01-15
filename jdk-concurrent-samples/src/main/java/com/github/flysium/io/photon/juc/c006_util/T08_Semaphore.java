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

package com.github.flysium.io.photon.juc.c006_util;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore维护一个许可集。 如有必要，在许可可用前会阻塞每一个 acquire()，然后再获取该许可。每个 release() 添加一个许可，从而可能释放一个正在阻塞的获取者。
 *
 * @author Sven Augustus
 */
public class T08_Semaphore {

  public static void main(String[] args) {
    // Semaphore semaphore = new Semaphore(3);
    Semaphore semaphore = new Semaphore(3, true);

    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        try {
          TimeUnit.MILLISECONDS.sleep(5);
          semaphore.acquire();
          System.out.println(Thread.currentThread().getName() + " 获得许可证");
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          semaphore.release();
        }
      }, "t" + i).start();
    }
  }
}
