package xyz.flysium.photon.algorithm.string.medium;

import java.util.HashSet;
import java.util.Set;

/**
 * 159. 至多包含两个不同字符的最长子串
 * <p>
 * https://leetcode-cn.com/problems/longest-substring-with-at-most-two-distinct-characters/
 *
 * @author zeno
 */
public interface T0159_LongestSubstringWithAtMostTwoDistinctCharacters {

  // 给定一个字符串 s ，找出 至多 包含两个不同字符的最长子串 t ，并返回该子串的长度。

  // 3ms 87.09%
  class Solution {

    public int lengthOfLongestSubstringTwoDistinct(String s) {
      if (s.length() == 0) {
        return 0;
      }
      final char[] cs = s.toCharArray();
      final int capacity = s.length();
      int[] hash = new int[128];// ASCII
      Set<Character> ts = new HashSet<>(s.length(), 1);

      int left = 0;
      int right = 0;
      int ans = 0;
      while (right < capacity) {
        char c = cs[right];
        right++;
        hash[c]++;
        ts.add(c);

        while (ts.size() > 2) {
          c = cs[left];
          left++;
          hash[c]--;
          if (hash[c] == 0) {
            ts.remove(c);
          }
        }
        ans = Math.max(ans, right - left);
      }

      return ans;
    }

  }

}
