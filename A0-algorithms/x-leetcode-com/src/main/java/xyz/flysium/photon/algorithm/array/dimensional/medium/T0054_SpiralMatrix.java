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
      final int rows = matrix.length;
      final int cols = matrix[0].length;
      final int sz = rows * cols;
      int right = cols - 1;
      int left = 0;
      int up = 1; // 开始时候消耗了第一行
      int down = rows - 1;
      // right = 1, down = 2, left = 3, up = 4
      int direction = 1;
      int i = 0;
      int j = 0;
      int cnt = 0;
      while (cnt < sz) {
        action.accept(i, j);
        cnt++;
        boolean f = true;
        while (f) {
          switch (direction) {
            // right
            case 1:
              if (j == right) {
                // turn to down
                direction = 2;
                right--;
                f = true;
                continue;
              }
              j++;
              f = false;
              break;
            // down
            case 2:
              if (i == down) {
                // turn to left
                down--;
                direction = 3;
                f = true;
                continue;
              }
              i++;
              f = false;
              break;
            // left
            case 3:
              if (j == left) {
                // turn to up
                direction = 4;
                left++;
                f = true;
                continue;
              }
              j--;
              f = false;
              break;
            // up
            case 4:
              if (i == up) {
                // turn to right
                direction = 1;
                up++;
                f = true;
                continue;
              }
              i--;
              f = false;
              break;
            default:
              break;
          }
        }
      }
    }

  }

}
