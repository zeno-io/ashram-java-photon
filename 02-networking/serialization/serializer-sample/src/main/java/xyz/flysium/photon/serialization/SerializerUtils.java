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

package xyz.flysium.photon.serialization;

import xyz.flysium.photon.serialization.binary.KryoSerialization;

/**
 * <code>Serializer</code> Utils
 *
 * @author Sven Augustus
 * @version 1.0
 */
public final class SerializerUtils {

  private SerializerUtils() {
  }

  private static final SerializationDelegate DELEGATE
//      = new JDKSerializer();
    = new KryoSerialization();
//      = new ProtostuffSerializer();
//      = new HessionSerializer();
//      = new FSTSerializer();
//      = new GsonSerializer();
//      = new JacksonSerializer();
//      = new FastJsonSerializer();

  /**
   * To bytes
   *
   * @param msg <code>InstantMessage</code>
   * @return bytes
   * @throws Exception any Exception while writing
   */
  public static <T> byte[] toBytes(T msg) throws Exception {
//    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(baos);) {
//      oos.writeObject(msg);
//      return baos.toByteArray();
//    }
    try {
      return DELEGATE.serializeToByteArray(msg);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * From Bytes
   *
   * @param bytes bytes
   * @return <code>InstantMessage</code>
   * @throws Exception any Exception while reading
   */
  public static <T> T fromBytes(byte[] bytes, Class<T> type) throws Exception {
//    try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//        ObjectInputStream ois = new ObjectInputStream(bais);) {
//      return (InstantMessage) ois.readObject();
//    }
    try {
      return (T) DELEGATE.deserializeFromByteArray(bytes, type);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

}
