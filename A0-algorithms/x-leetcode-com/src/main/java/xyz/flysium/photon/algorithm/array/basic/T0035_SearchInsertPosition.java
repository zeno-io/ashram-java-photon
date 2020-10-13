package xyz.flysium.photon.algorithm.array.basic;

/**
 * 35. 搜索插入位置
 * <p>
 * https://leetcode-cn.com/problems/search-insert-position/
 *
 * @author zeno
 */
public interface T0035_SearchInsertPosition {

  //  给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
  //  你可以假设数组中无重复元素。
  class Solution {

    public int searchInsert(int[] nums, int target) {
      int index = nums.length;
      for (int i = 0; i < nums.length; i++) {
        if (nums[i] >= target) {
          index = i;
          break;
        }
      }
      return index;
    }

  }

}
