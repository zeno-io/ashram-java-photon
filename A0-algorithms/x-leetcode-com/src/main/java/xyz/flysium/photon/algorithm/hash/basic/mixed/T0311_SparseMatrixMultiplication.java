package xyz.flysium.photon.algorithm.hash.basic.mixed;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * TODO description
 *
 * @author zeno
 */
public interface T0311_SparseMatrixMultiplication {
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

  // 1ms 84.46%   M * N
  class Solution {

    public int[][] multiply(int[][] A, int[][] B) {
      if (A.length == 0) {
        return new int[][]{};
      }
      // 你可以默认 A 的列数等于 B 的行数。
      final int rowsA = A.length;
      final int colsB = B[0].length;

      Map<Integer, Map<Integer, Integer>> hashA = new HashMap<>(rowsA, 1);
      for (int i = 0; i < rowsA; i++) {
        HashMap<Integer, Integer> row = new HashMap<>(A[i].length, 1);
        for (int j = 0; j < A[i].length; j++) {
          if (A[i][j] != 0) {
            row.put(j, A[i][j]);
          }
        }
        if (!row.isEmpty()) {
          hashA.put(i, row);
        }
      }
      Map<Integer, Map<Integer, Integer>> hashB = new HashMap<>(colsB, 1);
      for (int j = 0; j < colsB; j++) {
        HashMap<Integer, Integer> col = new HashMap<>(B.length, 1);
        for (int i = 0; i < B.length; i++) {
          if (B[i][j] != 0) {
            col.put(i, B[i][j]);
          }
        }
        if (!col.isEmpty()) {
          hashB.put(j, col);
        }
      }

      int[][] ans = new int[rowsA][colsB];

      for (Map.Entry<Integer, Map<Integer, Integer>> ea : hashA.entrySet()) {
        int i = ea.getKey();
        Map<Integer, Integer> row = ea.getValue();

        for (Map.Entry<Integer, Map<Integer, Integer>> eb : hashB.entrySet()) {
          int j = eb.getKey();
          Map<Integer, Integer> col = eb.getValue();

          Set<Integer> s = new HashSet<>(col.keySet());
          s.retainAll(row.keySet());

          for (Integer idx : s) {
            ans[i][j] += row.get(idx) * col.get(idx);
          }
        }
      }
      return ans;
    }

  }

}
