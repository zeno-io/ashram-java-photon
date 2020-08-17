/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.flysium.photon.rpc.remoting.serializer.binary;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.objenesis.strategy.StdInstantiatorStrategy;
import xyz.flysium.photon.rpc.remoting.serializer.Serializer;

/**
 * Kryo Serializer.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class KryoSerializer implements Serializer {

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

  @Override
  public String name() {
    return "Kryo";
  }

  @Override
  public <T> byte[] serialize(T object) throws Exception {
    // reset
//    output.setBuffer(buffer, -1);
//    kryo.writeObject(output, object);
//    return output.toBytes();
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Output output = new Output(bos)) {

      Kryo kryo = THREAD_LOCAL.get();
      kryo.writeObject(output, object);
      return output.toBytes();
    }
  }

  @Override
  public <T> T deserialize(byte[] data, Class<T> type) throws Exception {
//    input.setBuffer(data);
//    return kryo.readObject(input, type);
    try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
        Input input = new Input(bis)) {

      Kryo kryo = THREAD_LOCAL.get();
      return kryo.readObject(input, type);
    }
  }

}
