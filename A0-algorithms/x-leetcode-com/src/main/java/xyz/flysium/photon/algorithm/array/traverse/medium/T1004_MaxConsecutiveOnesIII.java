package xyz.flysium.photon.algorithm.array.traverse.medium;

/**
 * 1004. 最大连续1的个数 III
 * <p>
 * https://leetcode-cn.com/problems/max-consecutive-ones-iii/
 *
 * @author zeno
 */
public interface T1004_MaxConsecutiveOnesIII {

  // 给定一个由若干 0 和 1 组成的数组 A，我们最多可以将 K 个值从 0 变成 1 。
  //
  // 返回仅包含 1 的最长（连续）子数组的长度。

  //   1 <= A.length <= 20000
  //  0 <= K <= A.length
  //    A[i] 为 0 或 1

  // 2ms 100%
  class Solution {

    public int longestOnes(int[] A, int K) {
      int left = 0;
      int zeroCount = 0;
      for (int right = 0; right < A.length; right++) {
        if (A[right] == 0) {
          zeroCount++;
        }
        if (zeroCount > K) {
          if (A[left] == 0) {
            zeroCount--;
          }
          left++;
        }
      }
      return A.length - left;
    }

  }

}
