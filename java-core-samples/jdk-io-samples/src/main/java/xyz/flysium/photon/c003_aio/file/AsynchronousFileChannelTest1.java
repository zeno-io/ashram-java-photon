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

package xyz.flysium.photon.c003_aio.file;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import xyz.flysium.photon.c002_nio.ByteBufferUtils;

/**
 * 异步文件读写通道：AsynchronousFileChannel 测试
 *
 * @author Sven Augustus
 */
public class AsynchronousFileChannelTest1 {

  public static void main(String[] args) {
    Path file = Paths.get(".", "file.bin");
    // System.out.println("文件路径:" + file.normalize().toAbsolutePath());

    AsynchronousFileChannel channel = null;
    try {
      channel = AsynchronousFileChannel.open(file, StandardOpenOption.CREATE,
          StandardOpenOption.READ,
          StandardOpenOption.WRITE);
      /*
       * Because of the operations are synchronous. so the read maybe ended before
       * write. You should test read/write just once every time.
       * 因为读写操作都是异步的，所以读操作也有可能在写操作前完成。 你应该每一次只测试其他一个IO操作，读或者写。
       */
      // write(channel);
      read(channel);

      // wait fur user to press a key otherwise java exits because the
      // async thread isn't important enough to keep it running.
      try {
        System.in.read();
      } catch (IOException ignored) {
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void close(AsynchronousFileChannel channel) {
    System.out.println("Close Channel");
    if (channel != null) {
      try {
        channel.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  // 读文件
  private static void read(final AsynchronousFileChannel channel) throws IOException {
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {

      @Override
      public void completed(Integer result, ByteBuffer attachment) {// IO操作完成
        System.out.println("Bytes Read = " + result);
        Serializable data = null;
        try {
          data = ByteBufferUtils.readObject(attachment);
        } catch (Exception e) {
          e.printStackTrace();
        }
        System.out.println("读文件数据:" + data);
        close(channel);
      }

      @Override
      public void failed(Throwable exc, ByteBuffer attachment) {// IO操作失败
        System.out.println("Error:" + exc.getCause());
        exc.printStackTrace();
        close(channel);
      }
    });
  }

  // 写文件
  public static void write(final AsynchronousFileChannel channel) throws IOException {
    Serializable serializable = String.valueOf("赵客缦胡缨，吴钩霜雪明。银鞍照白马，飒沓如流星。\n");
    ByteBuffer byteBuffer = ByteBufferUtils.writeObject(serializable);
    channel.write(byteBuffer, 0, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {

      // IO操作完成
      @Override
      public void completed(Integer result, ByteBuffer attachment) {
        System.out.println("Bytes write = " + result);
        close(channel);
      }

      // IO操作失败
      @Override
      public void failed(Throwable exc, ByteBuffer attachment) {
        System.out.println("Error:" + exc.getCause());
        exc.printStackTrace();
        close(channel);
      }
    });
  }

}
