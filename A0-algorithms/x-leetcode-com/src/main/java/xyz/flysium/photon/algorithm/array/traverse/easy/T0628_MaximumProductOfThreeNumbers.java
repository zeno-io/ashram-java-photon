package xyz.flysium.photon.algorithm.array.traverse.easy;

/**
 * 628. 三个数的最大乘积
 * <p>
 * https://leetcode-cn.com/problems/maximum-product-of-three-numbers/
 *
 * @author zeno
 */
public class T0628_MaximumProductOfThreeNumbers {

  static class Solution {

    // 2ms
    public int maximumProduct(int[] nums) {
      if (nums.length == 3) {
        return nums[0] * nums[1] * nums[2];
      }
      int maxFirst = Integer.MIN_VALUE;
      int maxSecond = Integer.MIN_VALUE;
      int maxThird = Integer.MIN_VALUE;
      int minFirst = Integer.MAX_VALUE;
      int minSecond = Integer.MAX_VALUE;

      for (int num : nums) {
        if (num <= minFirst) {
          minSecond = minFirst;
          minFirst = num;
        } else if (num <= minSecond) {
          minSecond = num;
        }
        if (num >= maxFirst) {
          maxThird = maxSecond;
          maxSecond = maxFirst;
          maxFirst = num;
        } else if (num >= maxSecond) {
          maxThird = maxSecond;
          maxSecond = num;
        } else if (num >= maxThird) {
          maxThird = num;
        }
      }
      // 全是正数，最大的三个数乘积，有负数，则是 最大数与最小两个数 VS.最大的三个数乘积
      // 因此取 max（最大数与最小两个数 ， 最大的三个数乘积）
      return Math.max(maxFirst * maxSecond * maxThird, maxFirst * minFirst * minSecond);
    }

  }

}
