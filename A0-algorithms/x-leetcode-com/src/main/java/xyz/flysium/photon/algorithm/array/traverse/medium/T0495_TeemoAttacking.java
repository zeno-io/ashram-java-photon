package xyz.flysium.photon.algorithm.array.traverse.medium;

/**
 * 495. 提莫攻击
 * <p>
 * https://leetcode-cn.com/problems/teemo-attacking/
 *
 * @author zeno
 */
public class T0495_TeemoAttacking {

  static class Solution {

    //    你可以假定时间序列数组的总长度不超过 10000。
    //    你可以假定提莫攻击时间序列中的数字和提莫攻击的中毒持续时间都是非负整数，并且不超过 10,000,000。
    public int findPoisonedDuration(int[] timeSeries, int duration) {
      if (timeSeries == null || timeSeries.length == 0) {
        return 0;
      }
      if (timeSeries.length == 1) {
        return duration;
      }
      int total = duration;
      for (int index = 1; index < timeSeries.length; index++) {
//        if (timeSeries[index] - timeSeries[index - 1] < duration) {
//          total += timeSeries[index] - timeSeries[index - 1];
//        } else {
//          total += duration;
//        }
        total += Math.min(timeSeries[index] - timeSeries[index - 1], duration);
      }
      return (int) total;
    }

  }

}
