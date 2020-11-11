package xyz.flysium.photon.algorithm.array.traverse.medium;

/**
 * 1004. 最大连续1的个数 III
 * <p>
 * https://leetcode-cn.com/problems/max-consecutive-ones-iii/
 *
 * @author zeno
 */
public interface T1004_MaxConsecutiveOnesIII_1 {

  // 给定一个由若干 0 和 1 组成的数组 A，我们最多可以将 K 个值从 0 变成 1 。
  //
  // 返回仅包含 1 的最长（连续）子数组的长度。

  //   1 <= A.length <= 20000
  //  0 <= K <= A.length
  //    A[i] 为 0 或 1

  // 5ms 14.33%
  class Solution {

    public int longestOnes(int[] A, int K) {
      int zeroCount = 0;
      int right = -1;
      int ans = 0;
      // scan to right
      for (int left = 0; left < A.length; left++) {
        if (left > 0 && A[left - 1] == 0) {
          zeroCount--;
        }
        while (right + 1 < A.length) {
          // try add next, rollback if fail
          boolean b = A[right + 1] == 0;
          if (b) {
            zeroCount++;
          }
          // if forward, new capacity - max, if great than k, break
          if (zeroCount > K) {
            // rollback
            if (b) {
              zeroCount--;
            }
            break;
          }
          // forward to next position
          right++;
        }
        ans = Math.max(ans, right - left + 1);
      }
      return ans;
    }

    // 与 T0424_LongestRepeatingCharacterReplacement（424. 替换后的最长重复字符） 不同，这里左指针不能做到可以不回退
    //
    // 题424 是重复最长
    // 本题 是 固定1，如果换成 最长1或0 就一样了
//    public int longestOnes(int[] A, int K) {
//      int oneCount = 0;
//      int left = 0;
//      int max = 0;
//      for (int right = 0; right < A.length; right++) {
//        if (A[right] == 1) {
//          oneCount++;
//        }
//        while (right - left + 1 - oneCount > K) {
//          if (A[left] == 1) {
//            oneCount--;
//          }
//          left++;
//        }
//      }
//      return A.length - left;
//    }

  }

}
