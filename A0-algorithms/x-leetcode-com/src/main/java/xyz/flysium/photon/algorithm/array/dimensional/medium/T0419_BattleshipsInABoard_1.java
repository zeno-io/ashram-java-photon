package xyz.flysium.photon.algorithm.array.dimensional.medium;

/**
 * 419. 甲板上的战舰
 * <p>
 * https://leetcode-cn.com/problems/battleships-in-a-board/
 *
 * @author zeno
 */
public class T0419_BattleshipsInABoard_1 {

  static class Solution {

    // 3ms
    public int countBattleships(char[][] board) {
      final int length = board[0].length;
      int count = 0;
      int[] vertical = new int[length];
      for (int m = 0; m < board.length; m++) {
        int left = -1;
        int right = 0;
        while (right < length) {
          if (board[m][right] == 'X') {
            if (notFoundBattleshipOnVertical(board, vertical, m, right)) {
              if (right == 0 || board[m][right - 1] == '.') {
                left = right;
              }
            }
            vertical[right]++;
          } else {
            if (isNewBattleship(board, vertical, m, right - 1)) {
              count++;
            }
          }
          right++;
        }
        if (isNewBattleship(board, vertical, m, length - 1)) {
          count++;
        }
      }
      return count;
    }

    private boolean isNewBattleship(char[][] board, int[] vertical, int x, int y) {
      return y >= 0 && board[x][y] == 'X' && notFoundBattleshipOnVertical(board, vertical, x, y);
    }

    private boolean notFoundBattleshipOnVertical(char[][] board, int[] vertical, int x, int y) {
      if (vertical[y] == 0) {
        return true;
      }
      return x <= 0 || board[x - 1][y] != 'X';
    }

  }

}
