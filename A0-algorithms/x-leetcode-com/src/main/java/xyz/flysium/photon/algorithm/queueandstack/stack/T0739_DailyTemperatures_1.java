package xyz.flysium.photon.algorithm.queueandstack.stack;

import java.util.Arrays;

/**
 * 739. 每日温度
 * <p>
 * https://leetcode-cn.com/problems/daily-temperatures/
 *
 * @author zeno
 */
public interface T0739_DailyTemperatures_1 {

  // 请根据每日 气温 列表，重新生成一个列表。对应位置的输出为：要想观测到更高的气温，至少需要等待的天数。如果气温在这之后都不会升高，请在该位置用 0 来代替。
  // 例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。
  // 提示：气温 列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，都是在 [30, 100] 范围内的整数。
  class Solution {

    // 16ms , O(N*M)
    public int[] dailyTemperatures(int[] T) {
      int[] ans = new int[T.length];
      int[] next = new int[101];
      Arrays.fill(next, Integer.MAX_VALUE);
      for (int i = T.length - 1; i >= 0; i--) {
        int t = T[i];
        int warmerMinIndex = Integer.MAX_VALUE;
        for (int k = t + 1; k <= 100; k++) {
          if (next[k] < warmerMinIndex) {
            warmerMinIndex = next[k];
          }
        }
        if (warmerMinIndex < Integer.MAX_VALUE) {
          ans[i] = warmerMinIndex - i;
        }
        next[t] = i;
      }

      return ans;
    }

    // 1414ms , O(N^2)
//    public int[] dailyTemperatures(int[] T) {
//      int[] ans = new int[T.length];
//
//      for (int i = 0; i < T.length; i++) {
//        for (int k = i; k < T.length; k++) {
//          if (T[k] > T[i]) {
//            ans[i] = k - i;
//            break;
//          }
//        }
//      }
//
//      return ans;
//    }

  }

}
