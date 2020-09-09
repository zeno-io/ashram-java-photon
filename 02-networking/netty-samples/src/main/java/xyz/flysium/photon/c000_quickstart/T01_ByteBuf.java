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

package xyz.flysium.photon.c000_quickstart;

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
