package xyz.flysium.photon.algorithm.hash.basic.keys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 49. 字母异位词分组
 * <p>
 * https://leetcode-cn.com/problems/group-anagrams/
 *
 * @author zeno
 */
public interface T0049_GroupAnagrams_1 {

  // 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。

  //  所有输入均为小写字母。
  //  不考虑答案输出的顺序。

  // 8 ms 94.02%
  class Solution {

    public List<List<String>> groupAnagrams(String[] strs) {
      if (strs.length == 0) {
        return Collections.emptyList();
      }
      if (strs.length == 1) {
        return Collections.singletonList(new ArrayList<>(Collections.singletonList(strs[0])));
      }
      Map<String, List<String>> hash = new HashMap<>(strs.length, 1);

      for (String str : strs) {
        char[] cs = str.toCharArray();
        Arrays.sort(cs);
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
