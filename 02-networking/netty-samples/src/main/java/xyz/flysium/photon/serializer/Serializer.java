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

package xyz.flysium.photon.serializer;

/**
 * Serializer Interface.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public interface Serializer {

  /**
   * Get The name of Serializer
   *
   * @return The name of Serializer
   */
  String name();

  /**
   * serialize object to byte array.
   *
   * @param object Object
   * @return data byte array
   * @throws Exception any exception while in serialize
   */
  <T> byte[] serialize(T object) throws Exception;

  /**
   * deserialize byte array  to object.
   *
   * @param data data byte array
   * @param type Type of data
   * @return object
   * @throws Exception any exception while in deserialize
   */
  <T> T deserialize(byte[] data, Class<T> type) throws Exception;

}
