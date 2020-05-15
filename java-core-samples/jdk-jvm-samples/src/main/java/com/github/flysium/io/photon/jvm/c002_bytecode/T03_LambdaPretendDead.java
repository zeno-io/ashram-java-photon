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

package com.github.flysium.io.photon.jvm.c002_bytecode;

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
