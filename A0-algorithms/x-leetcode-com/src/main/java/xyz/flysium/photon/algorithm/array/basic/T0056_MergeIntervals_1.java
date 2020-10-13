package xyz.flysium.photon.algorithm.array.basic;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 56. 合并区间
 * <p>
 * https://leetcode-cn.com/problems/merge-intervals/
 *
 * @author zeno
 */
public interface T0056_MergeIntervals_1 {

  class Solution {

    public int[][] merge(int[][] intervals) {
      if (intervals.length == 0) {
        return new int[][]{};
      }
      if (intervals.length == 1) {
        return intervals;
      }
      Arrays.sort(intervals, (o1, o2) -> Integer.compare(o1[0], o2[0]));
      int rmax = -1;
      for (int i = 0; i < intervals.length; i++) {
        if (rmax < 0 || intervals[i][1] > intervals[rmax][1]) {
          rmax = i;
        }
      }
      if (intervals[0][1] >= intervals[rmax][0]) {
        return new int[][]{new int[]{intervals[0][0], intervals[rmax][1]}};
      }
      int[] left = intervals[0];
      int[] right = intervals[rmax];
      LinkedList<int[]> c = new LinkedList<>();
      for (int i = 1; i < intervals.length - 1; i++) {
        if (intervals[i][0] > left[1] && intervals[i][1] < right[0]) {
          c.add(left);
          left = intervals[i];
        } else if (intervals[i][0] <= left[1] && intervals[i][1] >= right[0]) {
          c.add(new int[]{left[0], right[1]});
          left = null;
          right = null;
          break;
        } else if (intervals[i][0] <= left[1]) {
          left[1] = Math.max(intervals[i][1], left[1]);
        } else if (intervals[i][1] >= right[0]) {
          right[0] = Math.min(intervals[i][0], right[0]);
        }
      }
      if (left != null) {
        c.add(left);
      }
      if (right != null) {
        c.add(right);
      }

      int[][] ans = new int[c.size()][];
      c.toArray(ans);
      return ans;
    }

  }

}
