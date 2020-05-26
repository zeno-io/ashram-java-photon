/*
 * Apache License 2.0
 *
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.jvm.c050_gc;

import java.time.Instant;

/**
 * 栈上分配，依赖于逃逸分析和标量替换
 *
 * <li>
 * -server -Xmx15m -Xms15m -XX:+PrintGCDetails -XX:-UseTLAB -XX:+DoEscapeAnalysis -XX:+EliminateAllocations
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
   * 一个User对象的大小：markdown 8 + class pointer 4 + int  4 + string (oops) 4 + padding 4 = 24B <br> 如果分配 100_000_000 个，则需要
   * 2400_000_000 字节， 约 2.24 GB。
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
