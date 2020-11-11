package xyz.flysium.photon.xalgorithm.easy;

/**
 * 746. 使用最小花费爬楼梯
 * <p>
 * https://leetcode-cn.com/problems/min-cost-climbing-stairs/
 *
 * @author zeno
 */
public class T0746_MinCostClimbingStairs {

//数组的每个索引作为一个阶梯，第 i个阶梯对应着一个非负数的体力花费值 cost[i](索引从0开始)。
//
// 每当你爬上一个阶梯你都要花费对应的体力花费值，然后你可以选择继续爬一个阶梯或者爬两个阶梯。
//
// 您需要找到达到楼层顶部的最低花费。在开始时，你可以选择从索引为 0 或 1 的元素作为初始阶梯。
//
// 示例 1:
//
// 输入: cost = [10, 15, 20]
//输出: 15
//解释: 最低花费是从cost[1]开始，然后走两步即可到阶梯顶，一共花费15。
//
//
// 示例 2:
//
// 输入: cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
//输出: 6
//解释: 最低花费方式是从cost[0]开始，逐个经过那些1，跳过cost[3]，一共花费6。
//
//
// 注意：
//
//
// cost 的长度将会在 [2, 1000]。
// 每一个 cost[i] 将会是一个Integer类型，范围为 [0, 999]。
//
// Related Topics 数组 动态规划
// 👍 401 👎 0


  public static void main(String[] args) {
    Solution solution = new T0746_MinCostClimbingStairs().new Solution();
    System.out.println(solution.minCostClimbingStairs(
      new int[]{10, 15, 20}));
    System.out.println(solution.minCostClimbingStairs(
      new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1}));
  }

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int minCostClimbingStairs(int[] cost) {
      if (cost == null || cost.length < 2) {
        return 0;
      }
      // dp[x] = min(dp[x-1]+cost[x-1], dp[x-2]+cost[x-2])
      int[] dp = new int[cost.length + 1];
      dp[0] = 0;
      dp[1] = 0;

      for (int x = 2; x <= cost.length; x++) {
        dp[x] = Math.min(dp[x - 1] + cost[x - 1], dp[x - 2] + cost[x - 2]);
      }

      return dp[cost.length];
    }

//    public int minCostClimbingStairs(int[] cost) {
//      if (cost == null || cost.length < 2) {
//        return 0;
//      }
//      dfs(cost, 0, 0);
//      dfs(cost, 1, 0);
//      return ans;
//    }
//
//    int ans = Integer.MAX_VALUE;
//
//    private void dfs(int[] cost, int currIdx, int sum) {
//      if (sum >= ans) {
//        return;
//      }
//      if (currIdx >= cost.length) {
//        ans = Math.min(ans, sum);
//        return;
//      }
//      sum += cost[currIdx];
//      dfs(cost, currIdx + 1, sum);
//      dfs(cost, currIdx + 2, sum);
//    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
