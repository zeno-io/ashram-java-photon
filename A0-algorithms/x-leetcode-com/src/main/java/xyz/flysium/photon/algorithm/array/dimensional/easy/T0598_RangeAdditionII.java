package xyz.flysium.photon.algorithm.array.dimensional.easy;

/**
 * 598. 范围求和 II
 * <p>
 * https://leetcode-cn.com/problems/range-addition-ii/
 *
 * @author zeno
 */
public class T0598_RangeAdditionII {

  static class Solution {

    public int maxCount(int m, int n, int[][] ops) {
      int minRows = m;
      int minCols = n;
      for (int[] op : ops) {
        minRows = Math.min(op[0], minRows);
        minCols = Math.min(op[1], minCols);
      }
      return minRows * minCols;
    }

  }

}
