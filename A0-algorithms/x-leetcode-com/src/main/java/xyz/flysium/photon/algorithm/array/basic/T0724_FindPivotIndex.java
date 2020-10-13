package xyz.flysium.photon.algorithm.array.basic;

/**
 * 724. 寻找数组的中心索引
 * <p>
 * https://leetcode-cn.com/problems/find-pivot-index
 *
 * @author zeno
 */
public interface T0724_FindPivotIndex {

  //给定一个整数类型的数组 nums，请编写一个能够返回数组 “中心索引” 的方法。
  //我们是这样定义数组 中心索引 的：数组中心索引的左侧所有元素相加的和等于右侧所有元素相加的和。
  //如果数组不存在中心索引，那么我们应该返回 -1。如果数组有多个中心索引，那么我们应该返回最靠近左边的那一个。
  class Solution {

    public int pivotIndex(int[] nums) {
      if (nums.length == 0) {
        return -1;
      }
      int sum = 0;
      for (int x : nums) {
        sum += x;
      }
      int leftSum = 0;
//      int[] sums = new int[nums.length];
//      sums[0] = nums[0];
//      for (int i = 1; i < nums.length; i++) {
//        sums[i] = sums[i - 1] + nums[i];
//      }
      for (int i = 0; i < nums.length; i++) {
//        if (((i == 0) ? 0 : sums[i - 1]) == (sums[nums.length - 1] - sums[i])) {
//          return i;
//        }
        if (leftSum == sum - leftSum - nums[i]) {
          return i;
        }
        leftSum += nums[i];
      }
      return -1;
    }

  }

}
