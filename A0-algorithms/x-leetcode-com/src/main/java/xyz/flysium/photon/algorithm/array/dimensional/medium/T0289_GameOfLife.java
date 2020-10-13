package xyz.flysium.photon.algorithm.array.dimensional.medium;

/**
 * 289、生命游戏
 * <p>
 * https://leetcode-cn.com/problems/game-of-life/
 *
 * @author zeno
 */
public class T0289_GameOfLife {

  static class Solution {

    public void gameOfLife(int[][] board) {
      // positive -> size of alive cells around
      //    if size of alive cells around is 0, we set to 1 (because of <2 will dead, so not matters)
      // negative -> dead
      if (board.length == 0) {
        return;
      }
      final int rows = board.length;
      final int cols = board[0].length;
      int[] px = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
      int[] py = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

      for (int x = 0; x < rows; x++) {
        for (int y = 0; y < cols; y++) {
          int aliveCount = 0;
          for (int k = 0; k < 8; k++) {
            if (x + px[k] >= 0 && x + px[k] < rows && y + py[k] >= 0 && y + py[k] < cols) {
              int e = board[x + px[k]][y + py[k]];
              if (e > 0) {
                aliveCount++;
              }
            }
          }
          //    if size of alive cells around is 0, we set to 1 (because of <2 will dead, so not matters)
          if (aliveCount == 0) {
            aliveCount = 1;
          }
          if (board[x][y] > 0) {
            board[x][y] = aliveCount;
          } else {
            board[x][y] = -aliveCount;
          }
        }
      }
      for (int x = 0; x < rows; x++) {
        for (int y = 0; y < cols; y++) {
          // 如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
          // 如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
          // 如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
          // 如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
          if (board[x][y] == 2 || board[x][y] == 3 || board[x][y] == -3) {
            board[x][y] = 1;
          } else {
            board[x][y] = 0;
          }
        }
      }
    }

  }

}
