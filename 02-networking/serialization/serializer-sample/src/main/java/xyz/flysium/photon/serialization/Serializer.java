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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A strategy interface for streaming an object to an OutputStream.
 *
 * @param <T> the object type
 * @author zeno
 * @see Deserializer
 */
@FunctionalInterface
public interface Serializer<T> {

  /**
   * Write an object of type T to the given OutputStream.
   * <p>Note: Implementations should not close the given OutputStream
   * (or any decorators of that OutputStream) but rather leave this up to the caller.
   *
   * @param object       the object to serialize
   * @param outputStream the output stream
   * @throws IOException in case of errors writing to the stream
   */
  void serialize(T object, OutputStream outputStream) throws IOException;

  /**
   * Turn an object of type T into a serialized byte array.
   *
   * @param object the object to serialize
   * @return the resulting byte array
   * @throws IOException in case of serialization failure
   * @since 5.2.7
   */
  default byte[] serializeToByteArray(T object) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
    serialize(object, out);
    return out.toByteArray();
  }

}
