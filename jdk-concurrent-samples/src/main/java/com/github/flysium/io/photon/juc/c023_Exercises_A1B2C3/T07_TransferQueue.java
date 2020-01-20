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

package com.github.flysium.io.photon.juc.c023_Exercises_A1B2C3;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * TransferQueue
 *
 * @author Sven Augustus
 */
public class T07_TransferQueue {

  static TransferQueue<Object> transferQueue = new LinkedTransferQueue<>();

  public static void main(String[] args) {
    new Thread(() -> {
      for (int i = 0; i < 26; i++) {
        try {
          Object o = transferQueue.take();
          System.out.print(o);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        try {
          transferQueue.transfer((i + 1));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }, "CHARS").start();

    new Thread(() -> {
      for (int i = 0; i < 26; i++) {
        try {
          transferQueue.transfer((char) ('A' + i));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        try {
          Object o = transferQueue.take();
          System.out.print(o);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }, "INTS").start();

  }

}
