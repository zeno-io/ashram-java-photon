package xyz.flysium.photon.algorithm.string.basic;

/**
 * 151. 翻转字符串里的单词
 * <p>
 * https://leetcode-cn.com/problems/reverse-words-in-a-string/
 *
 * @author zeno
 */
public interface T0151_ReverseWordsInAString {

  // 给定一个字符串，逐个翻转字符串中的每个单词。
  class Solution {

    public String reverseWords(String s) {
      char[] chars = s.toCharArray();
      final int length = chars.length;

      int i = length - 1;
      int l = i;
      int r = i;
      StringBuilder buf = new StringBuilder();
      String pre = "";
      while (i >= 0) {
        while (i >= 0 && chars[i] == ' ') {
          i--;
        }
        r = i + 1;
        if (i < 0) {
          break;
        }
        while (i >= 0 && chars[i] != ' ') {
          i--;
        }
        l = i + 1;
        buf.append(pre).append(s.substring(l, r));
        pre = " ";
        i--;
      }

      return buf.toString();
    }

  }

}
