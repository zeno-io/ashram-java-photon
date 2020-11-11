package xyz.flysium.photon.algorithm.queueandstack.search.basic;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 279. 完全平方数
 * <p>
 * https://leetcode-cn.com/problems/perfect-squares/
 *
 * @author zeno
 */
public interface T0279_PerfectSquares {

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

  // 45 ms BFS
  class Solution {

    public int numSquares(int n) {
      Deque<Integer> queue = new LinkedList<>();
      Set<Integer> visited = new HashSet<>();
      int depth = 1;

      queue.offerLast(n);
      while (!queue.isEmpty()) {
        int sz = queue.size();
        for (int x = 0; x < sz; x++) {
          Integer e = queue.pollFirst();
          for (int i = 1; i * i <= e; i++) {
            int next = e - i * i;
            if (next == 0) {
              return depth;
            }
            if (!visited.contains(next)) {
              visited.add(next);
              queue.offerLast(next);
            }
          }
        }
        depth++;
      }

      return depth;
    }

  }


}
