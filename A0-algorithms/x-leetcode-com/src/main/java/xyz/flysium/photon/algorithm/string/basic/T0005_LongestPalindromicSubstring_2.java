package xyz.flysium.photon.algorithm.string.basic;

/**
 * 5. 最长回文子串
 * <p>
 * https://leetcode-cn.com/problems/longest-palindromic-substring/
 *
 * @author zeno
 */
public interface T0005_LongestPalindromicSubstring_2 {

  // 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
  class Solution {

    // O(N^3)
    public String longestPalindrome(String s) {
      if (s.length() == 1) {
        return s;
      }
      // to charArray will optimization for String.charAt[i] -> char[i]
      // 342 ms -> 	132 ms
      char[] chars = s.toCharArray();
      final int length = chars.length;
      int palindromeStart = -1, palindromeEnd = -1;
      int palindromeLength = length;
      OUT:
      while (palindromeLength >= 1) {
        for (int i = 0; i < length - palindromeLength + 1; i++) {
          boolean isPalindrome = true;
          for (int k = 0; k < palindromeLength >> 1; k++) {
            if (chars[i + k] != chars[i + palindromeLength - 1 - k]) {
              isPalindrome = false;
              break;
            }
          }
          if (isPalindrome) {
            palindromeStart = i;
            palindromeEnd = i + palindromeLength;
            break OUT;
          }
        }
        palindromeLength--;
      }
      if (palindromeStart < 0) {
        return "";
      }
      return s.substring(palindromeStart, palindromeEnd);
    }

  }

}
