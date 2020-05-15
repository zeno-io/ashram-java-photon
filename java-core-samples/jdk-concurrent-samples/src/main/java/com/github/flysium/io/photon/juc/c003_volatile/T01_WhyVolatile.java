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

package com.github.flysium.io.photon.juc.c003_volatile;

import java.util.concurrent.TimeUnit;

/**
 * 为什么需要volatile ?
 *
 * @author Sven Augustus
 */
public class T01_WhyVolatile {

  private static /* volatile */ boolean running = true;

  public static void main(String[] args) {
    new Thread(() -> {
      System.out.println("begin... ");
      while (running) {
        ;
      }
      System.out.println("end !");
    }).start();

    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    running = false;
  }

}
