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
