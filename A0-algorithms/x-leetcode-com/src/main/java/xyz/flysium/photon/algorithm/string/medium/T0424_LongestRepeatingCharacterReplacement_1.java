package xyz.flysium.photon.algorithm.string.medium;

/**
 * 424. 替换后的最长重复字符
 * <p>
 * https://leetcode-cn.com/problems/longest-repeating-character-replacement/
 *
 * @author zeno
 */
public interface T0424_LongestRepeatingCharacterReplacement_1 {

  // 给你一个仅由大写英文字母组成的字符串，你可以将任意位置上的字符替换成另外的字符，总共可最多替换 k 次。
  //
  // 在执行上述操作后，找到包含重复字母的最长子串的长度。
  //
  //注意:
  //字符串长度 和 k 不会超过 10 ^ 4。

  // 6ms 63.56%
  class Solution {

    public int characterReplacement(String s, int k) {
      if (s.length() <= k) {
        return s.length();
      }
      char[] cs = s.toCharArray();
      final int capacity = cs.length;
      int[] hash = new int[128];
      int right = -1;
      int ans = 0;
      // scan to right
      for (int left = 0; left < capacity; left++) {
        if (left > 0) {
          hash[cs[left - 1]]--;
        }
        int maxCharIdx = cs[left];
        while (right + 1 < capacity) {
          int nextCharIdx = cs[right + 1];
          // try add next, rollback if fail
          hash[nextCharIdx]++;
          // compute max
          if (hash[nextCharIdx] > hash[maxCharIdx]) {
            maxCharIdx = nextCharIdx;
          }
          int newLen = (right + 1) - left + 1;
          // if forward, new capacity - max, if great than k, break
          if (newLen - hash[maxCharIdx] > k) {
            // rollback
            hash[nextCharIdx]--;
            break;
          }
          // forward to next position
          right++;
        }
        ans = Math.max(ans, right - left + 1);
      }

      return ans;
    }

  }

}
