package xyz.flysium.photon.algorithm.array.dimensional.medium;

/**
 * 419. 甲板上的战舰
 * <p>
 * https://leetcode-cn.com/problems/battleships-in-a-board/
 *
 * @author zeno
 */
public class T0419_BattleshipsInABoard {

  static class Solution {

    // 因为战舰并不会相交或相撞，两艘战舰之间至少有一个水平或垂直的空位分隔 - 即没有相邻的战舰。
    // 只需要统计战舰的头部个数即可。战舰的头部所在的格子为’X’且其上面和左边的格子都为’.’。
    public int countBattleships(char[][] board) {
      int count = 0;
      for (int m = 0; m < board.length; m++) {
        for (int n = 0; n < board[m].length; n++) {
          if (board[m][n] == 'X'
            && (n == 0 || board[m][n - 1] == '.')
            && (m == 0 || board[m - 1][n] == '.')) {
            count++;
          }
        }
      }
      return count;
    }

  }

}
