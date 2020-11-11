package xyz.flysium.photon.algorithm.hash.basic.beginner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 202. 快乐数
 * <p>
 * https://leetcode-cn.com/problems/happy-number/
 *
 * @author zeno
 */
public interface T0202_HappyNumber {

  // 编写一个算法来判断一个数 n 是不是快乐数。
  //
  //「快乐数」定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，
  // 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
  // 如果 可以变为  1，那么这个数就是快乐数。
  //
  //如果 n 是快乐数就返回 True ；不是，则返回 False 。
  //

  // 1ms 100.00%
  class Solution {

    private static final int[] RES = new int[]{0, 1, 4, 9, 16, 25, 36, 49, 64, 81};
    private static final Set<Integer> NOT_HAPPY = new HashSet<>(Arrays.asList(2, 3, 4, 5, 6, 8, 9));

    public boolean isHappy(int n) {
      // n -> .... -> 1...0  -> 1
      if (n == 1 || n == 7) {
        return true;
      }
      if (NOT_HAPPY.contains(n)) {
        return false;
      }
      Set<Integer> s = new HashSet<>();
      while (n != 1) {
        if (!s.add(n)) {
          return false;
        }
        int tmp = 0;
        while (n > 0) {
          int d = n % 10;
          tmp += RES[d];
          n = n / 10;
        }
        n = tmp;
      }
      return true;
    }

  }

}
