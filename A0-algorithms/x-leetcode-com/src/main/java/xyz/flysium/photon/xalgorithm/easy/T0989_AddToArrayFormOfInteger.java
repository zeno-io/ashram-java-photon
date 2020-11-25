package xyz.flysium.photon.xalgorithm.easy;

import java.util.LinkedList;
import java.util.List;

/**
 * 989. 数组形式的整数加法
 * <p>
 * https://leetcode-cn.com/problems/add-to-array-form-of-integer/
 *
 * @author zeno
 */
public class T0989_AddToArrayFormOfInteger {

//对于非负整数 X 而言，X 的数组形式是每位数字按从左到右的顺序形成的数组。例如，如果 X = 1231，那么其数组形式为 [1,2,3,1]。
//
// 给定非负整数 X 的数组形式 A，返回整数 X+K 的数组形式。
//
//
//
//
//
//
// 示例 1：
//
// 输入：A = [1,2,0,0], K = 34
//输出：[1,2,3,4]
//解释：1200 + 34 = 1234
//
//
// 示例 2：
//
// 输入：A = [2,7,4], K = 181
//输出：[4,5,5]
//解释：274 + 181 = 455
//
//
// 示例 3：
//
// 输入：A = [2,1,5], K = 806
//输出：[1,0,2,1]
//解释：215 + 806 = 1021
//
//
// 示例 4：
//
// 输入：A = [9,9,9,9,9,9,9,9,9,9], K = 1
//输出：[1,0,0,0,0,0,0,0,0,0,0]
//解释：9999999999 + 1 = 10000000000
//
//
//
//
// 提示：
//
//
// 1 <= A.length <= 10000
// 0 <= A[i] <= 9
// 0 <= K <= 10000
// 如果 A.length > 1，那么 A[0] != 0
//
// Related Topics 数组
// 👍 72 👎 0


  public static void main(String[] args) {
    Solution solution = new T0989_AddToArrayFormOfInteger().new Solution();
    System.out.println(solution.addToArrayForm(new int[]{1}, 189));
    System.out.println(solution.addToArrayForm(new int[]{1, 2, 0, 0}, 34));
  }

  // 执行耗时:3 ms,击败了99.40% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public List<Integer> addToArrayForm(int[] A, int K) {
      int carry = 0;
      A[A.length - 1] += K;
      for (int i = A.length - 1; i >= 0; i--) {
        A[i] += carry;
        if (A[i] >= 10) {
          int r = A[i] % 10;
          carry = A[i] / 10;
          A[i] = r;
        } else {
          carry = 0;
        }
      }
      LinkedList<Integer> ans = new LinkedList<>();
      if (carry > 0) {
        while (carry > 0) {
          int r = carry % 10;
          ans.addFirst(r);
          carry /= 10;
        }
      }
      for (int a : A) {
        ans.addLast(a);
      }
      return ans;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
