package xyz.flysium.photon.algorithm.array.traverse.easy;

/**
 * 414. 第三大的数
 * <p>
 * https://leetcode-cn.com/problems/third-maximum-number/
 *
 * @author zeno
 */
public class T0414_ThirdMaximumNumber_1 {

  static class Solution {

    public int thirdMax(int[] nums) {
      int maxIndexFirst = 0, maxIndexSecond = -1, maxIndexThird = -1;
      // find first Max Index
      for (int i = 0; i < nums.length; i++) {
        if (nums[i] > nums[maxIndexFirst]) {
          maxIndexFirst = i;
        }
      }
      // find second Max Index
      for (int i = 0; i < nums.length; i++) {
        // allow to equal index
        if (nums[i] < nums[maxIndexFirst]) {
          if (maxIndexSecond < 0 || nums[i] > nums[maxIndexSecond]) {
            maxIndexSecond = i;
          }
        }
      }
      // find third Max Index
      if (maxIndexSecond >= 0) {
        for (int i = 0; i < nums.length; i++) {
          // allow to equal index
          if (nums[i] < nums[maxIndexSecond]) {
            if ((maxIndexThird < 0 || nums[i] > nums[maxIndexThird])) {
              maxIndexThird = i;
            }
          }
        }
      }

      return maxIndexThird < 0 ? nums[maxIndexFirst] : nums[maxIndexThird];
    }

  }

}
