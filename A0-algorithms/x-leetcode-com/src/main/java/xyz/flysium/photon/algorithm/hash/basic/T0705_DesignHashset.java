package xyz.flysium.photon.algorithm.hash.basic;

import java.util.Arrays;

/**
 * 705. 设计哈希集合
 * <p>
 * https://leetcode-cn.com/problems/design-hashset/
 *
 * @author zeno
 */
public interface T0705_DesignHashset {

  // 不使用任何内建的哈希表库设计一个哈希集合
  //    所有的值都在 [0, 1000000]的范围内。
  //    操作的总数目在[1, 10000]范围内。
  //    不要使用内建的哈希集合库。

  // 16 ms 98.01%
  class MyHashSet {

    private boolean[] arr;

    /**
     * Initialize your data structure here.
     */
    public MyHashSet() {
      arr = new boolean[256];
    }

    public void add(int key) {
      if (key >= arr.length) {
        arr = Arrays.copyOfRange(arr, 0, Math.max(arr.length * 2 / 3, key + 2));
      }
      arr[key] = true;
    }

    public void remove(int key) {
      if (key >= arr.length) {
        return;
      }
      arr[key] = false;
    }

    /**
     * Returns true if this set contains the specified element
     */
    public boolean contains(int key) {
      if (key >= arr.length) {
        return false;
      }
      return arr[key];
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
