package xyz.flysium.photon.algorithm.array.dimensional.easy;

/**
 * 566、重塑矩阵
 * <p>
 * https://leetcode-cn.com/problems/reshape-the-matrix/
 *
 * @author zeno
 */
public class T0566_ReshapeTheMatrix {

  static class Solution {

    public int[][] matrixReshape(int[][] nums, int r, int c) {
      if (nums.length == 0) {
        return nums;
      }
      final int rows = nums.length;
      final int cols = nums[0].length;
      final int size = rows * cols;
      if (size != r * c) {
        return nums;
      }
      int[][] ans = new int[r][c];
      for (int i = 0; i < r; i++) {
        ans[i] = new int[c];
      }
      int x = 0, y = 0;
      for (int[] num : nums) {
        for (int j = 0; j < cols; j++) {
          ans[x][y] = num[j];
          if (y + 1 >= c) {
            y = 0;
            x++;
          } else {
            y++;
          }
        }
      }
      return ans;
    }

  }

}
