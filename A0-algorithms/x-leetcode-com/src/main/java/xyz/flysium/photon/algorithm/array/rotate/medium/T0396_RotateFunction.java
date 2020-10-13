package xyz.flysium.photon.algorithm.array.rotate.medium;

/**
 * 396. 旋转函数
 * <p>
 * https://leetcode-cn.com/problems/rotate-function/
 *
 * @author zeno
 */
public class T0396_RotateFunction {

  static class Solution {

    /**
     * 把数组逆转跟把乘数逆转是一样的，可以看出有如下规律
     * <p>
     * 4     3     2     6
     * <p>
     * 0*4   1*3   2*2   3*6   FH(0) = 25
     * <p>
     * 3*4   0*3   1*2   2*6   FH(1) = FH(0) - SUM(data) + N * data[0] = 25 - 15 + 4 * 4 = 26;
     * <p>
     * 2*4   3*3   0*2   1*6   FH(2) = FH(1) - SUM(data) + N * data[1] = 26 - 15 + 4 * 3 = 23;
     * <p>
     * 1*4   2*3   3*2   0*6   FH(3) = FH(2) - SUM(data) + N * data[2] = 23 - 15 + 4 * 2 = 16;
     *
     *
     * <pre>
     *   0*d(0) + 1*d(1)+ 2*d(2) + ...+ (n-1)*d(n-1) = fh(0)
     *   (n-1)*d(0) + 0*d(1) + 1*d(2) + ...+(n-2)*d(n-1) = fh(1)
     *
     *   fh(0) - fh(1) = (1-n)*d(0) + d(1) + d(2) ...+ d(n-1)
     *               = d(0) + d(1) + d(2) ...+ d(n-1) - n * d(0)
     *               = sum(n) - n * d(0)
     *   fh(1)  = f(0) - sum(n) + n * d(0)
     * </pre>
     */
    // 2ms
    public int maxRotateFunction(int[] A) {
      final int n = A.length;

      int sum = 0;
      int fh0 = 0;
      for (int i = 0; i < n; i++) {
        fh0 += i * A[i];
        sum += A[i];
      }
      int maxFh = fh0;
      int prevFh = fh0;
      for (int k = 1; k <= n - 1; k++) {
        int fh = prevFh - sum + (n * A[k - 1]);
        maxFh = Math.max(maxFh, fh);
        prevFh = fh;
      }
      return maxFh;
    }

  }

}
