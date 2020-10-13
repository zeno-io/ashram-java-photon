package xyz.flysium.photon.algorithm.hash.basic;

import java.util.Arrays;

/**
 * 349. 两个数组的交集
 * <p>
 * https://leetcode-cn.com/problems/intersection-of-two-arrays/
 *
 * @author zeno
 */
public interface T0349_IntersectionOfTwoArrays {

  // 给定两个数组，编写一个函数来计算它们的交集。

  // 输出结果中的每个元素一定是唯一的。
  // 我们可以不考虑输出结果的顺序。

  // 1ms 99.91%
  class Solution {

    // 仅适用于最大值与最小值没有超过 int的范围，且不过于大
    public int[] intersection(int[] nums1, int[] nums2) {
      if (nums1.length > nums2.length) {
        return intersection(nums2, nums1);
      }
      int min = Integer.MAX_VALUE;
      int max = Integer.MIN_VALUE;
      for (int num : nums1) {
        if (num > max) {
          max = num;
        }
        if (num < min) {
          min = num;
        }
      }
      for (int num : nums2) {
        if (num > max) {
          max = num;
        }
        if (num < min) {
          min = num;
        }
      }
      boolean[] hash = new boolean[max - min + 1];
      for (int num : nums1) {
        hash[num - min] = true;
      }
      int[] ans = new int[nums1.length + 1];
      int index = 0;
      for (int num : nums2) {
        if (hash[num - min]) {
          ans[index++] = num;
          hash[num - min] = false;
        }
      }
      return Arrays.copyOfRange(ans, 0, index);
    }

  }

}
