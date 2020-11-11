package xyz.flysium.photon.algorithm.hash.basic.beginner;

import java.util.Arrays;

/**
 * 136. 只出现一次的数字
 * <p>
 * https://leetcode-cn.com/problems/single-number/
 *
 * @author zeno
 */
public interface T0136_SingleNumber_1 {

  // 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
  // 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？

  // 6 ms 22.79%
  class Solution {

    public int singleNumber(int[] nums) {
      Arrays.sort(nums);
      if (nums.length == 1) {
        return nums[0];
      }
      if (nums[nums.length - 1] != nums[nums.length - 2]) {
        return nums[nums.length - 1];
      }
      for (int i = 0; i < nums.length - 1; i = i + 2) {
        if (nums[i] != nums[i + 1]) {
          return nums[i];
        }
      }
      return -1;
    }

  }

}
