package xyz.flysium.photon.algorithm.hash.basic.beginner;

import java.util.HashMap;

/**
 * 205. 同构字符串
 * <p>
 * https://leetcode-cn.com/problems/isomorphic-strings/
 *
 * @author zeno
 */
public interface T0205_IsomorphicStrings_1 {

  // 给定两个字符串 s 和 t，判断它们是否是同构的。
  //
  // 如果 s 中的字符可以被替换得到 t ，那么这两个字符串是同构的。
  //
  // 所有出现的字符都必须用另一个字符替换，同时保留字符的顺序。两个字符不能映射到同一个字符上，但字符可以映射自己本身。
  //
  // 你可以假设 s 和 t 具有相同的长度。

  // 10ms 68.71%
  class Solution {

    public boolean isIsomorphic(String s, String t) {
      char[] sc = s.toCharArray();
      char[] tc = t.toCharArray();
      final int len = sc.length;
      HashMap<Character, Character> s2t = new HashMap<>(len);
      HashMap<Character, Character> t2s = new HashMap<>(len);
      for (int i = 0; i < len; i++) {
        if (!t2s.containsKey(tc[i])) {
          // 两个字符不能映射到同一个字符上，但字符可以映射自己本身。
          // case: ab , aa -> false
          if (s2t.containsKey(sc[i])) {
            return false;
          }
          t2s.put(tc[i], sc[i]);
          s2t.put(sc[i], tc[i]);
        } else if (t2s.get(tc[i]) != sc[i]) {
          return false;
        }
      }
      return true;
    }

  }

}
