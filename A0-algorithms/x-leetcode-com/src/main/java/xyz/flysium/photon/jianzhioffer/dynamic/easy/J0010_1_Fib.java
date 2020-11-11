package xyz.flysium.photon.jianzhioffer.dynamic.easy;

/**
 * 剑指 Offer 10- I. 斐波那契数列
 * <p>
 * https://leetcode-cn.com/problems/fei-bo-na-qi-shu-lie-lcof/
 *
 * @author zeno
 */
public interface J0010_1_Fib {

  // 写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项。斐波那契数列的定义如下：

  // F(0) = 0,   F(1) = 1
  // F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
  //
  // 斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。
  //
  // 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
  //
  //0 <= n <= 100


  // 执行用时：0 ms, 在所有 Java 提交中击败了100.00% 的用户
  class Solution {

    public int fib(int n) {
      if (n <= 1) {
        return n;
      }
      // n=47 就超过 int的最大值, n=93 就超过 long 的最大值
      //  求余运算规则： 设正整数 x,y,p   (x+y)%p = (x%p + y%p) %p;
      //   fn = (   f(n-1) % p + f(n-2) % p ) % p
      long[] fn = new long[n + 1];
      fn[0] = 0;
      fn[1] = 1;
      for (int i = 2; i < n; i++) {
        fn[i] = (fn[i - 1] + fn[i - 2]) % 1000000007;
      }
      return (int) ((fn[n - 1] + fn[n - 2]) % 1000000007);
    }

  }

}
