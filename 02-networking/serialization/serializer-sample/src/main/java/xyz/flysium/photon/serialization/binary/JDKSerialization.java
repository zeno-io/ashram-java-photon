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

package xyz.flysium.photon.serialization.binary;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import xyz.flysium.photon.serialization.SerializationDelegate;

/**
 * JDK Built-in Serializer.
 *
 * @author Sven Augustus
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class JDKSerialization extends SerializationDelegate {

  public JDKSerialization() {
    super((t, os) -> {
      new ObjectOutputStream(os).writeObject(t);
    }, (is, type) -> {
      ObjectInputStream in = new ObjectInputStream(is);
      try {
        return in.readObject();
      } catch (ClassNotFoundException e) {
        throw new IOException(e);
      }
    });
  }

  @Override
  public String name() {
    return "JDK Built-in";
  }

}
