package xyz.flysium.photon.algorithm.array.move.easy;

/**
 * 283. 移动零
 * <p>
 * https://leetcode-cn.com/problems/move-zeroes/
 *
 * @author zeno
 */
public class T0283_MoveZeroes_1 {

  // 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
  static class Solution {

    // 5ms
    public void moveZeroes(int[] nums) {
      for (int i = 0; i < nums.length; i++) {
        if (nums[i] == 0) {
          int notZero = i + 1;
          while (notZero < nums.length) {
            if (nums[notZero] != 0) {
              break;
            }
            notZero++;
          }
          if (notZero < nums.length) {
            swap(nums, i, notZero);
          }
        }
      }
    }

    private void swap(int[] arr, int x, int y) {
      if (x == y) {
        return;
      }
      arr[x] = arr[x] ^ arr[y];
      arr[y] = arr[x] ^ arr[y];
      arr[x] = arr[x] ^ arr[y];
    }

  }

}
