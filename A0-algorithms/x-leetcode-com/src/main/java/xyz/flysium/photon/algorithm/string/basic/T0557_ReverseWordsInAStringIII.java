package xyz.flysium.photon.algorithm.string.basic;

/**
 * 557. 反转字符串中的单词 III
 * <p>
 * https://leetcode-cn.com/problems/reverse-words-in-a-string-iii/
 *
 * @author zeno
 */
public interface T0557_ReverseWordsInAStringIII {

  // 给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
  class Solution {

    public String reverseWords(String s) {
      int start = 0;
      int end = 0;
      char[] c = s.toCharArray();
      while (end < c.length) {
        while (end < c.length && c[end] != ' ') {
          end++;
        }
        int l = end - start;
        for (int i = 0; i < l >> 1; i++) {
          // swap
          char tmp = c[start + i];
          c[start + i] = c[start + l - 1 - i];
          c[start + l - 1 - i] = tmp;
        }
        end++;
        start = end;
      }

      return new String(c);
    }

  }

}
