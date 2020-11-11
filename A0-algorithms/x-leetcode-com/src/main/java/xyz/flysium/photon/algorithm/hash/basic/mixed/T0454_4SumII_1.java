package xyz.flysium.photon.algorithm.hash.basic.mixed;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 454. 四数相加 II
 * <p>
 * https://leetcode-cn.com/problems/4sum-ii/
 *
 * @author zeno
 */
public interface T0454_4SumII_1 {

  // 给定四个包含整数的数组列表 A , B , C , D ,计算有多少个元组 (i, j, k, l) ，使得 A[i] + B[j] + C[k] + D[l] = 0。
  //
  // 为了使问题简单化，所有的 A, B, C, D 具有相同的长度 N，且 0 ≤ N ≤ 500 。所有整数的范围在 -2^28 到 2^28 - 1 之间，最终结果不会超过 2^31 - 1 。
  //

  // 94ms 27%
  class Solution {

    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
      // 所有的 A, B, C, D 具有相同的长度 N，且 0 ≤ N ≤ 500 。
      int len = A.length;
      if (len == 0) {
        return 0;
      }
      Map<Integer, Integer> habsum = new HashMap<>(len * len, 1);
      Map<Integer, Integer> hcdsum = new HashMap<>(len * len, 1);
      AtomicInteger ans = new AtomicInteger();
      for (int a : A) {
        for (int b : B) {
          habsum.merge(a + b, 1, Integer::sum);
        }
      }
      for (int c : C) {
        for (int d : D) {
          hcdsum.merge(c + d, 1, Integer::sum);
        }
      }
      habsum.forEach((ab, tab) -> {
        Integer tcd = hcdsum.get(-ab);
        if (tcd != null) {
          ans.addAndGet(tab * tcd);
        }
      });
      return ans.get();
    }

  }

}
