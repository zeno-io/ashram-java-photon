package xyz.flysium.photon.algorithm.array.traverse.easy;

import java.util.Arrays;

/**
 * 628. 三个数的最大乘积
 * <p>
 * https://leetcode-cn.com/problems/maximum-product-of-three-numbers/
 *
 * @author zeno
 */
public class T0628_MaximumProductOfThreeNumbers_3 {

  static class Solution {

    // 12ms
    public int maximumProduct(int[] nums) {
      Arrays.sort(nums);
      return Math.max(nums[nums.length - 1] * nums[nums.length - 2] * nums[nums.length - 3],
        nums[nums.length - 1] * nums[0] * nums[1]);
    }

  }

}
