package xyz.flysium.photon.algorithm.string.easy;

/**
 * 14. 最长公共前缀
 * <p>
 * https://leetcode-cn.com/problems/longest-common-prefix/
 *
 * @author zeno
 */
public interface T0014_LongestCommonPrefix {

  //  编写一个函数来查找字符串数组中的最长公共前缀。
  //  如果不存在公共前缀，返回空字符串 ""。
  class Solution {

    public String longestCommonPrefix(String[] strs) {
      if (strs.length == 0) {
        return "";
      }
      if (strs.length == 1) {
        return strs[0];
      }
      int index = 0;
      StringBuilder buf = new StringBuilder();
      OUT:
      while (true) {
        if (index >= strs[0].length()) {
          break;
        }
        char c = strs[0].charAt(index);
        for (int i = 1; i < strs.length; i++) {
          if (index >= strs[i].length()) {
            break OUT;
          }
          if (strs[i].charAt(index) != c) {
            break OUT;
          }
        }
        buf.append(c);
        index++;
      }
      return buf.toString();
    }
  }

}
