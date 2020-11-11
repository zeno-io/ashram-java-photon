package xyz.flysium.photon.xalgorithm.easy;

/**
 * 66. 加一
 * <p>
 * https://leetcode-cn.com/problems/plus-one/
 *
 * @author zeno
 */
public class T0060_PlusOne {

//给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
//
// 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
//
// 你可以假设除了整数 0 之外，这个整数不会以零开头。
//
// 示例 1:
//
// 输入: [1,2,3]
//输出: [1,2,4]
//解释: 输入数组表示数字 123。
//
//
// 示例 2:
//
// 输入: [4,3,2,1]
//输出: [4,3,2,2]
//解释: 输入数组表示数字 4321。
//
// Related Topics 数组
// 👍 570 👎 0


  public static void main(String[] args) {
    Solution solution = new T0060_PlusOne().new Solution();

  }

  // 执行耗时:0 ms,击败了100.00% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int[] plusOne(int[] digits) {
      if (digits == null || digits.length == 0) {
        return new int[0];
      }

      int k = digits.length - 1;
      int carry = 1;
      while (k >= 0) {
        digits[k] += carry;
        if (digits[k] >= 10) {
          digits[k] -= 10;
          carry = 1;
        } else {
          carry = 0;
        }
        k--;
      }
      if (carry > 0) {
        int[] ans = new int[digits.length + 1];
        System.arraycopy(digits, 0, ans, 1, digits.length);
        ans[0] = carry;
        return ans;
      }

      return digits;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
