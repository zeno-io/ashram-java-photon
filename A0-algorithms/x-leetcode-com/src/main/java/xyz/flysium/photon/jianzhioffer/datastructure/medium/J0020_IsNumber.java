package xyz.flysium.photon.jianzhioffer.datastructure.medium;

/**
 * 剑指 Offer 20. 表示数值的字符串
 * <p>
 * https://leetcode-cn.com/problems/biao-shi-shu-zhi-de-zi-fu-chuan-lcof/
 *
 * @author zeno
 */
public interface J0020_IsNumber {

  // 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
  // 例如，字符串"+100"、"5e2"、"-123"、"3.1416"、"-1E-16"、"0123"都表示数值，但"12e"、"1a3.14"、"1.2.3"、"+-5"及"12e+5.4"都不是。
  //

  // 执行用时：3 ms, 在所有 Java 提交中击败了70.65% 的用户
  class Solution {

    public boolean isNumber(String s) {
      // [符号位+-][整数部分0~9][小数点][小数部分0~9][指数部分[eE][符号位+-][整数部分0~9]]
      //
      // 规则：
      // 1.如果符号位存在，后面必须是数字或小数点
      // 2.小数点前后至少有一个是数字
      // 3.指数部分不能是第一个

      // 根据规则定义以下状态：
      //
      // 1 指数前的符号位
      // 2 小数点前的数字
      // 3 小数点, 小数点后的数字
      // 4 当小数点前为空格时，小数点、小数点后的数字
      // 5 指数符号e/E
      // 6 指数中的符号位
      // 7 指数中的数字
      //
      // 合法的结束状态： 2 3 7
      //
      // 开始：
      //   符号位 -> 1
      //   数字 -> 2
      //   小数点 -> 4
      //
      // 中间：
      //   上一个状态
      //   是1（指数前的符号位）:
      //        数字 -> 2
      //        小数点 -> 4
      //
      //   是2（整数部分的数字）:
      //        数字 -> 2
      //        小数点 -> 3
      //        指数符号e/E  -> 5
      //
      //   是3（小数点, 小数点后的数字）:
      //        数字 -> 3
      //        指数符号e/E  -> 5
      //
      //   是4（当小数点前为空格时，小数点、小数点后的数字）:
      //        数字 -> 3
      //
      //   是5（指数符号e/E）:
      //        符号位  -> 6
      //        数字 -> 7
      //
      //   是6（指数中的符号位）:
      //        数字 -> 7
      //
      //   是7（指数中的数字）:
      //        数字 -> 7

      String ts = s.trim();
      if (ts.length() == 0) {
        return false;
      }
      int state = -1;
      for (int i = 0; i < ts.length(); i++) {
        char c = ts.charAt(i);
        // 开始
        if (i == 0) {
          if (isSign(c)) {
            state = 1;
          } else if (Character.isDigit(c)) {
            state = 2;
          } else if (isDot(c)) {
            state = 4;
          } else {
            return false;
          }
          continue;
        }
        // 中间
        switch (state) {
          case 1:
            if (Character.isDigit(c)) {
              state = 2;
            } else if (isDot(c)) {
              state = 4;
            } else {
              return false;
            }
            break;
          case 2:
            if (Character.isDigit(c)) {
              state = 2;
            } else if (isDot(c)) {
              state = 3;
            } else if (isE(c)) {
              state = 5;
            } else {
              return false;
            }
            break;
          case 3:
            if (Character.isDigit(c)) {
              state = 3;
            } else if (isE(c)) {
              state = 5;
            } else {
              return false;
            }
            break;
          case 4:
            if (Character.isDigit(c)) {
              state = 3;
            } else {
              return false;
            }
            break;
          case 5:
            if (isSign(c)) {
              state = 6;
            } else if (Character.isDigit(c)) {
              state = 7;
            } else {
              return false;
            }
            break;
          case 6:
          case 7:
            if (Character.isDigit(c)) {
              state = 7;
            } else {
              return false;
            }
            break;
          default:
            return false;
        }
      }
      return state == 2 || state == 3 || state == 7;
    }

    private boolean isSign(char c) {
      return c == '+' || c == '-';
    }

    private boolean isDot(char c) {
      return c == '.';
    }

    private boolean isE(char c) {
      return c == 'e' || c == 'E';
    }

  }

}
