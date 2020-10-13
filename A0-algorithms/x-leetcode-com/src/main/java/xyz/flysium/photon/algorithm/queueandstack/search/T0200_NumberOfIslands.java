package xyz.flysium.photon.algorithm.queueandstack.search;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 200. 岛屿数量
 * <p>
 * https://leetcode-cn.com/problems/number-of-islands/
 *
 * @author zeno
 */
public interface T0200_NumberOfIslands {

  // 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
  //
  //岛屿总是被水包围，并且每座岛屿只能由水平方向或竖直方向上相邻的陆地连接形成。
  //
  //此外，你可以假设该网格的四条边均被水包围。

  // 广度优先搜索 BFS: 为了求出岛屿的数量，我们可以扫描整个二维网格。
  // 如果一个位置为 1，则将其加入队列，开始进行广度优先搜索。
  // 在广度优先搜索的过程中，每个搜索到的 1 都会被重新标记为 0。直到队列为空，搜索结束。

  // BFS
  class Solution {

    private static final char LAND = '1';
    private static final char WATER = '0';
    private static final int[] DPX = new int[]{0, -1, 1, 0};
    private static final int[] DPY = new int[]{-1, 0, 0, 1};

    public int numIslands(char[][] grid) {
      if (grid.length == 0) {
        return 0;
      }
      final int rows = grid.length;
      final int cols = grid[0].length;
      int numOfIslands = 0;

      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[i].length; j++) {
          if (grid[i][j] == LAND) {
            numOfIslands++;
            bfs(grid, i, j, rows, cols);
          }
        }
      }
      return numOfIslands;
    }

    private void bfs(char[][] grid, int i, int j, final int rows, final int cols) {
      Queue<int[]> queue = new LinkedList<>();
      queue.offer(new int[]{i, j});

      while (!queue.isEmpty()) {
        int sz = queue.size();
        for (int g = 0; g < sz; g++) {
          int[] e = queue.poll();
          int x0 = e[0];
          int y0 = e[1];
          grid[x0][y0] = WATER;
          for (int k = 0; k < 4; k++) {
            int x = x0 + DPX[k];
            int y = y0 + DPY[k];
            if (x < 0 || x >= rows
              || y < 0 || y >= cols) {
              continue;
            }
            if (grid[x][y] == WATER) {
              continue;
            }
            grid[x][y] = WATER;
            queue.offer(new int[]{x, y});
          }
        }
      }
    }
  }

}
