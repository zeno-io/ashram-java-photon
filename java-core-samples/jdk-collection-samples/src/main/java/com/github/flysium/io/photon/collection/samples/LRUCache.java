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

package com.github.flysium.io.photon.collection.samples;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * 使用<code>LinkedHashMap</code>实现 LRU Cache
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class LRUCache {

  public static void main(String[] args) {
    LRUCacheImpl<Integer, Integer> cache = new LRUCacheImpl<>(10);
    for (int i = 0; i < 10; i++) {
      int p = i + 1;
      cache.put(p, null);
    }
    for (int i = 0; i < 10; i++) {
      int p = i + 1;
      if (p != 5) {
        cache.get(p);
      }
    }
    cache.get(2);
    cache.get(2);
    cache.get(2);
    cache.get(1);

    System.out.println(cache);
    cache.put(11, null);
    System.out.println(cache);
  }

  static class LRUCacheImpl<K, V> extends LinkedHashMap<K, V> {

    private final int maxThreshold;

    public LRUCacheImpl(int maxThreshold) {
      super((int) (maxThreshold / 0.75f) + 1, 0.75f, true);
      this.maxThreshold = maxThreshold;
    }

    @Override
    protected boolean removeEldestEntry(Entry eldest) {
      return size() >= maxThreshold;
    }
  }

}
