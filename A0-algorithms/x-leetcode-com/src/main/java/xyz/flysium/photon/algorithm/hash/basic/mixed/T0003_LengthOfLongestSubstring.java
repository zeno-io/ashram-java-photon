package xyz.flysium.photon.algorithm.hash.basic.mixed;

/**
 * 3. 无重复字符的最长子串
 *
 * @author zeno
 */
public interface T0003_LengthOfLongestSubstring {
  // 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。

  //示例 1:

  //输入: "abcabcbb"
  //输出: 3
  //解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
  //
  //示例 2:
  //
  //输入: "bbbbb"
  //输出: 1
  //解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
  //
  //示例 3:
  //
  //输入: "pwwkew"
  //输出: 3
  //解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
  //     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。

  // 2ms 100.00%
  class Solution {

    public int lengthOfLongestSubstring(String s) {
      if (s == null || s.length() == 0) {
        return 0;
      }
      boolean[] hash = new boolean[128];
      char[] cs = s.toCharArray();
      int ans = 1;
      int right = -1;
      for (int left = 0; left < cs.length; left++) {
        if (left > 0) {
          hash[cs[left - 1]] = false;
        }
        while (right + 1 < cs.length && !hash[cs[right + 1]]) {
          hash[cs[right + 1]] = true;
          right++;
        }
        ans = Math.max(ans, right - left + 1);
      }
      return ans;
    }

  }

}
