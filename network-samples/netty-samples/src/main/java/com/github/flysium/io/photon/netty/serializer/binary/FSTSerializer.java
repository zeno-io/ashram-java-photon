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
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.nustaq.serialization.FSTConfiguration;

/**
 * FST Serializer.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class FSTSerializer implements Serializer {

  //  private FSTObjectInput input;
//  private FSTObjectOutput output;
//
//  public FSTSerializer() {
//    try {
//      input = new FSTObjectInput();
//      output = new FSTObjectOutput();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
  private static final LoadingCache<Class<?>, FSTConfiguration> CONFIGURATION_LOADING_CACHE = CacheBuilder
      .newBuilder()
      .build(new CacheLoader<Class<?>, FSTConfiguration>() {
        @Override
        public FSTConfiguration load(Class<?> cls) throws Exception {
          return FSTConfiguration.createDefaultConfiguration();
        }
      });

  @Override
  public String name() {
    return "FST";
  }

  @Override
  public <T> byte[] serialize(T object) throws Exception {
//    output.resetForReUse();
//    output.writeObject(object);
//    return output.getCopyOfWrittenBuffer();
    return  getFSTConfiguration(object.getClass()).asByteArray(object);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T deserialize(byte[] data, Class<T> type) throws Exception {
//    input.resetForReuseUseArray(data);
//    return (T) input.readObject(type);
    return (T) getFSTConfiguration(type).asObject(data);
  }

  private static FSTConfiguration getFSTConfiguration(Class<?> clz) throws IOException {
    try {
      return CONFIGURATION_LOADING_CACHE.get(clz);
    } catch (ExecutionException e) {
      throw new IOException("create FSTConfiguration error, class:"+clz);
    }
  }

}
