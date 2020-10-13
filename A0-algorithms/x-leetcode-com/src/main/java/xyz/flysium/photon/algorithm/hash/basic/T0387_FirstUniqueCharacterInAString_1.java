package xyz.flysium.photon.algorithm.hash.basic;

/**
 * 387. 字符串中的第一个唯一字符
 * <p>
 * https://leetcode-cn.com/problems/first-unique-character-in-a-string/
 *
 * @author zeno
 */
public interface T0387_FirstUniqueCharacterInAString_1 {

  // 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。

  // 你可以假定该字符串只包含小写字母。

  // 5 ms 92.70%
  class Solution {

    public int firstUniqChar(String s) {
      char[] cs = s.toCharArray();
      MyInfo[] hash = new Solution.MyInfo[128];// 'a' - 'z'
      for (int i = 0; i < cs.length; i++) {
        int index = cs[i] & 127;
        Solution.MyInfo v = hash[index];
        if (v != null) {
          v.count++;
        } else {
          hash[index] = new Solution.MyInfo(i);
        }
      }
      int minIndex = Integer.MAX_VALUE;
      boolean exists = false;
      for (Solution.MyInfo info : hash) {
        if (info != null && info.count == 1) {
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
