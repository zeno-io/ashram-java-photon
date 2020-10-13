package xyz.flysium.photon.algorithm.string.basic;

/**
 * 5. 最长回文子串
 * <p>
 * https://leetcode-cn.com/problems/longest-palindromic-substring/
 *
 * @author zeno
 */
public interface T0005_LongestPalindromicSubstring_1 {

  // 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
  class Solution {

    // O(N^2) 153ms
    public String longestPalindrome(String s) {
      if (s.length() == 1) {
        return s;
      }
      char[] chars = s.toCharArray();
      final int length = chars.length;
      // dp[i][j] means substring(i,j) is palindrome
      // dp[i][j] = dp[i+1][j-1] && s(i) == s(j)
      boolean[][] dp = new boolean[length][length];
      // init
      for (int i = 0; i < length; i++) {
        for (int j = 0; j < length; j++) {
          dp[i][j] = true;
        }
      }
      int palindromeLength = 1;
      int palindromeStart = -1, palindromeEnd = -1;
      while (palindromeLength <= length) {
        for (int i = 0; i < length - palindromeLength + 1; i++) {
          int j = i + palindromeLength - 1;
          if (palindromeLength >= 3) {
            dp[i][j] = dp[i + 1][j - 1] && chars[i] == chars[j];
          } else {
            dp[i][j] = chars[i] == chars[j];
          }
          if (dp[i][j] && palindromeLength > (palindromeEnd - palindromeStart)) {
            palindromeStart = i;
            palindromeEnd = j + 1;
          }
        }
        palindromeLength++;
      }

      return palindromeStart < 0 ? "" : s.substring(palindromeStart, palindromeEnd);
    }

  }

}
