package xyz.flysium.photon.algorithm.queueandstack.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 150. 逆波兰表达式求值
 * <p>
 * https://leetcode-cn.com/problems/evaluate-reverse-polish-notation/
 *
 * @author zeno
 */
public interface T0150_EvaluateReversePolishNotation {

  // 根据 逆波兰表示法，求表达式的值。
  // 有效的运算符包括 +, -, *, / 。每个运算对象可以是整数，也可以是另一个逆波兰表达式。
  //    整数除法只保留整数部分。
  //    给定逆波兰表达式总是有效的。换句话说，表达式总会得出有效数值且不存在除数为 0 的情况。
  class Solution {

    public int evalRPN(String[] tokens) {
      Deque<Integer> numStack = new ArrayDeque<>(tokens.length);
      Integer op1, op2;
      for (String token : tokens) {
        switch (token) {
          case "+":
            op2 = numStack.pop();
            op1 = numStack.pop();
            numStack.push(op1 + op2);
            break;
          case "-":
            op2 = numStack.pop();
            op1 = numStack.pop();
            numStack.push(op1 - op2);
            break;
          case "*":
            op2 = numStack.pop();
            op1 = numStack.pop();
            numStack.push(op1 * op2);
            break;
          case "/":
            op2 = numStack.pop();
            op1 = numStack.pop();
            numStack.push(op1 / op2);
            break;
          default:
            numStack.push(Integer.valueOf(token));
            break;
        }
      }
      return numStack.pop();
    }

  }

}
