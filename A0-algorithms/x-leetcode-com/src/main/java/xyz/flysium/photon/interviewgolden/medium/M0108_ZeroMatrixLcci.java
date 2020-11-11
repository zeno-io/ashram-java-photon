package xyz.flysium.photon.interviewgolden.medium;

import xyz.flysium.photon.algorithm.array.dimensional.medium.T0073_SetMatrixZeroes;

/**
 * 面试题 01.08. 零矩阵
 * <p>
 * https://leetcode-cn.com/problems/zero-matrix-lcci/
 *
 * @author zeno
 * @see {@link T0073_SetMatrixZeroes}
 */
public interface M0108_ZeroMatrixLcci {

  // 编写一种算法，若M × N矩阵中某个元素为0，则将其所在的行与列清零。

  class Solution {

    public void setZeroes(int[][] matrix) {
      if (matrix.length == 0) {
        return;
      }
      final int rows = matrix.length;
      final int cols = matrix[0].length;
      boolean firstRowHasZero = false, firstColHasZero = false;
      for (int j = 0; j < cols; j++) {
        if (matrix[0][j] == 0) {
          firstRowHasZero = true;
          break;
        }
      }
      for (int i = 0; i < rows; i++) {
        if (matrix[i][0] == 0) {
          firstColHasZero = true;
          break;
        }
      }
      for (int i = 1; i < rows; i++) {
        for (int j = 1; j < cols; j++) {
          if (matrix[i][j] == 0) {
            matrix[0][j] = 0;
            matrix[i][0] = 0;
          }
        }
      }
      for (int j = 1; j < cols; j++) {
        if (matrix[0][j] == 0) {
          for (int i = 0; i < rows; i++) {
            matrix[i][j] = 0;
          }
        }
      }
      for (int i = 1; i < rows; i++) {
        if (matrix[i][0] == 0) {
          for (int j = 0; j < cols; j++) {
            matrix[i][j] = 0;
          }
        }
      }
      if (firstRowHasZero) {
        for (int j = 0; j < cols; j++) {
          matrix[0][j] = 0;
        }
      }
      if (firstColHasZero) {
        for (int i = 0; i < rows; i++) {
          matrix[i][0] = 0;
        }
      }

    }

  }

}
