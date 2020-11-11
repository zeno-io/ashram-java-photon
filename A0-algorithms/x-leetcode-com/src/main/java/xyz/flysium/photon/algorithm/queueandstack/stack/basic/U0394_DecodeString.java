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
public interface U0394_DecodeString {

  // 给定一个经过编码的字符串，返回它解码后的字符串。
  //
  //编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
  //
  //你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
  //
  //此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
  //

  // 1 ms , 89.44%
  class Solution {

    public String decodeString(String s) {
      char[] cs = s.toCharArray();

      // 数字存放在数字栈，字符串存放在字符串栈 ->
      Deque<Integer> repTimeStack = new LinkedList<>();
      Deque<StringBuilder> charStack = new LinkedList<>();
      StringBuilder ans = new StringBuilder();

      int repTime = 0;
      for (Character c : cs) {
        if (Character.isDigit(c)) {
          repTime = repTime * 10 + (c - '0');
        } else if (c == '[') {
          repTimeStack.push(repTime);
          charStack.push(ans);
          repTime = 0;
          ans = new StringBuilder();
        } else if (Character.isAlphabetic(c)) {
          ans.append(c);
        } else {
          // ]
          Integer times = repTimeStack.pop();
          StringBuilder str = charStack.pop();
          for (int i = 0; i < times; i++) {
            str.append(ans);
          }
          ans = str;
        }
      }
      return ans.toString();
    }

  }

}
