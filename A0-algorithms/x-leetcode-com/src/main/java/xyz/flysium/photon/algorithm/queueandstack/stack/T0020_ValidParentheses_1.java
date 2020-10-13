package xyz.flysium.photon.algorithm.queueandstack.stack;

import java.util.Stack;

/**
 * 20. 有效的括号
 * <p>
 * https://leetcode-cn.com/problems/valid-parentheses/
 *
 * @author zeno
 */
public interface T0020_ValidParentheses_1 {

  //  给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
  //  有效字符串需满足：
  //      左括号必须用相同类型的右括号闭合。
  //      左括号必须以正确的顺序闭合。
  //  注意空字符串可被认为是有效字符串。
  class Solution {

    // 3ms
    public boolean isValid(String s) {
      if (s == null || s.length() == 0) {
        return true;
      }
      char[] cs = s.toCharArray();
      Stack<Character> stack = new Stack<>();
      for (char c : cs) {
        boolean pop = !stack.isEmpty();
        if (pop) {
          Character pc = stack.peek();
          pop = ('(' == pc && c == ')') ||
            ('{' == pc && c == '}') ||
            ('[' == pc && c == ']');
        }
        if (pop) {
          stack.pop();
        } else {
          stack.push(c);
        }
      }

      return stack.isEmpty();
    }

  }

}
