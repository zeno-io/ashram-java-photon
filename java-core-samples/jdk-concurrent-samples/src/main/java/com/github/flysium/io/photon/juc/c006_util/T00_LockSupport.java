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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport
 *
 * @author Sven Augustus
 */
public class T00_LockSupport {

  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(() -> {
      System.out.println("t1 begin...");
      LockSupport.park();
      System.out.println("t1...");
      LockSupport.park();
      System.out.println("t1 end !");
    });

    t1.start();

    TimeUnit.SECONDS.sleep(1);

    LockSupport.unpark(t1);

    TimeUnit.SECONDS.sleep(1);

    LockSupport.unpark(t1);
  }

}
