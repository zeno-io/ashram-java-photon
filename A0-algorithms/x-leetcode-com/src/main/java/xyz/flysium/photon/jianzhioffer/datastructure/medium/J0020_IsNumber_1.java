package xyz.flysium.photon.jianzhioffer.datastructure.medium;

/**
 * 剑指 Offer 20. 表示数值的字符串
 * <p>
 * https://leetcode-cn.com/problems/biao-shi-shu-zhi-de-zi-fu-chuan-lcof/
 *
 * @author zeno
 */
public interface J0020_IsNumber_1 {

  // 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
  // 例如，字符串"+100"、"5e2"、"-123"、"3.1416"、"-1E-16"、"0123"都表示数值，但"12e"、"1a3.14"、"1.2.3"、"+-5"及"12e+5.4"都不是。
  //

  // 执行用时：10 ms, 在所有 Java 提交中击败了14.30% 的用户
  class Solution {

    public boolean isNumber(String s) {
      String ts = s.trim();
      if (ts.endsWith("f") || ts.endsWith("F") || ts.endsWith("d") || ts.endsWith("D")) {
        return false;
      }
      try {
        Double.parseDouble(ts);
      } catch (NumberFormatException e) {
        return false;
      }
      return true;
    }

  }

}
