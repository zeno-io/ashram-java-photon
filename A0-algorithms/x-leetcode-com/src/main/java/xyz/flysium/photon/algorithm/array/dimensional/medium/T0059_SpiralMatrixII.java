package xyz.flysium.photon.algorithm.array.dimensional.medium;

import java.util.function.BiConsumer;

/**
 * 59、螺旋矩阵 II
 * <p>
 * https://leetcode-cn.com/problems/spiral-matrix-ii/
 *
 * @author zeno
 */
public class T0059_SpiralMatrixII {

  // 给定一个正整数 n，生成一个包含 1 到 n^2 所有元素，且元素按顺时针顺序螺旋排列的正方形矩阵。
  static class Solution {

    public int[][] generateMatrix(int n) {
      int[][] matrix = new int[n][n];
      final int size = n * n;
      for (int i = 0; i < n; i++) {
        matrix[i] = new int[n];
      }
      clockwiseSpiral(matrix, (arr, i) -> {
        matrix[arr[0]][arr[1]] = (i + 1);
      });
      return matrix;
    }

    // 顺时针螺旋
    private void clockwiseSpiral(int[][] matrix, BiConsumer<int[], Integer> action) {
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

      int[] position = new int[2];
      // end while reach size
      while (i < size) {
        position[0] = x;
        position[1] = y;
        action.accept(position, i);
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
