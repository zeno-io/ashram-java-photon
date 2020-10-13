package xyz.flysium.photon.algorithm.hash.basic;

import java.util.HashSet;

/**
 * 349. 两个数组的交集
 * <p>
 * https://leetcode-cn.com/problems/intersection-of-two-arrays/
 *
 * @author zeno
 */
public interface T0349_IntersectionOfTwoArrays_1 {

  // 给定两个数组，编写一个函数来计算它们的交集。

  // 输出结果中的每个元素一定是唯一的。
  // 我们可以不考虑输出结果的顺序。

  // 3ms 96.92%
  class Solution {

    public int[] intersection(int[] nums1, int[] nums2) {
      if (nums1.length > nums2.length) {
        return intersection(nums2, nums1);
      }
      HashSet<Integer> hash = new HashSet<>(nums1.length, 1);
      HashSet<Integer> hash2 = new HashSet<>(nums1.length, 1);
      for (int num : nums1) {
        hash.add(num);
      }
      for (int num : nums2) {
        if (hash.contains(num)) {
          hash2.add(num);
        }
      }
      if (hash2.isEmpty()) {
        return new int[]{};
      }
      int[] ans = new int[hash2.size()];
      int i = 0;
      for (Integer e : hash2) {
        ans[i++] = e;
      }
      return ans;
    }

  }

}
