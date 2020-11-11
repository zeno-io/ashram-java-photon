package xyz.flysium.photon.interviewgolden.medium;

import xyz.flysium.photon.algorithm.array.dimensional.medium.T0048_RotateImage;

/**
 * 面试题 01.07. 旋转矩阵
 * <p>
 * https://leetcode-cn.com/problems/rotate-matrix-lcci/
 *
 * @author zeno
 * @see {@link T0048_RotateImage}
 */
public interface M0107_RotateMatrixLcci {

  // 给你一幅由 N × N 矩阵表示的图像，其中每个像素的大小为 4 字节。请你设计一种算法，将图像旋转 90 度。
  // 不占用额外内存空间能否做到？

  class Solution {

    public void rotate(int[][] matrix) {
      if (matrix.length == 0) {
        return;
      }
      int n = matrix.length;
      for (int x = 0; x < n >> 1; x++) {
        for (int y = x; y < n - 1 - x; y++) {
          swap(matrix, x, y, n - 1 - y, n - 1 - x);
        }
        for (int i = x; i < n >> 1; i++) {
          swap(matrix, i, n - 1 - x, n - 1 - i, n - 1 - x);
        }
        for (int y = x; y < n - 1 - x; y++) {
          swap(matrix, x, y, n - 1 - x, y);
        }
        for (int y = x + 1; y < n - 1 - x; y++) {
          swap(matrix, x, y, y, x);
        }
        for (int y = x + 1; y < n >> 1; y++) {
          swap(matrix, x, y, x, n - 1 - y);
        }
      }
    }

    private void swap(int[][] arr, int x1, int y1, int x2, int y2) {
      if (x1 == x2 && y1 == y2) {
        return;
      }
      arr[x1][y1] = arr[x1][y1] ^ arr[x2][y2];
      arr[x2][y2] = arr[x1][y1] ^ arr[x2][y2];
      arr[x1][y1] = arr[x1][y1] ^ arr[x2][y2];
    }

  }

}
