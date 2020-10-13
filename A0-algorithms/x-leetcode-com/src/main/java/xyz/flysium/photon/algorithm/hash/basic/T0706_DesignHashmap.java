package xyz.flysium.photon.algorithm.hash.basic;

import java.util.Arrays;

/**
 * 706. 设计哈希映射
 * <p>
 * https://leetcode-cn.com/problems/design-hashmap/
 *
 * @author zeno
 */
public interface T0706_DesignHashmap {

  // 不使用任何内建的哈希表库设计一个哈希映射
  //    所有的值都在 [0, 1000000]的范围内。
  //    操作的总数目在[1, 10000]范围内。
  //    不要使用内建的哈希集合库。

  // 21 ms 88.43%

  class MyHashNode {

    final int key;
    int value;

    public MyHashNode(int key, int value) {
      this.key = key;
      this.value = value;
    }
  }


  class MyHashMap {

    private MyHashNode[] arr;

    /**
     * Initialize your data structure here.
     */
    public MyHashMap() {
      arr = new MyHashNode[256];
    }

    /**
     * value will always be non-negative.
     */
    public void put(int key, int value) {
      if (key >= arr.length) {
        arr = Arrays.copyOfRange(arr, 0, Math.max(arr.length * 2 / 3, key + 2));
      }
      MyHashNode node = arr[key];
      if (node != null) {
        node.value = value;
        return;
      }
      node = new MyHashNode(key, value);
      arr[key] = node;
    }

    /**
     * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping
     * for the key
     */
    public int get(int key) {
      if (key >= arr.length) {
        return -1;
      }
      MyHashNode node = arr[key];
      if (node == null) {
        return -1;
      }
      return node.value;
    }

    /**
     * Removes the mapping of the specified value key if this map contains a mapping for the key
     */
    public void remove(int key) {
      if (key >= arr.length) {
        return;
      }
      MyHashNode node = arr[key];
      if (node == null) {
        return;
      }
      arr[key] = null;
    }

  }

/**
 * Your MyHashSet object will be instantiated and called as such:
 * MyHashSet obj = new MyHashSet();
 * obj.add(key);
 * obj.remove(key);
 * boolean param_3 = obj.contains(key);
 */
}
