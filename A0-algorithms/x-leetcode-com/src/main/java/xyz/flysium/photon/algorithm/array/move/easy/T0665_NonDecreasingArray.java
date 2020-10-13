package xyz.flysium.photon.algorithm.array.move.easy;

/**
 * 665. 非递减数列
 * <p>
 * https://leetcode-cn.com/problems/non-decreasing-array/
 *
 * @author zeno
 */
public class T0665_NonDecreasingArray {

  //  给你一个长度为 n 的整数数组，请你判断在 最多 改变 1 个元素的情况下，该数组能否变成一个非递减数列。
  //  我们是这样定义一个非递减数列的： 对于数组中所有的 i (0 <= i <= n-2)，总满足 nums[i] <= nums[i + 1]。
  //    1 <= n <= 10 ^ 4
  //    - 10 ^ 5 <= nums[i] <= 10 ^ 5
  static class Solution {

    public boolean checkPossibility(int[] nums) {
      boolean change = false;
      for (int i = 0; i < nums.length - 1; i++) {
        // 递减数列, 需要尝试修改
        if (nums[i] > nums[i + 1]) {
          // 修改过一次, 又需要修改, 不满足要求
          if (change) {
            return false;
          }
          if (i > 0 && i < nums.length - 2
            && nums[i] > nums[i + 2]
            && nums[i - 1] > nums[i + 1]) {
            return false;
          }
          change = true;
        }
      }
      return true;
    }

  }

}
