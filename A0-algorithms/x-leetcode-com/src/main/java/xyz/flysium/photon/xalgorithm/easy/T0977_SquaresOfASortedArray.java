package xyz.flysium.photon.xalgorithm.easy;

import java.util.Arrays;

/**
 * 977. 有序数组的平方
 * <p>
 * https://leetcode-cn.com/problems/squares-of-a-sorted-array/
 *
 * @author zeno
 */
public class T0977_SquaresOfASortedArray {

//给定一个按非递减顺序排序的整数数组 A，返回每个数字的平方组成的新数组，要求也按非递减顺序排序。
//
//
//
// 示例 1：
//
// 输入：[-4,-1,0,3,10]
//输出：[0,1,9,16,100]
//
//
// 示例 2：
//
// 输入：[-7,-3,2,3,11]
//输出：[4,9,9,49,121]
//
//
//
//
// 提示：
//
//
// 1 <= A.length <= 10000
// -10000 <= A[i] <= 10000
// A 已按非递减顺序排序。
//
// Related Topics 数组 双指针
// 👍 173 👎 0


  public static void main(String[] args) {
    Solution solution = new T0977_SquaresOfASortedArray().new Solution();
    System.out.println(Arrays.toString(solution.sortedSquares(new int[]{-1, 2, 2})));
  }

  // 执行耗时:1 ms,击败了100.00% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int[] sortedSquares(int[] A) {
      int[] ans = new int[A.length];
      int k = 0;
      while (k < A.length && A[k] < 0) {
        k++;
      }
      if (k >= A.length) {
        k = A.length - 1;
      }
      int c = 0;
      int l = k - 1;
      int r = k;
      while (c < A.length) {
        if (l >= 0 && r < A.length) {
          int ls = A[l] * A[l];
          int rs = A[r] * A[r];
          if (ls < rs) {
            ans[c++] = ls;
            l--;
          } else {
            ans[c++] = rs;
            r++;
          }
        } else if (l >= 0) {
          ans[c++] = A[l] * A[l];
          l--;
        } else if (r < A.length) {
          ans[c++] = A[r] * A[r];
          r++;
        }
      }
      return ans;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
