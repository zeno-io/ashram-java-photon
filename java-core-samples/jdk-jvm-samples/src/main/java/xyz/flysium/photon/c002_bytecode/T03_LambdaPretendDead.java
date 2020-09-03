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

package xyz.flysium.photon.c002_bytecode;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Lambda 造成的假死
 *
 * <li>
 * 使用 jps 定位 Java 程序
 * </li>
 * <li>
 * 使用 jstack -l [pid] 打印对应 Java 程序的 堆栈信息
 * </li>
 *
 * @author Sven Augustus
 */
public class T03_LambdaPretendDead {

  private static final AtomicLong L = new AtomicLong(0L);

  static {
    System.out.println("start static");
    // Lambda 本质上是在原来类 T03_LambdaPretendDead 上生成静态方法
    Thread t = new Thread(() -> {
      System.out.println("Lambda start");
      L.incrementAndGet();
    }, "t1");
    // 注意以下写法就不会假死
    // Thread t = new Thread(L::incrementAndGet, "t1");
    t.start();
    // run 调用的是 Lambda 生成的T04_LambdaPretendDead类的静态方法,
    // 因为 T03_LambdaPretendDead 还在执行static静态块,并没有初始化, 所以它的方法没法调用 (需要等待 T03_LambdaPretendDead static静态块调用完)
    try {
      t.join();  // join ，这里又要等 线程执行完，条件隐式互相等待并不释放，构成死锁
      // 只是需要注意的是，jstack -l 检测不出死锁
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("end static");
  }

  public static void main(String[] args) throws InterruptedException {
    System.out.println(L.incrementAndGet());

    TimeUnit.SECONDS.sleep(3);
  }

}
