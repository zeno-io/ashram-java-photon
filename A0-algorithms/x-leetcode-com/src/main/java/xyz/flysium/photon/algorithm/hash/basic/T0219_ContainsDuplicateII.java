package xyz.flysium.photon.algorithm.hash.basic;

import java.util.HashMap;

/**
 * 219. 存在重复元素 II
 * <p>
 * https://leetcode-cn.com/problems/contains-duplicate-ii/
 *
 * @author zeno
 */
public interface T0219_ContainsDuplicateII {

  // 给定一个整数数组和一个整数 k，判断数组中是否存在两个不同的索引 i 和 j，
  //  使得 nums [i] = nums [j]，并且 i 和 j 的差的 绝对值 至多为 k。
  //

  // 8ms 96.96%
  class Solution {

    public boolean containsNearbyDuplicate(int[] nums, int k) {
      if (nums.length == 0 || k == 0) {
        return false;
      }
      HashMap<Integer, Integer> hash = new HashMap<>(nums.length, 1);

      for (int i = 0; i < nums.length; i++) {
        Integer index = hash.get(nums[i]);
        if (index != null) {
          if (i - index <= k) {
            return true;
          }
        }
        hash.put(nums[i], i);
      }

      return false;
    }

  }

}
