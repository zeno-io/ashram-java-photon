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

package xyz.flysium.photon.c003_volatile;

import java.util.concurrent.TimeUnit;

/**
 * volatile 引用类型（包括数组）只能保证引用本身的可见性，不能保证内部字段的可见性
 *
 * @author Sven Augustus
 */
public class T02_VolatileReferenceInvalid {

  private static class RefObj {

    private Boolean running = false;

    public void m() {
      System.out.println(Thread.currentThread().getName() + " begin... ");
      while (running) {
//        try {
//          TimeUnit.MILLISECONDS.sleep(1);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
      }
      System.out.println(Thread.currentThread().getName() + " end !");
    }

  }

  volatile static RefObj refObj = new RefObj();

  public static void main(String[] args) {
    refObj.running = true;
    // 方式一
    new Thread(() -> {
      refObj.m();
    }, "T1").start();
    // 方式二：思考为什么下面这种能读到？？
    //  CPU高速缓存 与 主存 是会同步的。volatile 保证写之后，一定同步到主存。
    //  没有 volatile 只是写之后不立刻同步，但某个时间还是会同步。（这个时间其实对于人来说很短，对于CPU来说慢）
//    new Thread(() -> {
//      System.out.println(Thread.currentThread().getName() + " begin... ");
//      while (refObj.running) {
//      }
//      System.out.println(Thread.currentThread().getName() + " end !");
//    }, "T2").start();
    try {
//      TimeUnit.MILLISECONDS.sleep(1);
      // 设置 1000秒,为了减少 高速缓存与主存同步的时间差
      // 睡眠时间短或者 m 中睡眠一些时间，就可以触发 高速缓存与主存同步， 但是不稳定
      TimeUnit.MILLISECONDS.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    refObj.running = false;
  }

}

