package xyz.flysium.photon.algorithm.array.basic;

/**
 * 26. 删除排序数组中的重复项
 * <p>
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/
 *
 * @author zeno
 */
public interface T0026_RemoveDuplicatesFromSortedArray {

  // 给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
  // 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
  class Solution {

    public int removeDuplicates(int[] nums) {
      if (nums.length == 0) {
        return 0;
      }
      if (nums.length == 1) {
        return 1;
      }
      int len = 0;
      int i = 0;
      int e = nums[1];
      while (i < nums.length) {
        e = nums[len];
        while (i < nums.length && nums[i] == e) {
          i++;
        }
        swap(nums, len, i - 1);
        len++;
      }

      return len;
    }

    public void swap(int[] array, int i, int j) {
      if (i == j) {
        return;
      }
      array[i] = array[i] ^ array[j];
      array[j] = array[i] ^ array[j];
      array[i] = array[i] ^ array[j];
    }

  }

}
