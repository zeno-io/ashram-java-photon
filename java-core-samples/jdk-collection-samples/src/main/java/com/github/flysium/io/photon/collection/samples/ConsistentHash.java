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

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 使用<code>TreeMap</code>实现一致性哈希 Hash
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class ConsistentHash {

  public static void main(String[] args) {
    ConsistentHashImpl ch = new ConsistentHashImpl();
    ch.addVirtualNode("10.0.2.30");
    ch.addVirtualNode("10.0.2.31");
    ch.addVirtualNode("10.0.2.32");
    ch.addVirtualNode("10.0.2.33");

    System.out.println(ch.getNode("AAB"));
    System.out.println(ch.getNode("ABC"));
  }

  static class ConsistentHashImpl {

    private final TreeMap<Long, String> ring = new TreeMap<>();
    private static final int VIRTUAL_NODE_NUM = 10;

    public ConsistentHashImpl() {
    }

    /**
     * 存在虚拟节点
     */
    public void addVirtualNode(String serverKey) {
      for (int i = 0; i < VIRTUAL_NODE_NUM; i++) {
        addToRing(serverKey + "&&VIR" + i, serverKey);
      }
      addToRing(serverKey, serverKey);
    }

    private void addToRing(String virtualNode, String entryValue) {
      long entryKey = hash(virtualNode);
      ring.put(entryKey, entryValue);
      System.out.println("虚拟节点[" + virtualNode + "]被添加, hash值为"
          + entryKey + ", 映射真实的节点[" + entryValue + "]");
    }

    /**
     * 读取节点值
     */
    public String getNode(String key) {
      long hash = hash(key);
//      System.out.println(hash);
      SortedMap<Long, String> sortedMap = ring.tailMap(hash);
//      System.out.println(sortedMap);
      String value;
      if (!sortedMap.isEmpty()) {
        value = sortedMap.get(sortedMap.firstKey());
      } else {
        value = ring.firstEntry().getValue();
      }
      return value;
    }

    /**
     * 使用的是 FNV1_32_HASH
     */
    private long hash(String key) {
      final int p = 16777619;
      int hash = (int) 2166136261L;
      for (int i = 0; i < key.length(); i++) {
        hash = (hash ^ key.charAt(i)) * p;
      }
      hash += hash << 13;
      hash ^= hash >> 7;
      hash += hash << 3;
      hash ^= hash >> 17;
      hash += hash << 5;

      // 如果算出来的值为负数则取其绝对值
      if (hash < 0) {
        hash = Math.abs(hash);
      }
      return hash;
    }

  }

}
