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

package xyz.flysium.photon.c050_gc;

import java.time.Instant;

/**
 * 栈上分配，依赖于逃逸分析和标量替换
 *
 * <li>
 * -server -Xmx15m -Xms15m -XX:+PrintGCDetails -XX:-UseTLAB -XX:+DoEscapeAnalysis
 * -XX:+EliminateAllocations
 * </li>
 *
 * <li>
 * 以下的都会触发 GC:
 *   <ul>
 *    不使用逃逸分析 : -server -Xmx15m -Xms15m -XX:+PrintGCDetails -XX:-UseTLAB -XX:-DoEscapeAnalysis -XX:+EliminateAllocations
 *   </ul>
 *   <ul>
 *    不使用标量替换 : -server -Xmx15m -Xms15m -XX:+PrintGCDetails -XX:-UseTLAB -XX:+DoEscapeAnalysis -XX:-EliminateAllocations
 *    </ul>
 * </li>
 *
 * @author Sven Augustus
 */
public class TestTLAB {

  // private static User u;

  /**
   * 一个User对象的大小：markdown 8 + class pointer 4 + int  4 + string (oops) 4 + padding 4 = 24B <br> 如果分配
   * 100_000_000 个，则需要 2400_000_000 字节， 约 2.24 GB。
   */
  static class User {

    private int id;
    private String name;

    public User(int id, String name) {
      this.id = id;
      this.name = name;
    }
  }

  private static void alloc() {
    User u = new User(1, "SvenAugustus");
    // u = new User(1, "SvenAugustus");
  }

  public static void main(String[] args) throws InterruptedException {
    long start = Instant.now().toEpochMilli();
    for (int i = 0; i < 100_000_000; i++) {
      alloc();
    }
    System.out.println(Instant.now().toEpochMilli() - start);
  }

}
