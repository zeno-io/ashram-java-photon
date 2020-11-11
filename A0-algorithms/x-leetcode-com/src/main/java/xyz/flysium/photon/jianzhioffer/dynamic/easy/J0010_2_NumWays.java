package xyz.flysium.photon.jianzhioffer.dynamic.easy;

/**
 * 剑指 Offer 10- II. 青蛙跳台阶问题
 * <p>
 * https://leetcode-cn.com/problems/qing-wa-tiao-tai-jie-wen-ti-lcof/
 *
 * @author zeno
 */
public interface J0010_2_NumWays {

  // 一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
  //
  //答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
  //

  //执行用时：0 ms, 在所有 Java 提交中击败了100.00% 的用户
  class Solution {

    public int numWays(int n) {
      if (n == 0) {
        return 1;
      }
      if (n <= 2) {
        return n;
      }
      long[] fn = new long[n + 1];
      fn[1] = 1;
      fn[2] = 2;
      for (int i = 3; i < n; i++) {
        fn[i] = (fn[i - 1] + fn[i - 2]) % 1000000007;
      }
      return (int) ((fn[n - 1] + fn[n - 2]) % 1000000007);
    }

  }

}
