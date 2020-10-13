package xyz.flysium.photon.algorithm.hash.keys;

/**
 * 36. 有效的数独
 * <p>
 * https://leetcode-cn.com/problems/valid-sudoku/
 *
 * @author zeno
 */
public interface T0036_ValidSudoku {

  // 判断一个 9x9 的数独是否有效。只需要根据以下规则，验证已经填入的数字是否有效即可。
  //
  //    数字 1-9 在每一行只能出现一次。
  //    数字 1-9 在每一列只能出现一次。
  //    数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
  //

  //  一个有效的数独（部分已被填充）不一定是可解的。
  // 只需要根据以上规则，验证已经填入的数字是否有效即可。
  // 给定数独序列只包含数字 1-9 和字符 '.' 。
  // 给定数独永远是 9x9 形式的。
  //

  // 2ms 96.63%
  class Solution {

    public boolean isValidSudoku(char[][] board) {
      int[][] rows = new int[9][10];
      int[][] cols = new int[9][10];
      for (int i = 0; i < 9; i++) {
        //  数字 1-9 在每一行只能出现一次。
        for (int j = 0; j < 9; j++) {
          char c = board[i][j];
          if (c == '.') {
            continue;
          }
          if (rows[i][c - '0'] > 0) {
            return false;
          }
          rows[i][c - '0'] = 1;
        }
        // 数字 1-9 在每一列只能出现一次。
        for (int k = 0; k < 9; k++) {
          char c = board[k][i];
          if (c == '.') {
            continue;
          }
          if (cols[i][c - '0'] > 0) {
            return false;
          }
          cols[i][c - '0'] = 1;
        }
      }
      // 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
      if (isRegionInvalid(board, 1, 1)) {
        return false;
      }
      if (isRegionInvalid(board, 4, 1)) {
        return false;
      }
      if (isRegionInvalid(board, 7, 1)) {
        return false;
      }
      if (isRegionInvalid(board, 1, 4)) {
        return false;
      }
      if (isRegionInvalid(board, 4, 4)) {
        return false;
      }
      if (isRegionInvalid(board, 7, 4)) {
        return false;
      }
      if (isRegionInvalid(board, 1, 7)) {
        return false;
      }
      if (isRegionInvalid(board, 4, 7)) {
        return false;
      }
      if (isRegionInvalid(board, 7, 7)) {
        return false;
      }
      return true;
    }

    private boolean isRegionInvalid(char[][] board, int rowStart, int colStart) {
      int[] map = new int[10];
      for (int i = rowStart - 1; i < rowStart + 2; i++) {
        for (int j = colStart - 1; j < colStart + 2; j++) {
          char c = board[i][j];
          if (c == '.') {
            continue;
          }
          if (map[c - '0'] > 0) {
            return true;
          }
          map[c - '0'] = 1;
        }
      }
      return false;
    }

  }

}
