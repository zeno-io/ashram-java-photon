package xyz.flysium.photon.algorithm.string.basic;

/**
 * 5. 最长回文子串
 * <p>
 * https://leetcode-cn.com/problems/longest-palindromic-substring/
 *
 * @author zeno
 */
public interface T0005_LongestPalindromicSubstring {

  // 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
  class Solution {

    // O(N^2) 3ms
    public String longestPalindrome(String s) {
      if (s.length() == 0) {
        return "";
      }
      if (s.length() == 1) {
        return s;
      }
      char[] chars = s.toCharArray();
      final int length = chars.length;

      int palindromeStart = 0, palindromeEnd = 0;
      int i = 0;
      while (i < length) {
        //  把回文看成中间的部分全是同一字符，左右部分相对称
        //  找到下一个与当前字符不同的字符
        int l = i;
        int r = i;
        // 当剩余的字符串（就是low离右边的距离）*2 还小于当前最长回文子串， 后面就算找到回文，怎么也比不过当前最长回文子串
        if ((length - l) << 1 < (palindromeEnd - palindromeStart)) {
          break;
        }
        // 查找中间部分, 定位中间部分的最后一个字符
        while (r + 1 < length && chars[r + 1] == chars[i]) {
          r++;
        }
        int m = r;
        // 从中间向左右扩散
        while (l >= 1 && r + 1 < length && chars[l - 1] == chars[r + 1]) {
          l--;
          r++;
        }
        // 记录最大长度
        if ((r - l) > (palindromeEnd - palindromeStart)) {
          palindromeStart = l;
          palindromeEnd = r;
        }
        // m 其实只有两种值，也就是 =i 或者 一堆与i相同的值中间部分最后一个字符
        // 我们可以直接跳中间部分的最后一个字符，因为这中间的回文子串最长也不能超过 中间部分长度
        i = m;
        // 下一个位置
        i++;
      }

      return palindromeStart < 0 ? "" : s.substring(palindromeStart, palindromeEnd + 1);
    }

  }

}
