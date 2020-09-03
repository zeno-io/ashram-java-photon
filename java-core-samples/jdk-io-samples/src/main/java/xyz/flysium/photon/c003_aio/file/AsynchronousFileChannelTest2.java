
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
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import xyz.flysium.photon.c002_nio.ByteBufferUtils;

/**
 * 异步文件读写通道：AsynchronousFileChannel 读文件完整内容
 *
 * @author Sven Augustus
 */
public class AsynchronousFileChannelTest2 {

  public static void main(String[] args) {
    Path file = Paths.get(".", "file.bin");
    // System.out.println("文件路径:" + file.normalize().toAbsolutePath());

    AsynchronousFileChannel channel = null;
    try {
      channel = AsynchronousFileChannel.open(file, StandardOpenOption.CREATE,
          StandardOpenOption.READ, StandardOpenOption.WRITE);
      // 写入
      ByteBuffer byteBuffer = ByteBuffer.allocate(10240);
      byteBuffer.clear();
      for (int i = 0; i < 2000; ++i) {
        byteBuffer.put(String.valueOf(i).getBytes("utf-8"));
        byteBuffer.put(String.valueOf(",").getBytes("utf-8"));
      }
      byteBuffer.flip();
      Future<Integer> integerFuture = channel.write(byteBuffer, 0);
      integerFuture.get();
      // 读文件完整内容
      readFully(channel);

    } catch (IOException | ExecutionException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  // 读文件完整内容
  private static void readFully(final AsynchronousFileChannel channel) throws IOException {
    ByteBuffer buffer = ByteBuffer.allocate(1000);
    channel.read(buffer, 0, channel, new TryReadComplete(channel, buffer));
    // wait fur user to press a key otherwise java exits because the
    // async thread isn't important enough to keep it running.
    try {
      System.in.read();
    } catch (IOException ignored) {
    }
  }

  public static class TryReadComplete implements
      CompletionHandler<Integer, AsynchronousFileChannel> {

    // the position of next part.
    private int pos = 0;
    private final AsynchronousFileChannel channel;
    private final ByteBuffer buffer;

    public TryReadComplete(AsynchronousFileChannel channel, ByteBuffer buffer) {
      this.channel = channel;
      this.buffer = buffer;
    }

    @Override
    public void completed(Integer result, AsynchronousFileChannel attachment) {
      if (result != -1) {
        pos += result;
        // handle data
        byte[] bb = ByteBufferUtils.readToBytes(buffer);
        System.out.println(new String(bb));

        buffer.clear();// reset the buffer so you can read next part.
      }
      // start next read operation
      attachment.read(buffer, pos, attachment, this);
    }

    @Override
    public void failed(Throwable exc, AsynchronousFileChannel attachment) {
      System.out.println("Error:" + exc.getCause());
      exc.printStackTrace();
      System.out.println("Close Channel");
      if (channel != null) {
        try {
          channel.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
