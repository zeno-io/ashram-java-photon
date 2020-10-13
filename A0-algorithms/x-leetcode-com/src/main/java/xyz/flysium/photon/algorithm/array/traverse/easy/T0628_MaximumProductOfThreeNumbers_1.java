package xyz.flysium.photon.algorithm.array.traverse.easy;

/**
 * 628. 三个数的最大乘积
 * <p>
 * https://leetcode-cn.com/problems/maximum-product-of-three-numbers/
 *
 * @author zeno
 */
public class T0628_MaximumProductOfThreeNumbers_1 {

  static class Solution {

    // 3ms
    public int maximumProduct(int[] nums) {
      if (nums.length == 3) {
        return nums[0] * nums[1] * nums[2];
      }
      int[] ans = sort5(nums);
      // 全是正数，最大的三个数乘积，有负数，则是 最大数与最小两个数 VS.最大的三个数乘积
      // 因此取 max（最大数与最小两个数 ， 最大的三个数乘积）
      return Math.max(ans[ans.length - 1] * ans[ans.length - 2] * ans[ans.length - 3],
        ans[ans.length - 1] * ans[0] * ans[1]);
    }

    // another way is Bubble Sort O(N * 5), but more slow than this way O (N * K) (k = 2,3,4,5)
    private int[] sort5(int[] nums) {
      int maxIndexFirst = 0, maxIndexSecond = -1, maxIndexThird = -1;
      int minIndexFirst = -1, minIndexSecond = -1;

      // find first Max Index
      for (int i = 0; i < nums.length; i++) {
        if (nums[i] > nums[maxIndexFirst]) {
          maxIndexFirst = i;
        }
      }
      // find second Max Index
      for (int i = 0; i < nums.length; i++) {
        // not allow to equal index
        if (i == maxIndexFirst) {
          continue;
        }
        if (nums[i] <= nums[maxIndexFirst]) { // allow equal
          if (maxIndexSecond < 0 || nums[i] > nums[maxIndexSecond]) {
            maxIndexSecond = i;
          }
        }
      }
      // find third Max Index
      if (maxIndexSecond >= 0) {
        for (int i = 0; i < nums.length; i++) {
          // not allow to equal index
          if (i == maxIndexFirst || i == maxIndexSecond) {
            continue;
          }
          if (nums[i] <= nums[maxIndexSecond]) { // allow equal
            if ((maxIndexThird < 0 || nums[i] > nums[maxIndexThird])) {
              maxIndexThird = i;
            }
          }
        }
      }
      // find first Min Index
      for (int i = 0; i < nums.length; i++) {
        if (minIndexFirst < 0 || nums[i] < nums[minIndexFirst]) {
          minIndexFirst = i;
        }
      }
      // find second Min Index
      if (minIndexFirst >= 0) {
        for (int i = 0; i < nums.length; i++) {
          // not allow to equal index
          if (i == minIndexFirst) {
            continue;
          }
          if (nums[minIndexFirst] <= nums[i]) { // allow equal
            if (minIndexSecond < 0 || nums[i] < nums[minIndexSecond]) {
              minIndexSecond = i;
            }
          }
        }
      }
      int[] arr = new int[5];
      arr[0] = nums[maxIndexFirst];
      arr[1] = nums[maxIndexSecond];
      arr[2] = nums[maxIndexThird];
      int p = 3;
      if (minIndexSecond >= 0 && minIndexSecond != maxIndexSecond
        && minIndexSecond != maxIndexThird) {
        arr[p++] = nums[minIndexSecond];
      }
      if (minIndexFirst >= 0 && minIndexFirst != maxIndexSecond
        && minIndexFirst != maxIndexThird) {
        arr[p++] = nums[minIndexFirst];
      }
      // reverse
      int[] ans = new int[p];
      for (int i = 0; i < p; i++) {
        ans[i] = arr[p - 1 - i];
      }
      return ans;
    }

  }

}
