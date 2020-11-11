package xyz.flysium.photon.algorithm.hash.basic.mixed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 347. 前 K 个高频元素
 * <p>
 * https://leetcode-cn.com/problems/top-k-frequent-elements/
 *
 * @author zeno
 */
public interface T0347_TopKFrequentElements {

  // 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。

  // 你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
  // 你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
  // 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的。
  // 你可以按任意顺序返回答案。
  //

  // 9ms 99.15%
  class Solution {

    public int[] topKFrequent(int[] nums, int k) {
      Map<Integer, Integer> hash = new HashMap<>(nums.length, 1);
      for (int num : nums) {
        hash.merge(num, 1, Integer::sum);
      }
//      PriorityQueue<Integer> minHeap = new PriorityQueue<>(k,
//        (o1, o2) -> hash.get(o1).compareTo(hash.get(o2)));
      List<Integer>[] buckets = new List[nums.length + 1];
      for (Map.Entry<Integer, Integer> entry : hash.entrySet()) {
        Integer count = entry.getValue();
        List<Integer> l = buckets[count];
        if (l == null) {
          l = new ArrayList<>(nums.length);
          buckets[count] = l;
        }
        l.add(entry.getKey());
      }
      int[] ans = new int[k];
      int i = 0;
      for (int count = nums.length; count >= 0 && i < k; count--) {
        if (buckets[count] != null) {
          for (int num : buckets[count]) {
            ans[i++] = num;
            if (i >= k) {
              break;
            }
          }
        }
      }
      return i == k ? ans : Arrays.copyOfRange(ans, 0, k);
    }

  }

}
