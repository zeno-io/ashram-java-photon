package xyz.flysium.photon.algorithm.queueandstack.search.basic;

/**
 * 494. 目标和
 * <p>
 * https://leetcode-cn.com/problems/target-sum/
 *
 * @author zeno
 */
public interface U0494_TargetSum_2 {

  // 给定一个非负整数数组，a1, a2, ..., an, 和一个目标数，S。现在你有两个符号 + 和 -。对于数组中的任意一个整数，你都可以从 + 或 -中选择一个符号添加在前面。
  //
  // 返回可以使最终数组和为目标数 S 的所有添加符号的方法数。
  //

  //    数组非空，且长度不会超过 20 。
  //    初始的数组的和不会超过 1000 。
  //    保证返回的最终结果能被 32 位整数存下。

  //  7 ms 74.50%
  class Solution {

    public int findTargetSumWays(int[] nums, int S) {
      int sum = 0;
      for (int num : nums) {
        sum += num;
      }
      if (S > sum) {
        return 0;
      }
      // 前 i 个数的组合（+/-）和为 j 所需要的方法数
      // dp[i][j] = dp[i-1][j-nums[i]] + dp[i-1][j+nums[i]] ;
      // 可以拆分为两步骤:
      // dp[i][j] = dp[i][j] + dp[i-1][j-nums[i]]; -> dp[i][j+nums[i]] = dp[i][j+nums[i]] + dp[i-1][j];
      // dp[i][j] = dp[i][j] + dp[i-1][j+nums[i]]; -> dp[i][j-nums[i]] = dp[i][j-nums[i]] + dp[i-1][j];
      // ->
      //    dp[i][j+nums[i]] += dp[i-1][j];
      //    dp[i][j-nums[i]] += dp[i-1][j];
      final int base = sum;
      int[][] dp = new int[nums.length][2 * sum + 1];
      dp[0][base + nums[0]] += 1;
      dp[0][base - nums[0]] += 1;

      for (int i = 1; i < nums.length; i++) {
        for (int j = -sum; j <= sum; j++) {
          if (dp[i - 1][base + j] > 0) {
            dp[i][base + j + nums[i]] += dp[i - 1][base + j];
            dp[i][base + j - nums[i]] += dp[i - 1][base + j];
          }
        }
      }

      return S > 1000 ? 0 : dp[nums.length - 1][base + S];
    }

  }

}
