package xyz.flysium.photon.algorithm.hash.basic;

import java.util.LinkedList;

/**
 * 706. 设计哈希映射
 * <p>
 * https://leetcode-cn.com/problems/design-hashmap/
 *
 * @author zeno
 */
public interface T0706_DesignHashmap_1 {

  // 不使用任何内建的哈希表库设计一个哈希映射
  //    所有的值都在 [0, 1000000]的范围内。
  //    操作的总数目在[1, 10000]范围内。
  //    不要使用内建的哈希集合库。

  // 25 ms 74.66%

  class MyHashNode {

    final int key;
    int value;

    public MyHashNode(int key, int value) {
      this.key = key;
      this.value = value;
    }
  }


  class MyHashMap {

    private final LinkedList<MyHashNode>[] arr;
    private final int capacity;

    /**
     * Initialize your data structure here.
     */
    public MyHashMap() {
      capacity = 2069; // 2 ^ 10
      arr = new LinkedList[capacity];
    }

    private int indexFor(int key) {
      return key & (capacity - 1);
    }

    /**
     * value will always be non-negative.
     */
    public void put(int key, int value) {
      int index = indexFor(key);
      LinkedList<MyHashNode> l = arr[index];
      if (l != null) {
        for (MyHashNode node : l) {
          // update
          if (node.key == key) {
            node.value = value;
            return;
          }
        }
      } else {
        l = new LinkedList<>();
        arr[index] = l;
      }
      // insert
      l.addLast(new MyHashNode(key, value));
    }

    /**
     * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping
     * for the key
     */
    public int get(int key) {
      int index = indexFor(key);
      LinkedList<MyHashNode> l = arr[index];
      if (l == null) {
        return -1;
      }
      for (MyHashNode node : l) {
        if (node.key == key) {
          return node.value;
        }
      }
      return -1;
    }

    /**
     * Removes the mapping of the specified value key if this map contains a mapping for the key
     */
    public void remove(int key) {
      int index = indexFor(key);
      LinkedList<MyHashNode> l = arr[index];
      if (l == null) {
        return;
      }
      l.removeIf(node -> node.key == key);
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
