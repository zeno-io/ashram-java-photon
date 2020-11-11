package xyz.flysium.photon.algorithm.hash.basic.beginner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 599. 两个列表的最小索引总和
 * <p>
 * https://leetcode-cn.com/problems/minimum-index-sum-of-two-lists/
 *
 * @author zeno
 */
public interface T0599_MinimumIndexSumOfTwoLists_1 {

  // 假设Andy和Doris想在晚餐时选择一家餐厅，并且他们都有一个表示最喜爱餐厅的列表，每个餐厅的名字用字符串表示。
  //
  // 你需要帮助他们用最少的索引和找出他们共同喜爱的餐厅。 如果答案不止一个，则输出所有答案并且不考虑顺序。
  //
  // 你可以假设总是存在一个答案。

  // 11ms 64.06%
  class Solution {

    public String[] findRestaurant(String[] list1, String[] list2) {
      Map<String, Integer> hash = new HashMap<>(list1.length, 1);
      // 输出所有答案并且不考虑顺序
      Set<String>[] sumHash = new Set[list1.length + list2.length];

      for (int i = 0; i < list1.length; i++) {
        hash.put(list1[i], i);
      }
      int minIndexSum = Integer.MAX_VALUE;
      for (int j = 0; j < list2.length; j++) {
        String restaurant = list2[j];
        Integer i = hash.get(restaurant);
        if (i != null) {
          if (i + j <= minIndexSum) {
            if (i + j < minIndexSum && minIndexSum < sumHash.length) {
              sumHash[minIndexSum] = null;
            }
            Set<String> l = sumHash[i + j];
            if (l == null) {
              l = new HashSet<>();
              l.add(restaurant);
              sumHash[i + j] = l;
            } else {
              l.add(restaurant);
            }
            minIndexSum = i + j;
          }
        }
      }
      for (Set<String> s : sumHash) {
        if (s != null) {
          String[] ans = new String[s.size()];
          int index = 0;
          for (String restaurant : s) {
            ans[index++] = restaurant;
          }
          return ans;
        }
      }
      return new String[]{};
    }

  }

}
