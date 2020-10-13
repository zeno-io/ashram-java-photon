package xyz.flysium.photon.algorithm.array.traverse.easy;

/**
 * 414. 第三大的数
 * <p>
 * https://leetcode-cn.com/problems/third-maximum-number/
 *
 * @author zeno
 */
public class T0414_ThirdMaximumNumber {

  static class Solution {

    public int thirdMax(int[] nums) {
      if (nums.length == 1) {
        return nums[0];
      } else if (nums.length == 2) {
        return Math.max(nums[0], nums[1]);
      }
      // define long for number may be Integer.MIN_VALUE
      long[] max = new long[]{Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE};
      for (int num : nums) {
        if (num > max[0]) {
          max[2] = max[1];
          max[1] = max[0];
          max[0] = num;
        } else if (num > max[1] && num < max[0]) {
          max[2] = max[1];
          max[1] = num;
        } else if (num > max[2] && num < max[1]) {
          max[2] = num;
        }
      }

      return (int) (max[2] == Long.MIN_VALUE ? max[0] : max[2]);
    }

  }

}
