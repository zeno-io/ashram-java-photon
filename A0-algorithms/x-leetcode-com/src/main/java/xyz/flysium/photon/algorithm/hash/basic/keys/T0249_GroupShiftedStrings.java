package xyz.flysium.photon.algorithm.hash.basic.keys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 249. 移位字符串分组
 * <p>
 * https://leetcode-cn.com/problems/group-shifted-strings/
 *
 * @author zeno
 */
public interface T0249_GroupShiftedStrings {

  // 给定一个字符串，对该字符串可以进行 “移位” 的操作，也就是将字符串中每个字母都变为其在字母表中后续的字母，比如："abc" -> "bcd"。
  // 这样，我们可以持续进行 “移位” 操作，从而生成如下移位序列：
  //
  // "abc" -> "bcd" -> ... -> "xyz"
  //
  // 给定一个包含仅小写字母字符串的列表，将该列表中所有满足 “移位” 操作规律的组合进行分组并返回。
  //
  // 示例：
  //
  //输入：["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"]
  //输出：
  //[
  //  ["abc","bcd","xyz"],
  //  ["az","ba"],
  //  ["acef"],
  //  ["a","z"]
  //]
  //解释：可以认为字母表首尾相接，所以 'z' 的后续为 'a'，所以 ["az","ba"] 也满足 “移位” 操作规律。

  // 2ms 96.40%
  class Solution {

    public List<List<String>> groupStrings(String[] strings) {
      HashMap<String, List<String>> hash = new HashMap<>(strings.length, 1);

      for (String str : strings) {
        char[] cs = str.toCharArray();
        int incr = cs[0] - 'a';
        for (int i = 0; i < cs.length; i++) {
          int n = cs[i] - incr;
          if (n < 'a') {
            cs[i] = (char) ('z' - ('a' - 1 - n));
          } else {
            cs[i] = (char) n;
          }
        }
        String key = String.valueOf(cs);

        List<String> l = hash.computeIfAbsent(key, k -> new LinkedList<>());
        l.add(str);
      }
      List<List<String>> ans = new ArrayList<>(hash.size());
      ans.addAll(hash.values());
      return ans;
    }

  }

}
