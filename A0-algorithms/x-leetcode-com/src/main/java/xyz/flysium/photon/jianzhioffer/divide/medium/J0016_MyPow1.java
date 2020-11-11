package xyz.flysium.photon.jianzhioffer.divide.medium;

/**
 * 剑指 Offer 16. 数值的整数次方
 * <p>
 * https://leetcode-cn.com/problems/shu-zhi-de-zheng-shu-ci-fang-lcof/
 *
 * @author zeno
 */
public class J0016_MyPow1 {

//实现函数double Power(double base, int exponent)，求base的exponent次方。不得使用库函数，同时不需要考虑大数
//问题。
//
//
//
// 示例 1:
//
// 输入: 2.00000, 10
//输出: 1024.00000
//
//
// 示例 2:
//
// 输入: 2.10000, 3
//输出: 9.26100
//
//
// 示例 3:
//
// 输入: 2.00000, -2
//输出: 0.25000
//解释: 2 ^ -2 = 1/2 ^ 2 = 1/4 = 0.25
//
//
//
// 说明:
//
//
// -100.0 < x < 100.0
// n 是 32 位有符号整数，其数值范围是 [−2^31, 2^31 − 1] 。
//
//
// 注意：本题与主站 50 题相同：https://leetcode-cn.com/problems/powx-n/
// Related Topics 递归
// 👍 85 👎 0


  public static void main(String[] args) {
    Solution solution = new J0016_MyPow1().new Solution();
    System.out.println(solution.myPow(2.0, 10));
    System.out.println(solution.myPow(0.00001, 2147483647));
    System.out.println(solution.myPow(1.0, 2147483647));
    System.out.println(solution.myPow(2, -2147483648));
    System.out.println(solution.myPow(-1.0, -2147483648));
  }

  // 执行耗时:5 ms,击败了95.65% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public double myPow(double x, int n) {
      if (n == 0) {
        return 1.0;
      }
      return pow(x, n);
    }

    // long for -2147483648
    private double pow(double x, long n) {
      if (x == 0) {
        return 0;
      } else if (x == 1) {
        return 1;
      } else if (x == -1) {
        return Math.abs(n) % 2 == 0 ? 1 : -1;
      }
      if (n < 0) {
        return pow(1 / x, -n);
      } else if (n == 0) {
        return 1.0;
      } else if (n == 1) {
        return x;
      } else if (n == 2) {
        return x * x;
      }
      // avoid StackOverflowError
      if (n < 1000) {
        double s = 1;
        for (long i = 0; i < n; i++) {
          s *= x;
        }
        return s;
      }
      long h = n >> 1;
      double a = pow(x, h);
      if (a == 0) {
        return 0;
      }
      return a * pow(x, n - h);
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
