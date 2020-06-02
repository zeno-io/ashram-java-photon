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

package com.github.flysium.io.photon.netty.serializer.binary;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.github.flysium.io.photon.netty.serializer.Serializer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Hession Serializer.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class HessionSerializer implements Serializer {

  @Override
  public String name() {
    return "Hession";
  }

  @Override
  public <T> byte[] serialize(T object) throws Exception {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    Hessian2Output output = new Hessian2Output(bytes);
    output.writeObject(object);
    output.flush();
    output.close(); // flush to avoid EOF error
    return bytes.toByteArray();
  }

  @Override  @SuppressWarnings("unchecked")
  public <T> T deserialize(byte[] data, Class<T> type) throws Exception {
    Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(data));
    return (T) input.readObject(type);
  }

}
