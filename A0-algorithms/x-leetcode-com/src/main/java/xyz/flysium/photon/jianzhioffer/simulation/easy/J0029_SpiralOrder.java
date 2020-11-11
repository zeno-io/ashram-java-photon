package xyz.flysium.photon.jianzhioffer.simulation.easy;

import java.util.Arrays;
import xyz.flysium.photon.ArraySupport;

/**
 * 剑指 Offer 29. 顺时针打印矩阵
 * <p>
 * https://leetcode-cn.com/problems/shun-shi-zhen-da-yin-ju-zhen-lcof/
 *
 * @author zeno
 */
public class J0029_SpiralOrder {

//输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
//
//
//
// 示例 1：
//
// 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
//输出：[1,2,3,6,9,8,7,4,5]
//
//
// 示例 2：
//
// 输入：matrix =[[1,2,3,4],[5,6,7,8],[9,10,11,12]]
//输出：[1,2,3,4,8,12,11,10,9,5,6,7]
//
//
//
//
// 限制：
//
//
// 0 <= matrix.length <= 100
// 0 <= matrix[i].length <= 100
//
//
// 注意：本题与主站 54 题相同：https://leetcode-cn.com/problems/spiral-matrix/
// Related Topics 数组
// 👍 146 👎 0


  public static void main(String[] args) {
    Solution solution = new J0029_SpiralOrder().new Solution();
    System.out.println(Arrays.toString(
      solution.spiralOrder(ArraySupport.newTwoDimensionalArray("[[1,2,3],[4,5,6],[7,8,9]]"))));
    System.out.println(Arrays.toString(
      solution
        .spiralOrder(ArraySupport.newTwoDimensionalArray("[[1,2,3,4],[5,6,7,8],[9,10,11,12]]"))));
  }

  // 执行耗时:1 ms,击败了96.77% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int[] spiralOrder(int[][] matrix) {
      if (matrix.length == 0) {
        return new int[0];
      }
      final int rows = matrix.length;
      final int cols = matrix[0].length;
      final int sz = rows * cols;
      int right = cols - 1;
      int left = 0;
      int up = 1;
      int down = rows - 1;
      // right = 1, down = 2, left = 3, up = 4
      int k = 1;
      int i = 0;
      int j = 0;
      int cnt = 0;
      int[] ans = new int[sz];
      while (cnt < sz) {
        ans[cnt++] = matrix[i][j];
        boolean f = true;
        while (f) {
          switch (k) {
            // right
            case 1:
              if (j == right) {
                // turn to down
                k = 2;
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
                k = 3;
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
                k = 4;
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
                k = 1;
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
      return ans;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
