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

package xyz.flysium.photon.c008_ref_and_threadlocal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ThreadLocal的隔离性
 *
 * @author Sven Augustus
 */
public class T01_ThreadLocal {

  static ThreadLocal<LocalDateTime> tl = new ThreadLocal<LocalDateTime>() {

    /**
     * 初始化ThreadLocal变量，解决get()返回null问题
     */
    @Override
    protected LocalDateTime initialValue() {
      return LocalDateTime.now();
    }

  };

  public static void main(String[] args) throws InterruptedException {
    Runnable r = () -> {
      try {
        tl.set(LocalDateTime.now().plusHours(1));

        for (int i = 0; i < 3; i++) {
          System.out.println(Thread.currentThread().getName()
              + " " + tl.get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
          Thread.sleep(100);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };

    Thread a = new Thread(r, "A");
    a.start();

    Thread.sleep(1000);

    Thread b = new Thread(r, "B");
    b.start();

    a.join();
    b.join();
    tl.remove();
  }

}
