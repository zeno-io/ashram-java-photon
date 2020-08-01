/*
 * Copyright 2020 SvenAugustus
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

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 分配堆外内存时 当受限于 MaxDirectMemorySize， 分配内存不足时会触发 System.gc()
 *
 * <li>
 * 如果是 CMS / G1，会导致 Full GC， 可以通过 -XX:+ExplicitGCInvokesConcurrent 换成 Young GC
 * <pre>
 *   -XX:MaxDirectMemorySize=5m -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC
 * </pre>
 * </li>
 * </li>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class TestDirectByteBufferGC {

  public static void main(String[] args) throws IOException {
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 1024 * 1024);
    byteBuffer = null;
    ByteBuffer byteBuffer2 = ByteBuffer.allocateDirect(1 * 1024 * 1024 + 1);

    System.in.read();
  }

}
