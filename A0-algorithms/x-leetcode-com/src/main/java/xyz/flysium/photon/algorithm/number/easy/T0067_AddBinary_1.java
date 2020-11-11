package xyz.flysium.photon.algorithm.number.easy;


/**
 * 67. 二进制求和
 * <p>
 * https://leetcode-cn.com/problems/add-binary/
 *
 * @author zeno
 */
public class T0067_AddBinary_1 {

//给你两个二进制字符串，返回它们的和（用二进制表示）。
//
// 输入为 非空 字符串且只包含数字 1 和 0。
//
//
//
// 示例 1:
//
// 输入: a = "11", b = "1"
//输出: "100"
//
// 示例 2:
//
// 输入: a = "1010", b = "1011"
//输出: "10101"
//
//
//
// 提示：
//
//
// 每个字符串仅由字符 '0' 或 '1' 组成。
// 1 <= a.length, b.length <= 10^4
// 字符串如果不是 "0" ，就都不含前导零。
//
// Related Topics 数学 字符串
// 👍 500 👎 0


  public static void main(String[] args) {
    Solution solution = new T0067_AddBinary_1().new Solution();

  }

  // 执行用时：2 ms, 在所有 Java 提交中击败了99.06% 的用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public String addBinary(String num1, String num2) {
      if (num1.length() > num2.length()) {
        return addBinary(num2, num1);
      }
      char[] cs1 = num1.toCharArray();
      char[] cs2 = num2.toCharArray();
      int len1 = cs1.length;
      int len2 = cs2.length;
      int[] ans = new int[cs2.length + 1];
      int i = 0;
      int p = 0;
      while (i < cs1.length) {
        int a = cs1[len1 - 1 - i] - '0';
        int b = cs2[len2 - 1 - i] - '0';
        int sum = p + a + b;
        if (sum >= 2) {
          ans[i] = sum - 2;
          p = 1;
        } else {
          ans[i] = sum;
          p = 0;
        }
        i++;
      }
      while (i < cs2.length) {
        int b = cs2[len2 - 1 - i] - '0';
        int sum = p + b;
        if (sum >= 2) {
          ans[i] = sum - 2;
          p = 1;
        } else {
          ans[i] = sum;
          p = 0;
        }
        i++;
      }
      i--;
      StringBuilder buf = new StringBuilder();
      if (p > 0) {
        buf.append(1);
      }
      while (i >= 0) {
        buf.append(ans[i]);
        i--;
      }
      return buf.toString();
    }
  }
  //leetcode submit region end(Prohibit modification and deletion)

}
