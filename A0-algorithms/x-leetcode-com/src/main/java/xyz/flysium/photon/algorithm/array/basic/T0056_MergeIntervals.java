package xyz.flysium.photon.algorithm.array.basic;

/**
 * 56. 合并区间
 * <p>
 * https://leetcode-cn.com/problems/merge-intervals/
 *
 * @author zeno
 */
public interface T0056_MergeIntervals {

  // 给出一个区间的集合，请合并所有重叠的区间。
  // 示例 1:
  // 输入: intervals = [[1,3],[2,6],[8,10],[15,18]]
  // 输出: [[1,6],[8,10],[15,18]]
  // 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
  class Solution {

    // 1ms
    public int[][] merge(int[][] intervals) {
      if (intervals.length == 0) {
        return new int[][]{};
      }
      if (intervals.length == 1) {
        return intervals;
      }
      int mc = 0;
      for (int i = 0; i < intervals.length - 1; i++) {
        for (int j = i + 1; j < intervals.length; j++) {
          if (intervals[i][0] <= intervals[j][1] && intervals[i][1] >= intervals[j][0]) {
            intervals[j][0] = Math.min(intervals[i][0], intervals[j][0]);
            intervals[j][1] = Math.max(intervals[i][1], intervals[j][1]);
            intervals[i] = null;
            mc++;
            break;
          }
        }
      }
      int[][] ans = new int[intervals.length - mc][];
      int k = 0;
      for (int[] interval : intervals) {
        if (interval == null) {
          continue;
        }
        ans[k++] = interval;
      }
      return ans;
    }

  }

}
