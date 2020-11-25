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

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.ByteArrayOutputStream;
import org.objenesis.strategy.StdInstantiatorStrategy;
import xyz.flysium.photon.serialization.SerializationDelegate;

/**
 * Kryo Serializer.
 *
 * @author Sven Augustus
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class KryoSerialization extends SerializationDelegate {

  //  private final Kryo kryo = new Kryo();
//
//  {
//    kryo.setReferences(false);
//    kryo.setRegistrationRequired(true);
//  }
  private static final ThreadLocal<Kryo> THREAD_LOCAL = new ThreadLocal<Kryo>() {
    @Override
    protected Kryo initialValue() {
      Kryo kryo = new Kryo();
      kryo.setInstantiatorStrategy(
        new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
      return kryo;
    }
  };

//  private final byte[] buffer = new byte[512];
//  private final Output output = new Output(buffer, -1);
//  private final Input input = new Input(buffer);
//  public KryoSerializer(Class... types) {
//    if (types != null) {
//      for (Class type : types) {
//        kryo.register(type);
//      }
//    }
//  }

  public KryoSerialization() {
    super((t, os) -> {
      // reset
//    output.setBuffer(buffer, -1);
//    kryo.writeObject(output, object);
//    return output.toBytes();
      try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Output output = new Output(bos)) {

        Kryo kryo = THREAD_LOCAL.get();
        kryo.writeObject(output, t);
        os.write(output.toBytes());
      }
    }, (is, type) -> {
//    input.setBuffer(data);
//    return kryo.readObject(input, type);
      try (Input input = new Input(is)) {

        Kryo kryo = THREAD_LOCAL.get();
        return kryo.readObject(input, type);
      }
    });
  }

  @Override
  public String name() {
    return "Kryo";
  }

}
