
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
package xyz.flysium.photon.c002_nio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

/**
 * ByteBuffer工具类
 *
 * @author Sven Augustus
 */
public class ByteBufferUtils {

  private ByteBufferUtils() {
  }

  /**
   * 序列化ByteBuffer
   *
   * @param serializable 可序列化的对象
   * @return 字节缓冲区
   * @throws IOException any IO Exceptions when write object
   */
  public static ByteBuffer writeObject(Serializable serializable) throws IOException {
    ByteArrayOutputStream bos = null;
    ObjectOutputStream oos = null;
    try {
      bos = new ByteArrayOutputStream();
      oos = new ObjectOutputStream(new BufferedOutputStream(bos));
      oos.writeObject(serializable);
    } finally {
      if (oos != null) {
        oos.close();
      }
    }
    // System.out.println(printByteArray(bos.toByteArray()));
    return ByteBuffer.wrap(bos.toByteArray());
  }

  /**
   * 反序列化 ByteBuffer
   *
   * @param byteBuffer <code>ByteBuffer</code>
   * @return 可序列化的对象
   * @throws IOException any IO Exceptions when read object
   */
  public static Serializable readObject(ByteBuffer byteBuffer)
      throws IOException, ClassNotFoundException {
    ByteArrayInputStream bis = null;
    ObjectInputStream ois = null;
    Serializable object = null;
    try {
      byte[] bytes = readToBytes(byteBuffer);
      // System.out.println(printByteArray(bytes));
      bis = new ByteArrayInputStream(bytes);
      ois = new ObjectInputStream(new BufferedInputStream(bis));
      object = (Serializable) ois.readObject();
    } finally {
      if (ois != null) {
        ois.close();
      }
    }
    return object;
  }

  /**
   * 转化byte数组
   *
   * @param byteBuffer <code>ByteBuffer</code>
   * @return byte array
   */
  public static byte[] readToBytes(ByteBuffer byteBuffer) {
    byteBuffer.flip();
    // Retrieve bytes between the position and limit
    // (see Putting Bytes into a ByteBuffer)
    byte[] bytes = new byte[byteBuffer.remaining()];
    // transfer bytes from this buffer into the given destination array
    byteBuffer.get(bytes, 0, bytes.length);
    byteBuffer.clear();
    return bytes;
  }

  /**
   * 聚集为一个ByteBuffer
   */
  public static ByteBuffer gather(List<ByteBuffer> bytes) {
    int length = 0;
    for (int i = 0; i < bytes.size(); i++) {
      bytes.get(i).flip();
      length += bytes.get(i).remaining();
    }
    ByteBuffer buffer = ByteBuffer.allocate(length);
    buffer.clear();
    for (int i = 0, offset = 0; i < bytes.size(); i++) {
      while (bytes.get(i).hasRemaining()) {
        buffer.put(bytes.get(i));
      }
      bytes.get(i).clear();
    }
    return buffer;
  }

  private static String printByteArray(byte[] bytes) {
    StringBuilder stringBuilder = new StringBuilder(bytes.length);
    stringBuilder.append("length=").append(bytes.length).append("[");
    for (byte aByte : bytes) {
      stringBuilder.append(aByte);
    }
    stringBuilder.append("]");
    return stringBuilder.toString();
  }

  public static void main(String[] args) {
    ByteBuffer b1 = ByteBuffer.allocate(10);
    b1.put((byte) 1);
    b1.put((byte) 2);
    b1.put((byte) 3);
    ByteBuffer b2 = ByteBuffer.allocate(10);
    b2.put((byte) 1);
    b2.put((byte) 2);
    b2.put((byte) 3);
    ByteBuffer buffer = gather(Arrays.asList(b1, b2));
    byte[] bb = readToBytes(buffer);
    System.out.println(printByteArray(bb));
  }

}
