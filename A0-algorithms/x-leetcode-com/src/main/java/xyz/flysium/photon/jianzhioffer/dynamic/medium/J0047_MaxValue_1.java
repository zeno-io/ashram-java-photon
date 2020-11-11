package xyz.flysium.photon.jianzhioffer.dynamic.medium;

/**
 * 剑指 Offer 47.礼物的最大价值
 * <p>
 * https://leetcode-cn.com/problems/li-wu-de-zui-da-jie-zhi-lcof/
 *
 * @author zeno
 */
public interface J0047_MaxValue_1 {
//在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。你可以从棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直
//到到达棋盘的右下角。给定一个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？
//
//
//
// 示例 1:
//
// 输入:
//[
// [1,3,1],
// [1,5,1],
// [4,2,1]
//]
//输出: 12
//解释: 路径 1→3→5→2→1 可以拿到最多价值的礼物
//
//
//
// 提示：
//
//
// 0 < grid.length <= 200
// 0 < grid[0].length <= 200
//
// Related Topics 动态规划
// 👍 74 👎 0

  // 执行用时：1 ms, 在所有 Java 提交中击败了99.98% 的用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int maxValue(int[][] grid) {
      final int rows = grid.length;
      final int cols = grid[0].length;
      // dp[x][j] 表示从 (i,j) 到  右下角 （rows-1,cols-1） 的最大价值
      // dp[i][j] =  max(dp[i+1][j+1], dp[i+1][j], dp[i][j+1]) + grid[i][j];
      // 礼物价值都是正数，可以省去 dp[i-1][j-1]
      // dp[i][j] =  max(dp[i+1][j], dp[i][j+1]) + grid[i][j];
      dp = new int[rows][cols];

      return dfs(grid, 0, 0);
    }

    int[][] dp;

    private int dfs(int[][] grid, int i, int j) {
      if (i >= grid.length || j >= grid[0].length) {
        return 0;
      }
      if (dp[i][j] == 0) {
        dp[i][j] = Math.max(dfs(grid, i + 1, j), dfs(grid, i, j + 1)) + grid[i][j];
      }
      return dp[i][j];
    }

  }
//leetcode submit region end(Prohibit modification and deletion)

}
