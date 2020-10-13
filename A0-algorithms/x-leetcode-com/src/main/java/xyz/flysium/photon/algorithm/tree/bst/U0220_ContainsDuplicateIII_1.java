package xyz.flysium.photon.algorithm.tree.bst;

import java.util.HashSet;
import java.util.Set;

/**
 * 220. 存在重复元素 III
 * <p>
 * https://leetcode-cn.com/problems/contains-duplicate-iii/
 *
 * @author zeno
 */
public interface U0220_ContainsDuplicateIII_1 {

  // 在整数数组 nums 中，是否存在两个下标 i 和 j，
  // 使得 nums [i] 和 nums [j] 的差的绝对值小于等于 t ，且满足 i 和 j 的差的绝对值也小于等于 ķ 。
  //
  // 如果存在则返回 true，不存在返回 false。
  //

  // 9 ms
  class Solution {

    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
      if (nums.length == 0 || k <= 0 || t < 0) {
        return false;
      }
      Set<Integer> s = new HashSet<>(k, 1);

      for (int i = 0; i < nums.length; i++) {
        if (t == 0) {
          if (s.contains(nums[i])) {
            return true;
          }
        } else {
          for (Integer e : s) {
            if (Math.abs((long) nums[i] - e) <= t) {
              return true;
            }
          }
        }
        s.add(nums[i]);
        if (i >= k) {
          s.remove(nums[i - k]);
        }
      }
      return false;
    }

  }

}
