package xyz.flysium.photon.algorithm.array.dimensional.medium;

/**
 * 48、旋转图像
 * <p>
 * https://leetcode-cn.com/problems/rotate-image/
 *
 * @author zeno
 */
public class T0048_RotateImage {

  // 给定一个 n × n 的二维矩阵表示一个图像。
  // 将图像顺时针旋转 90 度。
  // 你必须在原地旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。
  static class Solution {

    // n x n
    public void rotate(int[][] matrix) {
      if (matrix.length == 0) {
        return;
      }
      final int n = matrix.length;
      for (int x = 0; x < n >> 1; x++) {
        //    (1) 将第一行和最后一列倒序交换。
        for (int y = x; y < n - 1 - x; y++) {
          swap(matrix, x, y, n - 1 - y, n - 1 - x);
        }
        //    (2) 将最后一列翻转。
        for (int i = x; i < n >> 1; i++) {
          swap(matrix, i, n - 1 - x, n - 1 - i, n - 1 - x);
        }
        //    (3) 交换第一行和最后一行前n-1个值。
        for (int y = x; y < n - 1 - x; y++) {
          swap(matrix, x, y, n - 1 - x, y);
        }
        //    (4) 将第一列和第一行中间的n-2个数倒序交换。
        for (int y = x + 1; y < n - 1 - x; y++) {
          swap(matrix, y, x, x, y);
        }
        //    (5) 将第一行中间n-2个数翻转。
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
