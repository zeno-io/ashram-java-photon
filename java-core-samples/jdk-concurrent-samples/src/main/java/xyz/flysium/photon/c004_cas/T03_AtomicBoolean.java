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

package xyz.flysium.photon.c004_cas;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * AtomicBoolean 用法
 *
 * @author Sven Augustus
 */
public class T03_AtomicBoolean {

  static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

  // 只会输出一个“我成功了！”，说明征用过程中达到了锁的效果。
  public static void main(String[] args) {
    for (int i = 0; i < 100; i++) {
      new Thread(() -> {
        if (atomicBoolean.compareAndSet(false, true)) {
          System.out.println(Thread.currentThread().getName() + " 我成功了！");
        }
      }, "t" + i).start();
    }
  }

}
