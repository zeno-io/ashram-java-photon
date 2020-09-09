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

package xyz.flysium.photon.samples;

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
