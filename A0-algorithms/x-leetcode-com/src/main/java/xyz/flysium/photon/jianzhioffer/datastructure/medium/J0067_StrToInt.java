package xyz.flysium.photon.jianzhioffer.datastructure.medium;

/**
 * 剑指 Offer 67. 把字符串转换成整数
 * <p>
 * https://leetcode-cn.com/problems/ba-zi-fu-chuan-zhuan-huan-cheng-zheng-shu-lcof/
 *
 * @author zeno
 */
public interface J0067_StrToInt {
  // 写一个函数 StrToInt，实现把字符串转换成整数这个功能。不能使用 atoi 或者其他类似的库函数。
  //
  //
  //
  //首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
  //
  //当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
  //
  //该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
  //
  //注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
  //
  //在任何情况下，若函数不能进行有效的转换时，请返回 0。
  //

  // 说明：
  //
  //假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为[−2^31, 2^31− 1]。如果数值超过这个范围，请返回 INT_MAX (2^31− 1) 或INT_MIN (−2^31) 。
  //

  // 执行用时：2 ms, 在所有 Java 提交中击败了99.89% 的用户
  class Solution {

    public int strToInt(String str) {
      int length = str.length();
      if (length == 0) {
        return 0;
      }
      int i = 0;
      while (i < length && str.charAt(i) == ' ') {
        i++;
      }
      boolean digit = false;
      boolean negative = false;
      long ans = 0;
      while (i < length) {
        char c = str.charAt(i);
        if (c == '+' || c == '-') {
          if (digit) {
            break;
          }
          if (i == length - 1 || !Character.isDigit(str.charAt(i + 1))) {
            break;
          }
          negative = c == '-';
          i++;
          continue;
        }
        if (!Character.isDigit(c)) {
          break;
        }
        digit = true;
        // -2147483648 2147483647
        if (ans > 214748364) {
          return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        if (!negative && ans == 214748364 && c > '7') {
          return Integer.MAX_VALUE;
        }
        if (negative && ans == 214748364 && c > '8') {
          return Integer.MIN_VALUE;
        }
        ans = ans * 10 + (c - '0');
        i++;
      }

      return (int) (negative ? -ans : ans);
    }

  }

}
