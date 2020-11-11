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
      int left = -1;
      int right = 0;
      int ans = 0;
      // 滑动窗口
      while (right < nums.length) {
        if (nums[right] == 1) {
          right++;
        } else {
          ans = Math.max(ans, right - left - 1);
          left = right;
          right++;
        }
      }
      if (nums[right - 1] == 1) {
        ans = Math.max(ans, right - left - 1);
      }
      return ans;
    }

  }

}
