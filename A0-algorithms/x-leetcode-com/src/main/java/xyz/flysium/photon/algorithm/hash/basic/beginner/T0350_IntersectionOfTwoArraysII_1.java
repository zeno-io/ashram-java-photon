package xyz.flysium.photon.algorithm.hash.basic.beginner;

import java.util.Arrays;

/**
 * 350. 两个数组的交集 II
 * <p>
 * https://leetcode-cn.com/problems/intersection-of-two-arrays-ii/
 *
 * @author zeno
 */
public interface T0350_IntersectionOfTwoArraysII_1 {

  // 给定两个数组，编写一个函数来计算它们的交集。

  // 说明：
  //
  //    输出结果中每个元素出现的次数，应与元素在两个数组中出现次数的最小值一致。
  //    我们可以不考虑输出结果的顺序。
  //
  // 进阶：
  //
  //    如果给定的数组已经排好序呢？你将如何优化你的算法？
  //    如果 nums1 的大小比 nums2 小很多，哪种方法更优？
  //    如果 nums2 的元素存储在磁盘上，内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？
  //

  // 	2 ms 99.56%
  class Solution {

    public int[] intersect(int[] nums1, int[] nums2) {
      if (nums1.length > nums2.length) {
        return intersect(nums2, nums1);
      }
      Arrays.sort(nums1);
      Arrays.sort(nums2);
      // use max length to store enough elements
      int[] ans = new int[nums2.length];
      int index = 0;
      int i = 0;
      int j = 0;
      while (i < nums1.length && j < nums2.length) {
        if (nums1[i] < nums2[j]) {
          i++;
        } else if (nums1[i] > nums2[j]) {
          j++;
        } else {
          ans[index] = nums1[i];
          i++;
          j++;
          index++;
        }
      }
      return Arrays.copyOfRange(ans, 0, index);
    }

  }

}
