package xyz.flysium.photon.algorithm.array.prefix.medium;

/**
 * 238、除自身以外数组的乘积
 * <p>
 * https://leetcode-cn.com/problems/product-of-array-except-self/
 *
 * @author zeno
 */
public class T0238_ProductOfArrayExceptSelf {

  // 给你一个长度为 n 的整数数组 nums，其中 n > 1，返回输出数组 output ，其中 output[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积。
  // 提示：题目数据保证数组之中任意元素的全部前缀元素和后缀（甚至是整个数组）的乘积都在 32 位整数范围内。
  // 说明: 请不要使用除法，且在 O(n) 时间复杂度内完成此题。（ 出于对空间复杂度分析的目的，输出数组不被视为额外空间。）
  static class Solution {

    public int[] productExceptSelf(int[] nums) {
      final int n = nums.length;
      int[] ans = new int[n];

      int product = 1;
      for (int k = n - 1; k >= 0; k--) {
        product *= nums[k];
        ans[k] = product;
      }

      product = 1;
      for (int i = 0; i < n; i++) {
        ans[i] = (i == n - 1 ? 1 : ans[i + 1]) * product;
        product *= nums[i];
      }
      return ans;
    }

  }

}
