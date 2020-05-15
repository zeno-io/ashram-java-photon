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

package com.github.flysium.io.photon.juc.c001_thread;

import java.util.concurrent.TimeUnit;

/**
 * 守护线程
 *
 * @author Sven Augustus
 */
public class T03_DaemonThread {

  // 在Java中有两类线程：User Thread(用户线程)、Daemon Thread(守护线程)
  // User和Daemon两者几乎没有区别，唯一的不同之处就在于虚拟机的离开：
  //      如果 User Thread已经全部退出运行了，只剩下Daemon Thread存在了，虚拟机也就退出了。 因为没有了被守护者，Daemon也就没有工作可做了，也就没有继续运行程序的必要了。

  // 用户线程是保证执行完毕的，守护线程还没有执行完毕就退出了

  public static void main(String[] args) {
    Thread t = new Thread(() -> {
      try {
        // 模拟睡眠1秒
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Thread finished !");
    });
    // 设置为守护线程
    t.setDaemon(true);
    t.start();
    System.out.println("这里打印的时候，表示main线程准备结束了");
  }

}
