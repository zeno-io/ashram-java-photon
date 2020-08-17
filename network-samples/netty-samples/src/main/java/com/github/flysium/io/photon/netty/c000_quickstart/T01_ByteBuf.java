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

package com.github.flysium.io.photon.netty.c000_quickstart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

/**
 * Test <code>ByteBuf</code>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T01_ByteBuf {

  public static void main(String[] args) {
    testWriteBytes();
    testReadBytes();
    testGetBytes();
  }

  private static void testWriteBytes() {
    //        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(8, 20);

    //pool
//        ByteBuf buf = UnpooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
    ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);

    print(buf);
    buf.writeBytes(new byte[]{1, 2, 3, 4});
    print(buf);
    buf.writeBytes(new byte[]{1, 2, 3, 4});
    print(buf);
    buf.writeBytes(new byte[]{1, 2, 3, 4});
    print(buf);
    buf.writeBytes(new byte[]{1, 2, 3, 4});
    print(buf);
    buf.writeBytes(new byte[]{1, 2, 3, 4});
    print(buf);

    buf.writeBytes(new byte[]{1, 2, 3, 4});
//    Exception in thread "main" java.lang.IndexOutOfBoundsException: writerIndex(20) + minWritableBytes(4) exceeds maxCapacity(20): PooledUnsafeHeapByteBuf(ridx: 0, widx: 20, cap: 20/20)
    print(buf);
  }

  private static void testReadBytes() {
    ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
    print(buf);
    buf.writeBytes(new byte[]{1, 2, 3, 4});
    print(buf);

    buf.readBytes(4);
    print(buf);
  }

  private static void testGetBytes() {
    ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
    print(buf);
    buf.writeBytes(new byte[]{1, 2, 3, 4});
    print(buf);

    byte[] bytes = new byte[4];
    buf.getBytes(buf.readerIndex(), bytes);
    print(buf);
  }

  public static void print(ByteBuf buf) {
    System.out.println("buf.isReadable()    :" + buf.isReadable());
    System.out.println("buf.readerIndex()   :" + buf.readerIndex());
    System.out.println("buf.readableBytes() :" + buf.readableBytes());
    System.out.println("buf.isWritable()    :" + buf.isWritable());
    System.out.println("buf.writerIndex()   :" + buf.writerIndex());
    System.out.println("buf.writableBytes() :" + buf.writableBytes());
    System.out.println("buf.capacity()      :" + buf.capacity());
    System.out.println("buf.maxCapacity()   :" + buf.maxCapacity());
    System.out.println("buf.isDirect()      :" + buf.isDirect());
    System.out.println("--------------");
  }

}
