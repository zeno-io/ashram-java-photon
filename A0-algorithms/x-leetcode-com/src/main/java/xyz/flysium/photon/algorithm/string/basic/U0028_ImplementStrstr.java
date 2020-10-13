package xyz.flysium.photon.algorithm.string.basic;

/**
 * 28. 实现 strStr()
 * <p>
 * https://leetcode-cn.com/problems/implement-strstr/
 *
 * @author zeno
 */
public interface U0028_ImplementStrstr {

  // 实现 strStr() 函数。
  // 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
  class Solution {

    // KMP
    public int strStr(String haystack, String needle) {
      final int n = haystack.length();
      final int l = needle.length();
      if (l == 0) {
        return 0;
      }
      char[] cn = haystack.toCharArray();
      char[] cl = needle.toCharArray();
      int[] next = getNext(needle);
      // 文本串指针
      int pn = 0;
      // 模式串指针
      int pl = 0;
      // 自左向右逐个比对字符
      while (pn < n && pl < l) {
        // 若匹配，或 needle 已移到最左侧
        if (pl < 0 || cn[pn] == cl[pl]) {
          // 则转到下一字符
          pn++;
          pl++;
        } else {
          // 模式串右移（注意：文本串不用回退）
          pl = next[pl];
        }
      }
      if (pl == l) {
        return pn - pl;
      }
      return -1;
    }

    private int[] getNext(String patternStr) {
      char[] pattern = patternStr.toCharArray();
      final int n = pattern.length;
      //  a b a c a b a b d
      //  next[k] 表示 pattern 从 [0,k) 子串中公共前缀，后缀的最大长度
      //  也是匹配的最大公共前缀的下一个下标
      //  next[1] a -> 0
      //  next[2] a b -> 0
      //  next[3] a b a -> 1
      //  next[4] a b a c -> 0
      //  next[5] a b a c a -> 1
      //  next[6] a b a c a b -> 2
      //  next[7] a b a c a b a -> 3
      //  next[8] a b a c a b a b -> 失匹配 -> next[3] 继续 -> 2
      int[] next = new int[n];
      next[0] = -1;
      if (n > 1) {
        next[1] = 0;
      }
      // “主”串指针
      int j = 0;
      // 模式串指针
      int k = -1;
      while (j < n - 1) {
        // 匹配，或 k 已移到最左侧
        if (k < 0 || pattern[k] == pattern[j]) {
          k++;
          j++;
          next[j] = k;
        } else {
          // 失配
          k = next[k];
        }
      }
      return next;
    }

  }

}
