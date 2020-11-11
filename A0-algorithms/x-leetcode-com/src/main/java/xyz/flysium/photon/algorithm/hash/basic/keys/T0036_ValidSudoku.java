package xyz.flysium.photon.algorithm.hash.basic.keys;

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
      boolean[][] rows = new boolean[9][10];
      boolean[][] cols = new boolean[9][10];
      boolean[][] region = new boolean[9][10];
      for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
          char c = board[i][j];
          if (c == '.') {
            continue;
          }
          //  数字 1-9 在每一行只能出现一次。
          if (rows[i][c - '0']) {
            return false;
          }
          rows[i][c - '0'] = true;
          //  数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
          int regionId = (i / 3) * 3 + j / 3;
          if (region[regionId][c - '0']) {
            return false;
          }
          region[regionId][c - '0'] = true;
        }
        // 数字 1-9 在每一列只能出现一次。
        for (int k = 0; k < 9; k++) {
          char c = board[k][i];
          if (c == '.') {
            continue;
          }
          if (cols[i][c - '0']) {
            return false;
          }
          cols[i][c - '0'] = true;
        }
      }
      return true;
    }

  }

}
