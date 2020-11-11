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
      int last = 0;
      int i = 0;
      while (i < nums.length) {
        while (i < nums.length && nums[i] == nums[last]) {
          i++;
        }
        if (i < nums.length) {
          last++;
          nums[last] = nums[i];
        }
      }
      return last + 1;
    }
  }

}
