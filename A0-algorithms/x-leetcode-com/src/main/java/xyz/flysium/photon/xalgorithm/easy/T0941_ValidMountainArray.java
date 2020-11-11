package xyz.flysium.photon.xalgorithm.easy;

/**
 * 941. 有效的山脉数组
 * <p>
 * https://leetcode-cn.com/problems/valid-mountain-array/
 *
 * @author zeno
 */
public class T0941_ValidMountainArray {

//给定一个整数数组 A，如果它是有效的山脉数组就返回 true，否则返回 false。
//
// 让我们回顾一下，如果 A 满足下述条件，那么它是一个山脉数组：
//
//
// A.length >= 3
// 在 0 < i < A.length - 1 条件下，存在 i 使得：
//
// A[0] < A[1] < ... A[i-1] < A[i]
// A[i] > A[i+1] > ... > A[A.length - 1]
//
//
//
//
//
//
//
//
//
//
// 示例 1：
//
// 输入：[2,1]
//输出：false
//
//
// 示例 2：
//
// 输入：[3,5,5]
//输出：false
//
//
// 示例 3：
//
// 输入：[0,3,2,1]
//输出：true
//
//
//
// 提示：
//
//
// 0 <= A.length <= 10000
// 0 <= A[i] <= 10000
//
//
//
//
//
// Related Topics 数组
// 👍 100 👎 0


  public static void main(String[] args) {
    Solution solution = new T0941_ValidMountainArray().new Solution();

  }

  // 执行耗时:1 ms,击败了100.00% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public boolean validMountainArray(int[] A) {
      if (A == null || A.length < 3) {
        return false;
      }
      final int len = A.length;
      int i = 0;
      while (i + 1 < len && A[i + 1] > A[i]) {
        i = i + 1;
      }
      int k = i;
      while (i + 1 < len && A[i + 1] < A[i]) {
        i = i + 1;
      }
      return (k > 0 && k < len - 1) && i == len - 1;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
