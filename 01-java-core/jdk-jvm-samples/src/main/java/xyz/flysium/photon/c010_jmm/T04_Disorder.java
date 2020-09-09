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

package xyz.flysium.photon.c010_jmm;

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