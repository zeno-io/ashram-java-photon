package xyz.flysium.photon.algorithm.array.dimensional.medium;

/**
 * 73、矩阵置零
 * <p>
 * https://leetcode-cn.com/problems/set-matrix-zeroes/
 *
 * @author zeno
 */
public class T0073_SetMatrixZeroes {

  //  给定一个 m x n 的矩阵，如果一个元素为 0，则将其所在行和列的所有元素都设为 0。请使用原地算法。
  //  一个直接的解决方案是使用  O(mn) 的额外空间，但这并不是一个好的解决方案。
  //  一个简单的改进方案是使用 O(m + n) 的额外空间，但这仍然不是最好的解决方案。
  //  你能想出一个常数空间的解决方案吗？
  static class Solution {

    public void setZeroes(int[][] matrix) {
      if (matrix.length == 0) {
        return;
      }
      final int rows = matrix.length;
      final int cols = matrix[0].length;
      boolean firstRowHasZero = false, firstColHasZero = false;
      for (int y = 0; y < cols; y++) {
        if (matrix[0][y] == 0) {
          firstRowHasZero = true;
          break;
        }
      }
      for (int x = 0; x < rows; x++) {
        if (matrix[x][0] == 0) {
          firstColHasZero = true;
          break;
        }
      }
      for (int x = 1; x < rows; x++) {
        for (int y = 1; y < cols; y++) {
          if (matrix[x][y] == 0) {
            matrix[0][y] = 0;
            matrix[x][0] = 0;
          }
        }
      }
      for (int x = 1; x < rows; x++) {
        if (matrix[x][0] == 0) {
          for (int i = 0; i < cols; i++) {
            matrix[x][i] = 0;
          }
        }
      }
      for (int y = 1; y < cols; y++) {
        if (matrix[0][y] == 0) {
          for (int i = 0; i < rows; i++) {
            matrix[i][y] = 0;
          }
        }
      }
      if (firstRowHasZero) {
        for (int y = 0; y < cols; y++) {
          matrix[0][y] = 0;
        }
      }
      if (firstColHasZero) {
        for (int x = 0; x < rows; x++) {
          matrix[x][0] = 0;
        }
      }
    }

  }

}
