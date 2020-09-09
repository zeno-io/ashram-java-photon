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
