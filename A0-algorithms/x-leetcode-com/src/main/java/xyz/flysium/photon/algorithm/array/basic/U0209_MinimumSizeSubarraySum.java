package xyz.flysium.photon.algorithm.array.basic;

/**
 * 209. 长度最小的子数组
 * <p>
 * https://leetcode-cn.com/problems/minimum-size-subarray-sum/
 *
 * @author zeno
 */
public interface U0209_MinimumSizeSubarraySum {

  //  给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的 连续 子数组，并返回其长度。
  //  如果不存在符合条件的子数组，返回 0。
  //  如果你已经完成了 O(n) 时间复杂度的解法, 请尝试 O(n log n) 时间复杂度的解法。
  class Solution {

    public int minSubArrayLen(int s, int[] nums) {
      long ans = Long.MAX_VALUE;
      int start = 0, end = 0;
      int sum = 0;

      while (end < nums.length) {
        sum += nums[end];
        while (sum >= s) {
          ans = Math.min(ans, end - start + 1);
          sum -= nums[start];
          start++;
        }
        end++;
      }

      return ans == Long.MAX_VALUE ? 0 : (int) ans;
    }

  }

}
