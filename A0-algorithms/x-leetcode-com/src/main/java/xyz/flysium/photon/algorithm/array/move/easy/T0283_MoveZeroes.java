package xyz.flysium.photon.algorithm.array.move.easy;

/**
 * 283. 移动零
 * <p>
 * https://leetcode-cn.com/problems/move-zeroes/
 *
 * @author zeno
 */
public class T0283_MoveZeroes {

  // 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
  static class Solution {

    // 0ms
    public void moveZeroes(int[] nums) {
      int last = 0;
      for (int i = 0; i < nums.length; i++) {
        if (nums[i] != 0) {
          nums[last] = nums[i];
          last++;
        }
      }
      for (int i = last; i < nums.length; i++) {
        nums[i] = 0;
      }
    }

  }

}
