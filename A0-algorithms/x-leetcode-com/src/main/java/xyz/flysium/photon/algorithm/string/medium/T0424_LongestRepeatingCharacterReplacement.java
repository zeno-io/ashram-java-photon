package xyz.flysium.photon.algorithm.string.medium;

/**
 * 424. 替换后的最长重复字符
 * <p>
 * https://leetcode-cn.com/problems/longest-repeating-character-replacement/
 *
 * @author zeno
 */
public interface T0424_LongestRepeatingCharacterReplacement {

  // 给你一个仅由大写英文字母组成的字符串，你可以将任意位置上的字符替换成另外的字符，总共可最多替换 k 次。
  //
  // 在执行上述操作后，找到包含重复字母的最长子串的长度。
  //
  //注意:
  //字符串长度 和 k 不会超过 10 ^ 4。

  // 3ms 100.00%
  class Solution {

    public int characterReplacement(String s, int k) {
      if (s.length() <= k) {
        return s.length();
      }
      char[] cs = s.toCharArray();
      final int capacity = cs.length;
      int[] hash = new int[128];
      int left = 0;
      int right = 0;
      int charMax = 0;
      // scan to left
      while (right < capacity) {
        int charIdx = cs[right];
        hash[charIdx]++;
        right++;

        charMax = Math.max(hash[charIdx], charMax);
        while (right - left - charMax > k) {
          hash[cs[left]]--;
          left++;
        }
      }
      return capacity - left;
    }

  }

}
