package xyz.flysium.photon.algorithm.queueandstack.search;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 542. 01 矩阵
 * <p>
 * https://leetcode-cn.com/problems/01-matrix/
 *
 * @author zeno
 */
public interface U0542_01Matrix_1 {

  // 给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离。
  //
  // 两个相邻元素间的距离为 1 。

  //    给定矩阵的元素个数不超过 10000。
  //    给定矩阵中至少有一个元素是 0。
  //    矩阵中的元素只在四个方向上相邻: 上、下、左、右。

  // 37 ms  9.31%
  // BFS
  class Solution {

    private static final List<int[]> DIRECTIONS = Arrays.asList(
      new int[]{0, -1},// up
      new int[]{0, 1}, // down
      new int[]{-1, 0}, // left
      new int[]{1, 0} // right
    );

    public int[][] updateMatrix(int[][] matrix) {
      if (matrix.length == 0) {
        return matrix;
      }
      int[][] visited = new int[matrix.length][matrix[0].length];
      for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[i].length; j++) {
          matrix[i][j] = bfs(matrix, i, j, visited);
          visited[i][j] = 1;
        }
      }
      return matrix;
    }

    private int bfs(int[][] matrix, int x, int y, int[][] visited) {
      if (matrix[x][y] == 0) {
        return 0;
      }
      int depth = 0;
      Queue<int[]> queue = new LinkedList<>();
      queue.offer(new int[]{x, y});

      while (!queue.isEmpty()) {
        int sz = queue.size();
        for (int i = 0; i < sz; i++) {
          int[] v = queue.poll();
          int x0 = v[0];
          int y0 = v[1];
          if (matrix[x0][y0] == 0) {
            return depth;
          }
          for (int[] d : DIRECTIONS) {
            int x1 = x0 + d[0];
            int y1 = y0 + d[1];
            if (x1 < 0 || y1 < 0 || x1 >= matrix.length || y1 >= matrix[0].length) {
              continue;
            }
            queue.offer(new int[]{x1, y1});
          }
        }
        depth++;
      }

      return depth;
    }

  }

}
