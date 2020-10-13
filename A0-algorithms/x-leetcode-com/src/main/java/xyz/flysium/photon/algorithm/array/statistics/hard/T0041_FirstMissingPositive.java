package xyz.flysium.photon.algorithm.array.statistics.hard;

/**
 * 41. 缺失的第一个正数
 * <p>
 * https://leetcode-cn.com/problems/first-missing-positive/
 *
 * @author zeno
 */
public class T0041_FirstMissingPositive {

  // 给你一个未排序的整数数组，请你找出其中没有出现的最小的正整数。
  // 你的算法的时间复杂度应为O(n)，并且只能使用常数级别的额外空间。
  static class Solution {

    public int firstMissingPositive(int[] nums) {
      // []
      if (nums == null || nums.length == 0) {
        return 1;
      }
      int limit = nums.length + 1;
      // set integers not in [1-N] to N+1
      for (int i = 0; i < nums.length; i++) {
        if (nums[i] <= 0 || nums[i] >= limit) {
          nums[i] = limit;
        }
      }
      int num = 0;
      int numIndex = 0;
      // when nums[i] occurs, nums[i] set to - Math.abs(nums[i])
      for (int i = 0; i < nums.length; i++) {
        num = Math.abs(nums[i]);
        if (num <= 0 || num >= limit) {
          continue;
        }
        numIndex = num - 1;
        if (nums[numIndex] > 0) {
          nums[numIndex] = -nums[numIndex];
        }
      }
      // if >0, it's the missing positive
      for (int i = 0; i < nums.length; i++) {
        if (nums[i] > 0) {
          return i + 1;
        }
      }
      return nums.length + 1;
    }

  }

}
