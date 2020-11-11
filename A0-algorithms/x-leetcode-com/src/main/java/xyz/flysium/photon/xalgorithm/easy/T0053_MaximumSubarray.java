package xyz.flysium.photon.xalgorithm.easy;

import xyz.flysium.photon.ArraySupport;

/**
 * 53. 最大子序和
 * <p>
 * https://leetcode-cn.com/problems/maximum-subarray/
 *
 * @author zeno
 */
public class T0053_MaximumSubarray {

//给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
//
// 示例:
//
// 输入: [-2,1,-3,4,-1,2,1,-5,4]
//输出: 6
//解释:连续子数组[4,-1,2,1] 的和最大，为6。
//
//
// 进阶:
//
// 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
// Related Topics 数组 分治算法 动态规划
// 👍 2563 👎 0


  public static void main(String[] args) {
    Solution solution = new T0053_MaximumSubarray().new Solution();
    System.out.println(solution.maxSubArray(ArraySupport.newArray("[-2,1,-3,4,-1,2,1,-5,4]")));
  }

  // 执行用时：1 ms, 在所有 Java 提交中击败了95.79% 的用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int maxSubArray(int[] nums) {
      // dp[x] 表示以nums[x]为结尾的最大子序和
      // dp[x] = max (nums[x], dp[x-1] + nums[x])
      int dp0 = nums[0];
      int max = dp0;
      int dp1 = 0;

      for (int i = 1; i < nums.length; i++) {
        dp1 = Math.max(nums[i], dp0 + nums[i]);
        max = Math.max(max, dp1);
        dp0 = dp1;
      }

      return max;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
