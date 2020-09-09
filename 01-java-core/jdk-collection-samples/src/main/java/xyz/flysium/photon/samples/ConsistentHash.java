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
