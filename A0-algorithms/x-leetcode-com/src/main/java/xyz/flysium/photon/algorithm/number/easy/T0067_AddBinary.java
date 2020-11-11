package xyz.flysium.photon.algorithm.number.easy;


/**
 * 67. 二进制求和
 * <p>
 * https://leetcode-cn.com/problems/add-binary/
 *
 * @author zeno
 */
public class T0067_AddBinary {

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
    Solution solution = new T0067_AddBinary().new Solution();

  }

  // 执行用时：2 ms, 在所有 Java 提交中击败了99.06% 的用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public String addBinary(String a, String b) {
      int i = a.length() - 1;
      int j = b.length() - 1;
      StringBuilder buf = new StringBuilder();
      int pre = 0;
      while (i >= 0 || j >= 0) {
        int sum = pre;
        if (i >= 0) {
          sum += (a.charAt(i--) - '0');
        }
        if (j >= 0) {
          sum += (b.charAt(j--) - '0');
        }
        buf.append(sum % 2);
        pre = sum / 2;
      }
      if (pre != 0) {
        buf.append(pre);
      }
      return buf.reverse().toString();
    }
  }
  //leetcode submit region end(Prohibit modification and deletion)

}
