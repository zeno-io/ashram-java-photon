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

package xyz.flysium.photon.rpc.remoting.serializer;


import xyz.flysium.photon.rpc.remoting.serializer.binary.KryoSerializer;

/**
 * <code>Serializer</code> Utils
 *
 * @author Sven Augustus
 * @version 1.0
 */
public final class SerializerUtils {

  private SerializerUtils() {
  }

  private static final Serializer SERIALIZER
//      = new JDKSerializer();
      = new KryoSerializer();
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
      return SERIALIZER.serialize(msg);
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
      return (T) SERIALIZER.deserialize(bytes, type);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

}
