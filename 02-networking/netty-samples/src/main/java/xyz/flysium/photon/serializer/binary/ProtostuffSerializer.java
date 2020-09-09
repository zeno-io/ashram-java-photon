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

package xyz.flysium.photon.serializer.binary;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import xyz.flysium.photon.serializer.Serializer;

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
