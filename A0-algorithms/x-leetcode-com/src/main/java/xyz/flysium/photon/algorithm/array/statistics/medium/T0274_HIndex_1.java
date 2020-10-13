package xyz.flysium.photon.algorithm.array.statistics.medium;

import java.util.Arrays;

/**
 * 274. H 指数
 * <p>
 * https://leetcode-cn.com/problems/h-index/
 *
 * @author zeno
 */
public class T0274_HIndex_1 {

  // 给定一位研究者论文被引用次数的数组（被引用次数是非负整数）。编写一个方法，计算出研究者的 h 指数。
  // h 指数的定义：h 代表“高引用次数”（high citations），一名科研人员的 h 指数是指他（她）的 （N 篇论文中）总共有 h 篇论文分别被引用了至少 h 次。
  // （其余的 N - h 篇论文每篇被引用次数 不超过 h 次。）
  // 提示：如果 h 有多种可能的值，h 指数是其中最大的那个。
  static class Solution {

    public int hIndex(int[] citations) {
      if (citations.length == 0) {
        return 0;
      }
      if (citations.length == 1) {
        return citations[0] > 0 ? 1 : 0;
      }
      final int n = citations.length;
      //  sort
      Arrays.sort(citations);
      // h = 0
      if (citations[n - 1] <= 0) {
        return 0;
      }
      for (int h = 1; h < n; h++) {
        int i = n - h;
        if (citations[i] >= h && citations[i - 1] <= h) {
          return h;
        }
      }
      // h = n
      if (citations[0] >= n) {
        return n;
      }
      return 0;
    }

  }

}
