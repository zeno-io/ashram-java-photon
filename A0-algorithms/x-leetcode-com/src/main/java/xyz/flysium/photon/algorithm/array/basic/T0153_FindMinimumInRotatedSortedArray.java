package xyz.flysium.photon.algorithm.array.basic;

/**
 * 153. 寻找旋转排序数组中的最小值
 * <p>
 * https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array/
 *
 * @author zeno
 */
public interface T0153_FindMinimumInRotatedSortedArray {

  //  假设按照升序排序的数组在预先未知的某个点上进行了旋转。
  //  ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
  //  请找出其中最小的元素。
  //  你可以假设数组中不存在重复元素。
  class Solution {

    public int findMin(int[] nums) {
      if (nums.length == 1) {
        return nums[0];
      }
      int left = 0, right = nums.length - 1;
      // 如果已经是排序好的数组，直接返回结果
      if (nums[right] > nums[0]) {
        return nums[0];
      }
      // 二分查找
      while (left <= right) {
        int mid = left + ((right - left + 1) >> 1);

        // 元素比后一个大(反上升沿), 说明来到上升沿的最后
        if (nums[mid] > nums[mid + 1]) {
          return nums[mid + 1];
        }
        // 元素比前一个小(反上升沿), 说明来到上升沿的最后下一个
        if (nums[mid] < nums[mid - 1]) {
          return nums[mid];
        }
        // 如果比第一个元素大，说明是在左边上升沿，需要查找右边的上升沿
        if (nums[mid] > nums[0]) {
          left = mid + 1;
        } else {
          // 否则，查找左边的上升沿
          right = mid;
        }
      }

      return -1;
    }

//    public int findMin(int[] nums) {
//      int left = 0, right = 0;
//      for (int i = 0; i < nums.length; i++) {
//        left = i == 0 ? nums[nums.length - 1] : nums[i - 1];
//        right = i == nums.length - 1 ? nums[0] : nums[i + 1];
//        if (nums[i] <= left && nums[i] <= right) {
//          return nums[i];
//        }
//      }
//      return nums[0];
//    }

  }

}
