package xyz.flysium.photon.algorithm.hash.basic.mixed;

/**
 * TODO description
 *
 * @author zeno
 */
public interface T0311_SparseMatrixMultiplication_1 {
  // 给你两个 稀疏矩阵 A 和 B，请你返回 AB 的结果。你可以默认 A 的列数等于 B 的行数。
  //
  //请仔细阅读下面的示例。
  //
  //
  //
  //示例：
  //
  //输入：
  //
  //A = [
  //  [ 1, 0, 0],
  //  [-1, 0, 3]
  //]
  //
  //B = [
  //  [ 7, 0, 0 ],
  //  [ 0, 0, 0 ],
  //  [ 0, 0, 1 ]
  //]
  //
  //输出：
  //
  //     |  1 0 0 |   | 7 0 0 |   |  7 0 0 |
  //AB = | -1 0 3 | x | 0 0 0 | = | -7 0 3 |
  //                  | 0 2 1 |
  //

  // 3ms 66.89%  M * N * K
  class Solution {

    public int[][] multiply(int[][] A, int[][] B) {
      if (A.length == 0) {
        return new int[][]{};
      }
      // 你可以默认 A 的列数等于 B 的行数。
      final int rowsA = A.length;
      final int colsA = A[0].length;
      final int rowsB = B.length; // colsA = rowsB
      final int colsB = B[0].length;

      int[][] ans = new int[rowsA][colsB];
      for (int i = 0; i < rowsA; i++) {
        for (int j = 0; j < colsB; j++) {
          int multiply = 0;
          for (int k = 0, l = 0; k < colsA; k++, l++) {
            multiply += A[i][k] * B[l][j];
          }
          ans[i][j] = multiply;
        }
      }
      return ans;
    }

  }

}
