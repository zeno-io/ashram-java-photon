package xyz.flysium.photon.algorithm.hash.basic.beginner;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 350. 两个数组的交集 II
 * <p>
 * https://leetcode-cn.com/problems/intersection-of-two-arrays-ii/
 *
 * @author zeno
 */
public interface T0350_IntersectionOfTwoArraysII {

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

  // 	4 ms 61.74%
  class Solution {

    public int[] intersect(int[] nums1, int[] nums2) {
      if (nums1.length > nums2.length) {
        return intersect(nums2, nums1);
      }
      // count in nums1
      HashMap<Integer, Integer> initHash = new HashMap<>(nums1.length, 1);
      // mark 1 if in nums1, and then count in nums2
      HashMap<Integer, Integer> hash = new HashMap<>(nums1.length, 1);
      for (int num : nums1) {
        Integer countInNums1 = initHash.get(num);
        if (countInNums1 != null) {
          initHash.put(num, countInNums1 + 1);
        } else {
          initHash.put(num, 1);
        }
        hash.put(num, 1);
      }
      // use max length to store enough elements
      int[] ans = new int[nums2.length];
      int index = 0;
      for (int num : nums2) {
        Integer countInNums2 = hash.get(num);
        if (countInNums2 != null) {
          // 输出结果中每个元素出现的次数，应与元素在两个数组中出现次数的最小值一致。
          if (countInNums2 <= initHash.get(num)) {
            ans[index++] = num;
          }
          hash.put(num, countInNums2 + 1);
        }
      }
      Arrays.sort(ans, 0, index);
      return Arrays.copyOfRange(ans, 0, index);
    }

  }

}
