package xyz.flysium.photon.jianzhioffer.find.easy;

import java.util.Arrays;
import xyz.flysium.photon.ArraySupport;

/**
 * 剑指 Offer 04. 二维数组中的查找
 * <p>
 * https://leetcode-cn.com/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof/
 *
 * @author zeno
 */
public class J0004_FindNumberIn2DArray {

//在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，
//判断数组中是否含有该整数。
//
//
//
// 示例:
//
// 现有矩阵 matrix 如下：
//
// [
//  [1,   4,  7, 11, 15],
//  [2,   5,  8, 12, 19],
//  [3,   6,  9, 16, 22],
//  [10, 13, 14, 17, 24],
//  [18, 21, 23, 26, 30]
//]
//
//
// 给定 target = 5，返回 true。
//
// 给定 target = 20，返回 false。
//
//
//
// 限制：
//
// 0 <= n <= 1000
//
// 0 <= m <= 1000
//
//
//
// 注意：本题与主站 240 题相同：https://leetcode-cn.com/problems/search-a-2d-matrix-ii/
// Related Topics 数组 双指针
// 👍 163 👎 0


  public static void main(String[] args) {
    Solution solution = new J0004_FindNumberIn2DArray().new Solution();
    System.out.println(Arrays.binarySearch(new int[]{1, 2, 3, 4, 7, 9}, 5));
    System.out.println(Arrays.binarySearch(new int[]{1, 2, 4, 6, 7, 9}, 5));
    // false
    System.out.println(
      solution.findNumberIn2DArray(ArraySupport.newTwoDimensionalArray("[[1],[3],[5]]"),
        2));
    // true
    System.out.println(
      solution.findNumberIn2DArray(ArraySupport.newTwoDimensionalArray(
        "[[1,2,3,4,5],"
          + "[6,7,8,9,10],"
          + "[11,12,13,14,15],"
          + "[16,17,18,19,20],"
          + "[21,22,23,24,25]]"), 19));
    // true
    System.out.println(
      solution.findNumberIn2DArray(ArraySupport.newTwoDimensionalArray(
        "["
          + "[3,3,8,13,13,18],"
          + "[4,5,11,13,18,20],"
          + "[9,9,14,15,23,23],"
          + "[13,18,22,22,25,27],"
          + "[18,22,23,28,30,33],"
          + "[21,25,28,30,35,35],"
          + "[24,25,33,36,37,40]]"), 21));
    // true
    System.out.println(
      solution.findNumberIn2DArray(ArraySupport.newTwoDimensionalArray(
        "[[1,3,5,7,9],"
          + "[2,4,6,8,10],"
          + "[11,13,15,17,19],"
          + "[12,14,16,18,290],"
          + "[21,22,23,24,25]]"), 13));
  }

  // 执行耗时:0 ms,击败了100.00% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public boolean findNumberIn2DArray(int[][] matrix, int target) {
      if (matrix.length == 0 || matrix[0].length == 0) {
        return false;
      }
      final int row = matrix.length;
      final int col = matrix[0].length;
      if (target == matrix[0][0] || target == matrix[row - 1][col - 1]) {
        return true;
      } else if (target < matrix[0][0] || target > matrix[row - 1][col - 1]) {
        return false;
      }
      int hr = row - 1;
      int hc = 0;

      while (hr >= 0 && hc < col) {
        if (matrix[hr][hc] == target) {
          return true;
        } else if (target < matrix[hr][hc]) {
          hr--;
        } else {
          hc++;
        }
      }
      return false;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
