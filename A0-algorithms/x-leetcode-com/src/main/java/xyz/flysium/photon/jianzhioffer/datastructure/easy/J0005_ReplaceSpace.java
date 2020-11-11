package xyz.flysium.photon.jianzhioffer.datastructure.easy;

/**
 * 剑指 Offer 05. 替换空格
 * <p>
 * https://leetcode-cn.com/problems/ti-huan-kong-ge-lcof/
 *
 * @author zeno
 */
public interface J0005_ReplaceSpace {

  // 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。

  // 0ms 100.00%
  class Solution {

    public String replaceSpace(String s) {
      StringBuilder buf = new StringBuilder();
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (c == ' ') {
          buf.append("%20");
        } else {
          buf.append(c);
        }
      }
      return buf.toString();
    }

  }

}
