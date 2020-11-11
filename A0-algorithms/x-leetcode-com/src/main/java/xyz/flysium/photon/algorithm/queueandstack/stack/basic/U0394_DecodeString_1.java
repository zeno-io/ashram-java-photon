package xyz.flysium.photon.algorithm.queueandstack.stack.basic;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 394. 字符串解码
 * <p>
 * https://leetcode-cn.com/problems/decode-string/
 *
 * @author zeno
 */
public interface U0394_DecodeString_1 {

  // 给定一个经过编码的字符串，返回它解码后的字符串。
  //
  //编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
  //
  //你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
  //
  //此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
  //

  // 3 ms , 32.20%
  class Solution {

    public String decodeString(String s) {
      char[] cs = s.toCharArray();

      // 数字存放在数字栈，字符串存放在字符串栈 <-
      Deque<Integer> numStack = new LinkedList<>();
      Deque<Character> charStack = new LinkedList<>();
      LinkedList<Character> ts = new LinkedList<>();

      int i = cs.length - 1;
      while (i >= 0) {
        if (cs[i] == ']') {
          charStack.push(']');
        } else if (cs[i] == '[') {
          i--;
          while (i >= 0 && cs[i] >= '0' && cs[i] <= '9') {
            numStack.push(cs[i] - '0');
            i--;
          }
          int num = 0;
          while (!numStack.isEmpty()) {
            Integer n = numStack.pop();
            num = num * 10 + n;
          }
          ts.clear();
          while (!charStack.isEmpty()) {
            Character c = charStack.pop();
            if (c == ']') {
              break;
            }
            ts.offerFirst(c);
          }
          System.out.println();
          for (int j = 0; j < num; j++) {
            for (Character c : ts) {
              charStack.push(c);
            }
          }
          continue;
        } else {
          charStack.push(cs[i]);
        }
        i--;
      }
      StringBuilder buf = new StringBuilder();
      while (!charStack.isEmpty()) {
        Character c = charStack.pop();
        if (c == ']') {
          break;
        }
        buf.append(c);
      }

      return buf.toString();
    }

  }

}
