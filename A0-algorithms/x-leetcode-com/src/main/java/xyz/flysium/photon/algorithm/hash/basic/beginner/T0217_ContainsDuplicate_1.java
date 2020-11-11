package xyz.flysium.photon.algorithm.hash.basic.beginner;

import java.util.Arrays;

/**
 * 217. 存在重复元素
 * <p>
 * https://leetcode-cn.com/problems/contains-duplicate/
 *
 * @author zeno
 */
public interface T0217_ContainsDuplicate_1 {

  // 给定一个整数数组，判断是否存在重复元素。
  //
  // 如果任意一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。

  // 4ms 99.10%
  class Solution {

    public boolean containsDuplicate(int[] nums) {
      Arrays.sort(nums);
      for (int i = 1; i < nums.length; i++) {
        if (nums[i] == nums[i - 1]) {
          return true;
        }
      }
      return false;
    }

  }

}
