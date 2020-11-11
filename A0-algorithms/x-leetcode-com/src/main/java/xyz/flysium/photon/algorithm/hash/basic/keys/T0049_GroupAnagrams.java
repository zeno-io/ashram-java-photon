package xyz.flysium.photon.algorithm.hash.basic.keys;

import java.util.ArrayList;
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
public interface T0049_GroupAnagrams {

  // 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。

  //  所有输入均为小写字母。
  //  不考虑答案输出的顺序。

  // 6 ms 99.56%
  class Solution {

    // 每个字母对应一个质数 'a' - 'z'
    private static final int[] PRIME = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 41, 43, 47, 53, 59,
      61, 67, 71, 73, 79, 83, 89, 97, 101, 103};

    public List<List<String>> groupAnagrams(String[] strs) {
      if (strs.length == 0) {
        return Collections.emptyList();
      }
      if (strs.length == 1) {
        return Collections.singletonList(new ArrayList<>(Collections.singletonList(strs[0])));
      }
      Map<Long, List<String>> hash = new HashMap<>(strs.length, 1);

      for (String str : strs) {
        // 算术基本定理，又称为正整数的唯一分解定理，即：
        //  每个大于1的自然数，要么本身就是质数，要么可以写为2个以上的质数的积，而且这些质因子按大小排列之后，写法仅有一种方式。
        char[] cs = str.toCharArray();
        // 需要考虑 key 的计算溢出
        long key = 1;
        for (char c : cs) {
          key *= PRIME[c - 'a'];
        }

        List<String> l = hash.computeIfAbsent(key, k -> new LinkedList<>());
        l.add(str);
      }
      List<List<String>> ans = new ArrayList<>(hash.size());
      ans.addAll(hash.values());
      return ans;
    }

  }

}
