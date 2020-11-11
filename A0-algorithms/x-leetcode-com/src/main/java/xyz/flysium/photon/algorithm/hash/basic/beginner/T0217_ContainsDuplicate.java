package xyz.flysium.photon.algorithm.hash.basic.beginner;

import java.util.HashSet;

/**
 * 217. 存在重复元素
 * <p>
 * https://leetcode-cn.com/problems/contains-duplicate/
 *
 * @author zeno
 */
public interface T0217_ContainsDuplicate {

  // 给定一个整数数组，判断是否存在重复元素。
  //
  // 如果任意一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。

  // 5ms 77.56%
  class Solution {

    public boolean containsDuplicate(int[] nums) {
      HashSet<Integer> s = new HashSet<>(nums.length);
      for (int num : nums) {
        if (!s.add(num)) {
          return true;
        }
      }
      return false;
    }

  }

// 6ms 73.37%
//  class Solution {
//
//    public boolean containsDuplicate(int[] nums) {
//      MyHashSet has = new MyHashSet(40960);
//      for (int num : nums) {
//        if (!has.add(num)) {
//          return true;
//        }
//      }
//      return false;
//    }
//
//    static class MyHashSet {
//
//      private final LinkedList<Integer>[] arr;
//      private final int capacity;
//
//      MyHashSet(int capacity) {
//        this.capacity = capacity;
//        this.arr = new LinkedList[capacity];
//      }
//
//      public boolean add(int value) {
//        int index = indexFor(value);
//        LinkedList<Integer> l = arr[index];
//        boolean exists = false;
//        if (l == null) {
//          l = new LinkedList<>();
//          arr[index] = l;
//        }
//        for (Integer e : l) {
//          if (e == value) {
//            exists = true;
//            return false;
//          }
//        }
//        if (!exists) {
//          l.add(value);
//        }
//        return !exists;
//      }
//
//      private int indexFor(int key) {
//        return key & (capacity - 1);
//      }
//
//    }
//
//  }

}
