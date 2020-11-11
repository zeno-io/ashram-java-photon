package xyz.flysium.photon.jianzhioffer.dynamic.easy;

/**
 * 剑指 Offer 42. 连续子数组的最大和
 * <p>
 * https://leetcode-cn.com/problems/lian-xu-zi-shu-zu-de-zui-da-he-lcof/
 *
 * @author zeno
 */
public interface J0042_MaxSubArray {
  // 输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
  //
  //要求时间复杂度为O(n)。
  //
  //提示：
  //
  //    1 <= arr.length <= 10^5
  //    -100 <= arr[i] <= 100

  // 执行用时：1 ms, 在所有 Java 提交中击败了99.42% 的用户
  class Solution {

    public int maxSubArray(int[] nums) {
      if (nums.length == 1) {
        return nums[0];
      }
      // dp[n] 表示以n为结尾的所有子数组和的最大值
      // dp[0] = nums[0]
      // dp[1] = max(nums[0]+nums[1], nums[1])
      // dp[2] = max(nums[0]+nums[1]+nums[2], nums[1]+nums[2], nums[2])
      // dp[3] = max(nums[0]+nums[1]+nums[2]+nums[3], nums[1]+nums[2]+nums[3], nums[2]+nums[3], nums[3])
      // ...
      // 如果
      //      dp[2] <=0     dp[3] = nums[3]
      //      dp[2] >0      dp[3] = max(nums[0]+nums[1]+nums[2], nums[1]+nums[2], nums[2]) + nums[3] = dp[2] + nums[3]
      //...
      //      dp[n-1] <=0   dp[n] = nums[n]
      //      dp[n-1] >0    dp[n] = dp[n-1] + nums[n]
//      int[] dp = new int[nums.length];
//      dp[0] = nums[0];
//      int maxSum = dp[0];
//      for (int n = 1; n < nums.length; n++) {
//        dp[n] = (dp[n - 1] <= 0) ? nums[n] : (dp[n - 1] + nums[n]);
//        maxSum = Math.max(maxSum, dp[n]);
//      }
//      return maxSum;
      int maxSum = nums[0];
      for (int n = 1; n < nums.length; n++) {
        nums[n] = (nums[n - 1] <= 0) ? nums[n] : (nums[n - 1] + nums[n]);
        maxSum = Math.max(maxSum, nums[n]);
      }
      return maxSum;
    }

  }

}
