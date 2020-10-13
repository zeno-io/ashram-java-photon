package xyz.flysium.photon.algorithm.array.dimensional.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * 54. 螺旋矩阵
 * <p>
 * https://leetcode-cn.com/problems/spiral-matrix/
 *
 * @author zeno
 */
public class T0054_SpiralMatrix {

  // 给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。
  static class Solution {

    public List<Integer> spiralOrder(int[][] matrix) {
      if (matrix.length == 0) {
        return new ArrayList<>(0);
      }
      List<Integer> ans = new ArrayList<>();
      clockwiseSpiral(matrix, (x, y) -> {
        ans.add(matrix[x][y]);
      });
      return ans;
    }

    // 顺时针螺旋
    private void clockwiseSpiral(int[][] matrix, BiConsumer<Integer, Integer> action) {
      final int m = matrix.length;
      final int n = matrix[0].length;
      final int size = m * n;
      // position
      int x = 0, y = 0;
      // size of elements
      int i = 0;
      // right = 1, down = 2, left = 3, up = 4
      int direction = 1;
      int left = -1, right = n, up = 0, down = m + 1;
      // turn direction
      boolean turn = true;
      int turnTime = 0;
      // end while reach size
      while (i < size) {
        action.accept(x, y);
        // turn direction if available
        turn = true;
        turnTime = 0;
        while (turn && turnTime < 2) {
          switch (direction) {
            // right
            case 1:
              turn = y + 1 >= right;
              if (turn) {
                // turn to down
                direction = 2;
                down--;
              } else {
                y++;
              }
              break;
            // down
            case 2:
              turn = x + 1 >= down;
              if (turn) {
                // turn to left
                direction = 3;
                left++;
              } else {
                x++;
              }
              break;
            // left
            case 3:
              turn = y <= left;
              if (turn) {
                // turn to up
                direction = 4;
                up++;
              } else {
                y--;
              }
              break;
            // up
            case 4:
              turn = x <= up;
              if (turn) {
                // turn to right
                direction = 1;
                right--;
              } else {
                x--;
              }
              break;
            default:
              break;
          }
          turnTime++;
        }
        i++;
      }
    }

  }

}
