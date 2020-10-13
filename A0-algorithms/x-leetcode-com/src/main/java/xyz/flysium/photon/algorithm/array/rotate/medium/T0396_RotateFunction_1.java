package xyz.flysium.photon.algorithm.array.rotate.medium;

/**
 * 396. 旋转函数
 * <p>
 * https://leetcode-cn.com/problems/rotate-function/
 *
 * @author zeno
 */
public class T0396_RotateFunction_1 {

  static class Solution {

    public int maxRotateFunction(int[] A) {
      final int n = A.length;

      int maxFn = f(A, 0);
      for (int k = 1; k <= n - 1; k++) {
        int fn = f(A, n - k);
        maxFn = Math.max(maxFn, fn);
      }
      return maxFn;
    }

    private int f(int[] nums, int s) {
      int fn = 0;
      int f = 0;
      for (int i = s; i < nums.length; i++) {
        fn += f * nums[i];
        f++;
      }
      for (int i = 0; i < s; i++) {
        fn += f * nums[i];
        f++;
      }
      return fn;
    }

  }

}
