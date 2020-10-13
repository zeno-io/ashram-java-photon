package xyz.flysium.photon.algorithm.hash.keys;

import java.util.HashSet;

/**
 * 36. 有效的数独
 * <p>
 * https://leetcode-cn.com/problems/valid-sudoku/
 *
 * @author zeno
 */
public interface T0036_ValidSudoku_1 {

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

  // 3ms 55.36%
  class Solution {

    public boolean isValidSudoku(char[][] board) {
      HashSet<Character> s = new HashSet<>(16);
      for (int i = 0; i < 9; i++) {
        if (!s.isEmpty()) {
          s.clear();
        }
        //  数字 1-9 在每一行只能出现一次。
        for (int j = 0; j < 9; j++) {
          char c = board[i][j];
          if (c != '.' && !s.add(c)) {
            return false;
          }
        }
        s.clear();
        // 数字 1-9 在每一列只能出现一次。
        for (int k = 0; k < 9; k++) {
          char c = board[k][i];
          if (c != '.' && !s.add(c)) {
            return false;
          }
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
      HashSet<Character> s = new HashSet<>(16);
      for (int i = rowStart - 1; i < rowStart + 2; i++) {
        for (int j = colStart - 1; j < colStart + 2; j++) {
          char c = board[i][j];
          if (c != '.' && !s.add(c)) {
            return true;
          }
        }
      }
      return false;
    }

  }

}
