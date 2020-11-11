package xyz.flysium.photon.algorithm.queueandstack.search.basic;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 542. 01 矩阵
 * <p>
 * https://leetcode-cn.com/problems/01-matrix/
 *
 * @author zeno
 */
public interface U0542_01Matrix {

  // 给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离。
  //
  // 两个相邻元素间的距离为 1 。

  //    给定矩阵的元素个数不超过 10000。
  //    给定矩阵中至少有一个元素是 0。
  //    矩阵中的元素只在四个方向上相邻: 上、下、左、右。

  // 17 ms  54.82%
  // BFS
  class Solution {

    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int[][] updateMatrix(int[][] matrix) {
      final int rows = matrix.length;
      final int cols = matrix[0].length;
      int[][] ans = new int[rows][cols];
      boolean[][] visited = new boolean[rows][cols];
      Queue<int[]> queue = new LinkedList<>();

      for (int x = 0; x < rows; x++) {
        for (int y = 0; y < cols; y++) {
          if (matrix[x][y] == 0) {
            queue.offer(new int[]{x, y});
            visited[x][y] = true;
          }
        }
      }
      while (!queue.isEmpty()) {
        int sz = queue.size();
        for (int i = 0; i < sz; i++) {
          int[] v = queue.poll();
          int x0 = v[0];
          int y0 = v[1];
          for (int[] direction : DIRECTIONS) {
            int x1 = x0 + direction[0];
            int y1 = y0 + direction[1];
            if (x1 < 0 || y1 < 0 || x1 >= rows || y1 >= cols) {
              continue;
            }
            if (visited[x1][y1]) {
              continue;
            }
            ans[x1][y1] = ans[x0][y0] + 1;
            queue.offer(new int[]{x1, y1});
            visited[x1][y1] = true;
          }
        }
      }
      return ans;
    }

  }

}
