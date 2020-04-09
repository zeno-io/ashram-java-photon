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

package com.github.flysium.io.photon.jvm.c010_jmm;

/**
 * CPU为了提高指令执行效率，会在一条指令执行过程中（比如去内存读数据（慢100倍）），去同时执行另一条指令，前提是，两条指令没有依赖关系
 *
 * @see <a>https://preshing.com/20120515/memory-reordering-caught-in-the-act/</a>
 */
public class T04_Disorder {

  private static int x = 0, y = 0;
  private static int a = 0, b = 0;

  public static void main(String[] args) throws InterruptedException {
    int i = 0;
    for (; ; ) {
      i++;
      x = 0;
      y = 0;
      a = 0;
      b = 0;
      Thread one = new Thread(new Runnable() {
        @Override
        public void run() {
          //由于线程 one 先启动，下面这句话让它等一等线程 other. 读着可根据自己电脑的实际性能适当调整等待时间.
          shortWait(100000);
          a = 1;
          x = b;
        }
      });

      Thread other = new Thread(new Runnable() {
        @Override
        public void run() {
          b = 1;
          y = a;
        }
      });
      one.start();
      other.start();
      one.join();
      other.join();
      String result = "第" + i + "次 (" + x + "," + y + "）";
      if (x == 0 && y == 0) {
        System.err.println(result);
        break;
      } else {
        //System.out.println(result);
      }
    }
  }

  public static void shortWait(long interval) {
    long start = System.nanoTime();
    long end;
    do {
      end = System.nanoTime();
    } while (start + interval >= end);
  }

}