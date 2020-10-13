package xyz.flysium.photon.algorithm.queueandstack.search;

import java.util.HashMap;
import java.util.Map;

/**
 * 279. 完全平方数
 * <p>
 * https://leetcode-cn.com/problems/perfect-squares/
 *
 * @author zeno
 */
public interface T0279_PerfectSquares_1 {

  // 给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。
  // 示例 1:
  //
  // 输入: n = 12
  // 输出: 3
  // 解释: 12 = 4 + 4 + 4.
  //
  // 示例 2:
  //
  // 输入: n = 13
  // 输出: 2
  // 解释: 13 = 4 + 9.
  //

  // 792 ms DFS
  class Solution {

    public int numSquares(int n) {
      return dfs(n, new HashMap<>());
    }

    private int dfs(int n, Map<Integer, Integer> hash) {
      if (hash.containsKey(n)) {
        return hash.get(n);
      }
      if (n == 0) {
        return 0;
      }
      int count = Integer.MAX_VALUE;
      for (int x = 1; x * x <= n; x++) {
        int next = n - x * x;
        count = Math.min(count, dfs(next, hash) + 1);
      }
      hash.put(n, count);
      return count;
    }

  }


}
