package xyz.flysium.photon.jianzhioffer.search.medium;

/**
 * 剑指 Offer 13. 机器人的运动范围
 * <p>
 * https://leetcode-cn.com/problems/ji-qi-ren-de-yun-dong-fan-wei-lcof/
 *
 * @author zeno
 */
public class J0013_MovingCount_2 {

//地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。一个机器人从坐标 [0, 0] 的格子开始移动，它每次可以向左、右、上、下移动一
//格（不能移动到方格外），也不能进入行坐标和列坐标的数位之和大于k的格子。例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。但
//它不能进入方格 [35, 38]，因为3+5+3+8=19。请问该机器人能够到达多少个格子？
//
//
//
// 示例 1：
//
// 输入：m = 2, n = 3, k = 1
//输出：3
//
//
// 示例 2：
//
// 输入：m = 3, n = 1, k = 0
//输出：1
//
//
// 提示：
//
//
// 1 <= n,m <= 100
// 0 <= k <= 20
//
// 👍 169 👎 0


  public static void main(String[] args) {
    Solution solution = new J0013_MovingCount_2().new Solution();
//    solution.movingCount(38, 15, 9); // 135
    solution.movingCount(36, 11, 15); // 362
  }

  // 	执行耗时:4 ms,击败了23.73% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int movingCount(int m, int n, int k) {
      if (k == 0) {
        return 1;
      }
//      if (trans(m - 1) + trans(n - 1) <= k) {
//        return m * n;
//      }
      int[][] visited = new int[m][n];
      dfs(m, n, k, 0, 0, visited);

      int count = 0;
      for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
         /* if (visited[i][j] == -1) {
            break;
          }*/
          if (visited[i][j] == 1) {
            count++;
          }
        }
      }
      return count;
    }

    private void dfs(int m, int n, int k, int x, int y, int[][] visited) {
      if (x < 0 || y < 0 || x >= m || y >= n) {
        return;
      }
      if (visited[x][y] != 0) {
        return;
      }
      int tx = trans(x);
      int ty = trans(y);
      if (tx + ty > k) {
        visited[x][y] = -1;
        return;
      }
      visited[x][y] = 1;
      dfs(m, n, k, x, y - 1, visited);
      dfs(m, n, k, x, y + 1, visited);
      dfs(m, n, k, x - 1, y, visited);
      dfs(m, n, k, x + 1, y, visited);
    }

    private int trans(int x) {
      if (x < 10) {
        return x;
      }
      String cs = String.valueOf(x);
      int num = 0;
      for (int i = 0; i < cs.length(); i++) {
        num += (cs.charAt(i) - '0');
      }
      return num;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
