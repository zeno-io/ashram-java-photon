package xyz.flysium.photon.algorithm.queueandstack.search;

/**
 * 286. 墙与门
 * <p>
 * https://leetcode-cn.com/problems/walls-and-gates/
 *
 * @author zeno
 */
public interface T0286_WallsAndGates_1 {

  // 你被给定一个 m × n 的二维网格，网格中有以下三种可能的初始化值：
  //
  //    -1 表示墙或是障碍物
  //    0 表示一扇门
  //    INF 无限表示一个空的房间。然后，我们用 2^31 - 1 = 2147483647 代表 INF。你可以认为通往门的距离总是小于 2147483647 的。
  //
  // 你要给每个空房间位上填上该房间到 最近 门的距离，如果无法到达门，则填 INF 即可。
  //
  // 示例：
  //
  //给定二维网格：
  //
  //INF  -1  0  INF
  //INF INF INF  -1
  //INF  -1 INF  -1
  //  0  -1 INF INF
  //
  //运行完你的函数后，该网格应该变成：
  //
  //  3  -1   0   1
  //  2   2   1  -1
  //  1  -1   2  -1
  //  0  -1   3   4

  // 深度优先搜索 DFS: 找出所有的门，从门出发，更新所有的房间到门的距离
  // 6 ms DFS
  class Solution {

    private static final int EMPTY_ROOM = Integer.MAX_VALUE;
    private static final int GATE = 0;
    private static final int WALL = -1;

    private static final int[] DPX = new int[]{0, -1, 1, 0};
    private static final int[] DPY = new int[]{-1, 0, 0, 1};

    public void wallsAndGates(int[][] rooms) {
      if (rooms.length == 0) {
        return;
      }
      final int rows = rooms.length;
      final int cols = rooms[0].length;

      for (int i = 0; i < rooms.length; i++) {
        for (int j = 0; j < rooms[i].length; j++) {
          if (rooms[i][j] == GATE) {
            dfs(rooms, i, j, 0, rows, cols);
          }
        }
      }
    }

    // DFS
    private void dfs(int[][] rooms, int x0, int y0, int distance, final int rows, final int cols) {
      for (int k = 0; k < 4; k++) {
        int x = x0 + DPX[k];
        int y = y0 + DPY[k];
        if (x < 0 || x >= rows
          || y < 0 || y >= cols) {
          continue;
        }
        if (rooms[x][y] == GATE || rooms[x0][y0] == WALL) {
          continue;
        }
        if (rooms[x][y] <= distance + 1) {
          continue;
        }
        rooms[x][y] = distance + 1;
        dfs(rooms, x, y, distance + 1, rows, cols);
      }
    }

  }

}
