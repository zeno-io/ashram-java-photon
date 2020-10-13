package xyz.flysium.photon.algorithm.array.basic;

/**
 * 27. 移除元素
 * <p>
 * https://leetcode-cn.com/problems/remove-element/
 *
 * @author zeno
 */
public interface U0027_RemoveElement_1 {

  // 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
  // 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
  // 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
  class Solution {

    public int removeElement(int[] nums, int val) {
      final int length = nums.length;
      int x = 0, y = length - 1;

      while (x < y) {
        // remove last, find index which not equal val
        while (y >= 0 && nums[y] == val) {
          y--;
        }
        // try to swap if match val
        if (x < y && y >= 0 && nums[x] == val) {
          swap(nums, x, y);
          y--;
        }
        x++;
      }
      // remove last again
      while (y >= 0 && nums[y] == val) {
        y--;
      }

      return y + 1;
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
