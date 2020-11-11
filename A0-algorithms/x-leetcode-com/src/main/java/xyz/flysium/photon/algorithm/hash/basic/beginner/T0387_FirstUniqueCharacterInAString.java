package xyz.flysium.photon.algorithm.hash.basic.beginner;

import java.util.HashSet;
import java.util.Set;

/**
 * 387. 字符串中的第一个唯一字符
 * <p>
 * https://leetcode-cn.com/problems/first-unique-character-in-a-string/
 *
 * @author zeno
 */
public interface T0387_FirstUniqueCharacterInAString {

  // 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。

  // 你可以假定该字符串只包含小写字母。

  // 2 ms 100.00%
  class Solution {

    public int firstUniqChar(String s) {
      final int length = s.length();
      if (length == 0) {
        return -1;
      }
      // 如果不超过26个字母
      if (length <= 26) {
        Set<Character> set = new HashSet<>();
        int index = 0;
        while (index < length) {
          char c = s.charAt(index);
          if (!set.contains(c)) {
            if (s.lastIndexOf(c) == index) {
              return index;
            }
            set.add(c);
          }
          index++;
        }
        return -1;
      }
      // 超过26个字母的遍历26个字母
      int index = -1;
      for (char ch = 'a'; ch <= 'z'; ch++) {
        int beginIndex = s.indexOf(ch);
        // 从头开始的位置是否等于结束位置，相等说明只有一个，
        if (beginIndex != -1 && beginIndex == s.lastIndexOf(ch)) {
          // 取小的，越小代表越前。
          index = (index == -1 || index > beginIndex) ? beginIndex : index;
        }
      }

      return index;
    }

  }

}
