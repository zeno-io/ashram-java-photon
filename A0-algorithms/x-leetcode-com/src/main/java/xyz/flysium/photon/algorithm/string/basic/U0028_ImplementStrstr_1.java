package xyz.flysium.photon.algorithm.string.basic;

/**
 * 28. 实现 strStr()
 * <p>
 * https://leetcode-cn.com/problems/implement-strstr/
 *
 * @author zeno
 */
public interface U0028_ImplementStrstr_1 {

  // 实现 strStr() 函数。
  // 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
  class Solution {

    public int strStr(String haystack, String needle) {
      final int n = haystack.length();
      final int l = needle.length();
      if (l == 0) {
        return 0;
      }
      char[] c = haystack.toCharArray();
      char[] s = needle.toCharArray();

      int pn = 0, sn;
      while (pn < n - l + 1) {
        // find the position of the first needle character in the haystack string
        while (pn < n - l + 1 && c[pn] != s[0]) {
          ++pn;
        }
        sn = pn;
        int pl = 0;
        // compute the max match string
        while (pl < l && pn < n && c[pn] == s[pl]) {
          ++pl;
          ++pn;
        }
        //if the whole needle string is found,return its start position
        if (pl == l) {
          return sn;
        }
        // otherwise, backtrack
        pn = sn + 1;
      }
      return -1;
    }

  }

}
