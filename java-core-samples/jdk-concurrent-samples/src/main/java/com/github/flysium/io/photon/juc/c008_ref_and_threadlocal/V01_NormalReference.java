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

package com.github.flysium.io.photon.juc.c008_ref_and_threadlocal;

import java.io.IOException;

/**
 * 强引用
 *
 * @author Sven Augustus
 */
public class V01_NormalReference {

  public static void main(String[] args) throws IOException {
    M m = new M("Sven Augustus", 20);
    m = null;

    System.gc(); //-XX:-DisableExplicitGC

    // 阻塞，调试查看 m finalize() 是否打印
    System.in.read();
  }
  // 我被回收了！M{name='Sven Augustus', age=20}

}
