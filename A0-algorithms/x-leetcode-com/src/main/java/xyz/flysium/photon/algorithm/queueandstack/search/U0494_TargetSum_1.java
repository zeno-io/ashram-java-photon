package xyz.flysium.photon.algorithm.queueandstack.search;

/**
 * 494. 目标和
 * <p>
 * https://leetcode-cn.com/problems/target-sum/
 *
 * @author zeno
 */
public interface U0494_TargetSum_1 {

  // 给定一个非负整数数组，a1, a2, ..., an, 和一个目标数，S。现在你有两个符号 + 和 -。对于数组中的任意一个整数，你都可以从 + 或 -中选择一个符号添加在前面。
  //
  // 返回可以使最终数组和为目标数 S 的所有添加符号的方法数。
  //

  //    数组非空，且长度不会超过 20 。
  //    初始的数组的和不会超过 1000 。
  //    保证返回的最终结果能被 32 位整数存下。

  // 	690 ms DFS
  class Solution {

    private int ways = 0;

    public int findTargetSumWays(int[] nums, int S) {
      dfs(nums, S, 0, 0);
      return ways;
    }

    private void dfs(final int[] nums, final int S, int sum, int index) {
      if (index == nums.length) {
        if (S == sum) {
          ways++;
        }
        return;
      }
      dfs(nums, S, sum + nums[index], index + 1);
      dfs(nums, S, sum - nums[index], index + 1);
    }

  }

}
