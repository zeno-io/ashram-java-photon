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

package com.github.flysium.io.photon.juc.c007_AQS;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 深入 AbstractQueuedSynchronizer 源码
 *
 * @author Sven Augustus
 */
public class StartToDeepInAQS_01_ReentrantLock {

  volatile static int i = 0;
  static ReentrantLock lock = new ReentrantLock(true);
  //  ReentrantLock lock = new ReentrantLock(false);

  public static void main(String[] args) {
    lock.lock();
    try {
      i++;
    } finally {
      lock.unlock();
    }

  }

}
