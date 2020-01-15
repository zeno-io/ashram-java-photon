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

package com.github.flysium.io.photon.juc.c004_cas;

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
