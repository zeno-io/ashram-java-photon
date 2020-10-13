package xyz.flysium.photon.algorithm.array.traverse.easy;

/**
 * 485. 最大连续1的个数
 * <p>
 * https://leetcode-cn.com/problems/max-consecutive-ones/
 *
 * @author zeno
 */
public class T0485_MaxConsecutiveOnes {

  static class Solution {

    public int findMaxConsecutiveOnes(int[] nums) {
      int x = -1;
      int y = 0;
      int count = 0;
      while (y < nums.length) {
        if (nums[y] == 0) {
          count = Math.max(count, y - 1 - x);
          x = y;
        }
        y++;
      }
      if (nums[y - 1] == 1) {
        count = Math.max(count, y - 1 - x);
      }

      return count;
    }

  }

}
