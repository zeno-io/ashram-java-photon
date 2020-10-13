package xyz.flysium.photon.algorithm.hash.basic;

import java.util.HashMap;
import java.util.Map;

/**
 * 136. 只出现一次的数字
 * <p>
 * https://leetcode-cn.com/problems/single-number/
 *
 * @author zeno
 */
public interface T0136_SingleNumber_3 {

  // 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
  // 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？

  // 12 ms 12.86%
  class Solution {

    public int singleNumber(int[] nums) {
      Map<Integer, Integer> hash = new HashMap<>(nums.length);

      for (int num : nums) {
        Integer v = hash.putIfAbsent(num, 1);
        if (v != null) {
          hash.put(num, 2);
        }
      }

      for (Map.Entry<Integer, Integer> entry : hash.entrySet()) {
        if (entry.getValue() == 1) {
          return entry.getKey();
        }
      }

      return -1;
    }

  }

}
