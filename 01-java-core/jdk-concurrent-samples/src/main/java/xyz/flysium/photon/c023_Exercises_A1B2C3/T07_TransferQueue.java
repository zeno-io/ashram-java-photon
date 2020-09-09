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

package xyz.flysium.photon.c023_Exercises_A1B2C3;

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
