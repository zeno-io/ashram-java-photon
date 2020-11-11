package xyz.flysium.photon.algorithm.hash.basic.beginner;

import java.util.HashMap;
import java.util.Map;

/**
 * 387. 字符串中的第一个唯一字符
 * <p>
 * https://leetcode-cn.com/problems/first-unique-character-in-a-string/
 *
 * @author zeno
 */
public interface T0387_FirstUniqueCharacterInAString_2 {

  // 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。

  // 你可以假定该字符串只包含小写字母。

  // 20 ms 63%
  class Solution {

    public int firstUniqChar(String s) {
      char[] cs = s.toCharArray();
      Map<Character, MyInfo> hash = new HashMap<>(s.length(), 1);
      for (int i = 0; i < cs.length; i++) {
        MyInfo v = hash.get(cs[i]);
        if (v != null) {
          v.count++;
        } else {
          hash.put(cs[i], new MyInfo(i));
        }
      }
      int minIndex = Integer.MAX_VALUE;
      boolean exists = false;
      for (MyInfo info : hash.values()) {
        if (info.count == 1) {
          exists = true;
          if (info.index < minIndex) {
            minIndex = info.index;
          }
        }
      }
      return exists ? minIndex : -1;
    }

    static class MyInfo {

      int index;
      int count = 1;

      public MyInfo(int index) {
        this.index = index;
      }

      @Override
      public String toString() {
        return "MyInfo{" +
          "index=" + index +
          ", count=" + count +
          '}';
      }
    }

  }

}
