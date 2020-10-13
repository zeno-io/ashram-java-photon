package xyz.flysium.photon.algorithm.array.traverse.easy;

/**
 * 628. 三个数的最大乘积
 * <p>
 * https://leetcode-cn.com/problems/maximum-product-of-three-numbers/
 *
 * @author zeno
 */
public class T0628_MaximumProductOfThreeNumbers_2 {

  static class Solution {

    // 9ms
    public int maximumProduct(int[] nums) {
      // Bubble Sort
      // (0,1), (1,2), (2,3) .... (N-1,N)  0~N
      // (0,1), (1,2), (2,3) .... (N-2,N-1) 0 ~N-1
      // ...
      // find the first, second, third max number in [0, N]
      for (int k = 0; k < 3; k++) {
        for (int i = 1; i < nums.length - k; i++) {
          if (nums[i - 1] > nums[i]) {
            swap(nums, i, i - 1);
          }
        }
      }
      // find the first, second min number in [0, N-3]
      for (int k = 0; k < 2; k++) {
        for (int i = nums.length - 4; i >= k; i--) {
          if (i >= 1 && nums[i - 1] > nums[i]) {
            swap(nums, i, i - 1);
          }
        }
      }
      // 全是正数，最大的三个数乘积，有负数，则是 最大数与最小两个数 VS.最大的三个数乘积
      // 因此取 max（最大数与最小两个数 ， 最大的三个数乘积）
      return Math.max(nums[nums.length - 1] * nums[nums.length - 2] * nums[nums.length - 3],
        nums[nums.length - 1] * nums[0] * nums[1]);
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
