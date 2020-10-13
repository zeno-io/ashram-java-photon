package xyz.flysium.photon.algorithm.array.dimensional.medium;

/**
 * 73、矩阵置零
 * <p>
 * https://leetcode-cn.com/problems/set-matrix-zeroes/
 *
 * @author zeno
 */
public class T0073_SetMatrixZeroes_1 {

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
      int[] zeroInRows = new int[rows];
      int[] zeroInCols = new int[cols];
      for (int x = 0; x < rows; x++) {
        for (int y = 0; y < cols; y++) {
          if (matrix[x][y] == 0) {
            zeroInCols[y] = 1;
            zeroInRows[x] = 1;
          }
        }
      }
      for (int x = 0; x < rows; x++) {
        if (zeroInRows[x] == 1) {
          for (int i = 0; i < cols; i++) {
            matrix[x][i] = 0;
          }
        }
      }
      for (int y = 0; y < cols; y++) {
        if (zeroInCols[y] == 1) {
          for (int i = 0; i < rows; i++) {
            matrix[i][y] = 0;
          }
        }
      }
    }

  }

}
