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

package com.github.flysium.io.photon.juc.c102_Exercises_A1B2C3;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport.park  / unpark
 *
 * @author Sven Augustus
 */
public class T01_LockSupport {

  static Thread charPrintThread, intPrintThread;

  public static void main(String[] args) {
    intPrintThread = new Thread(() -> {
      for (int i = 0; i < 26; i++) {
        LockSupport.park();
        System.out.print((i + 1));
        LockSupport.unpark(charPrintThread);
      }
    });
    charPrintThread = new Thread(() -> {
      for (int i = 0; i < 26; i++) {
        System.out.print((char) ('A' + i));
        LockSupport.unpark(intPrintThread);
        LockSupport.park();
      }
    });

    intPrintThread.start();
    charPrintThread.start();
  }

}
