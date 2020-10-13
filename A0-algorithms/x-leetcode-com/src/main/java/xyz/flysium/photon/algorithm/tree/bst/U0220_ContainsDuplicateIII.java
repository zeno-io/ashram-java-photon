package xyz.flysium.photon.algorithm.tree.bst;

import java.util.TreeSet;

/**
 * 220. 存在重复元素 III
 * <p>
 * https://leetcode-cn.com/problems/contains-duplicate-iii/
 *
 * @author zeno
 */
public interface U0220_ContainsDuplicateIII {

  // 在整数数组 nums 中，是否存在两个下标 i 和 j，
  // 使得 nums [i] 和 nums [j] 的差的绝对值小于等于 t ，且满足 i 和 j 的差的绝对值也小于等于 ķ 。
  //
  // 如果存在则返回 true，不存在返回 false。
  //

  // 40 ms
  class Solution {

    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
      if (nums.length == 0 || k <= 0 || t < 0) {
        return false;
      }
      TreeSet<Long> s = new TreeSet<>();

      for (int i = 0; i < nums.length; i++) {
        Long succ = s.ceiling((long) nums[i]);
        if (succ != null && succ <= nums[i] + t) {
          return true;
        }
        Long pred = s.floor((long) nums[i]);
        if (pred != null && nums[i] <= pred + t) {
          return true;
        }
        s.add((long) nums[i]);
        if (i >= k) {
          s.remove((long) nums[i - k]);
        }
      }
      return false;
    }

  }

}
