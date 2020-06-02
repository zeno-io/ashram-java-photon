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

import com.github.flysium.io.photon.netty.serializer.Serializer;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Protostuff Serializer.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class ProtostuffSerializer implements Serializer {

//  private final Schema<T> schema;
//  private LinkedBuffer buffer = LinkedBuffer.allocate();
//  public ProtostuffSerializer(Class<T> type) {
//    schema = RuntimeSchema.getSchema(type);
//  }

  private static final LoadingCache<Class<?>, Schema<?>> SCHEMAS = CacheBuilder.newBuilder()
      .build(new CacheLoader<Class<?>, Schema<?>>() {
        @Override
        public Schema<?> load(Class<?> cls) throws Exception {

          return RuntimeSchema.createFrom(cls);
        }
      });


  @Override
  public String name() {
    return "Protostuff";
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> byte[] serialize(T object) throws Exception {
//    byte[] data = ProtobufIOUtil.toByteArray(object, schema, buffer);
//    buffer.clear();
//    return data;
    LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
    try {
      Schema schema = getSchema(object.getClass());
      return ProtostuffIOUtil.toByteArray(object, schema, buffer);
    } finally {
      buffer.clear();
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T deserialize(byte[] data, Class<T> type) throws Exception {
//    T obj = type.newInstance();
    Schema<T> schema = getSchema(type);
    T obj = schema.newMessage();
    ProtostuffIOUtil.mergeFrom(data, obj, schema);
    return (T) obj;
  }

  private static Schema getSchema(Class<?> cls) throws IOException {
    try {
      return SCHEMAS.get(cls);
    } catch (ExecutionException e) {
      throw new IOException("create protostuff schema error", e);
    }
  }

}
