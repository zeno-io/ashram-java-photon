package xyz.flysium.photon.algorithm.hash.basic;

/**
 * 205. 同构字符串
 * <p>
 * https://leetcode-cn.com/problems/isomorphic-strings/
 *
 * @author zeno
 */
public interface T0205_IsomorphicStrings {

  // 给定两个字符串 s 和 t，判断它们是否是同构的。
  //
  // 如果 s 中的字符可以被替换得到 t ，那么这两个字符串是同构的。
  //
  // 所有出现的字符都必须用另一个字符替换，同时保留字符的顺序。两个字符不能映射到同一个字符上，但字符可以映射自己本身。
  //
  // 你可以假设 s 和 t 具有相同的长度。

  // 2ms 99.98%
  class Solution {

    public boolean isIsomorphic(String s, String t) {
      char[] sc = s.toCharArray();
      char[] tc = t.toCharArray();
      final int len = sc.length;
//      HashMap<Character, Character> s2t = new HashMap<>(len);
//      HashMap<Character, Character> t2s = new HashMap<>(len);
      // ASCII 'A' ... 'Z' 'a' ... 'z'
      int[] s2t = new int[128];  // s to t
      int[] t2s = new int[128];  // t to s
      for (int i = 0; i < len; i++) {
        int t2sIndex = tc[i] & 127;
        if (t2s[t2sIndex] == 0) {
          // 两个字符不能映射到同一个字符上，但字符可以映射自己本身。
          // case: ab , aa -> false
          int s2tIndex = sc[i] & 127;
          if (s2t[s2tIndex] > 0) {
            return false;
          }
          t2s[t2sIndex] = sc[i];
          s2t[s2tIndex] = tc[i];
        } else if (t2s[t2sIndex] != sc[i]) {
          return false;
        }
      }
      return true;
    }

  }

}
