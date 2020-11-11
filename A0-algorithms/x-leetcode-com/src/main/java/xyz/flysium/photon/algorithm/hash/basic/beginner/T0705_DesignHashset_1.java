package xyz.flysium.photon.algorithm.hash.basic.beginner;

import java.util.LinkedList;

/**
 * 705. 设计哈希集合
 * <p>
 * https://leetcode-cn.com/problems/design-hashset/
 *
 * @author zeno
 */
public interface T0705_DesignHashset_1 {

  // 不使用任何内建的哈希表库设计一个哈希集合
  //    所有的值都在 [0, 1000000]的范围内。
  //    操作的总数目在[1, 10000]范围内。
  //    不要使用内建的哈希集合库。

  // 21 ms
  class MyHashSet {

    private final LinkedList<Integer>[] arr;
    private final int capacity;

    /**
     * Initialize your data structure here.
     */
    public MyHashSet() {
      capacity = 1024; // 2 ^ 10
      arr = new LinkedList[capacity];
    }

    private int indexFor(int key) {
      return key & (capacity - 1);
    }

    public void add(int key) {
      int index = indexFor(key);
      LinkedList<Integer> l = arr[index];
      if (l == null) {
        l = new LinkedList<>();
        arr[index] = l;
        l.addLast(key);
        return;
      }
      if (!l.contains(key)) {
        l.addLast(key);
      }
    }

    public void remove(int key) {
      int index = indexFor(key);
      LinkedList<Integer> l = arr[index];
      if (l != null) {
        l.removeIf(e -> e == key);
      }
    }

    /**
     * Returns true if this set contains the specified element
     */
    public boolean contains(int key) {
      int index = indexFor(key);
      LinkedList<Integer> l = arr[index];
      return l != null && l.contains(key);
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
