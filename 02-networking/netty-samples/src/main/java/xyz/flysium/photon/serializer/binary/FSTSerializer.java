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
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.nustaq.serialization.FSTConfiguration;
import xyz.flysium.photon.serializer.Serializer;

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
    return getFSTConfiguration(object.getClass()).asByteArray(object);
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
      throw new IOException("create FSTConfiguration error, class:" + clz);
    }
  }

}
