package xyz.flysium.photon.algorithm.array.statistics.easy;

/**
 * 697. 数组的度
 * <p>
 * https://leetcode-cn.com/problems/degree-of-an-array/
 *
 * @author zeno
 */
public class T0697_DegreeOfAnArray {

  //    nums.length 在1到50,000区间范围内。
  //    nums[i] 是一个在0到49,999范围内的整数。
  static class Solution {

    public int findShortestSubArray(int[] nums) {
      int[] count = new int[50001];
      int[] startIndex = new int[50001];
      int[] endIndex = new int[50001];
      for (int i = 0; i < startIndex.length; i++) {
        startIndex[i] = -1;
        endIndex[i] = -1;
      }
      for (int i = 0; i < nums.length; i++) {
        count[nums[i]]++;
        if (startIndex[nums[i]] == -1) {
          startIndex[nums[i]] = i;
        }
        endIndex[nums[i]] = i;
      }
      int maxDegree = 0;
      int ans = Integer.MAX_VALUE;
      for (int i = 0; i < count.length; i++) {
        if (count[i] > maxDegree) {
          maxDegree = count[i];
          ans = endIndex[i] - startIndex[i] + 1;
        } else if (count[i] == maxDegree && (endIndex[i] - startIndex[i] + 1) < ans) {
          ans = endIndex[i] - startIndex[i] + 1;
        }
      }
      return ans;
    }

//    public int findShortestSubArray(int[] nums) {
//      int[] stat = new int[50001];
//      int[] startIndex = new int[50001];
//      int[] endIndex = new int[50001];
//      for (int i = 0; i < startIndex.length; i++) {
//        startIndex[i] = -1;
//        endIndex[i] = -1;
//      }
//      for (int i = 0; i < nums.length; i++) {
//        stat[nums[i]]++;
//        if (startIndex[nums[i]] == -1) {
//          startIndex[nums[i]] = i;
//        }
//        endIndex[nums[i]] = i;
//      }
//      int degree = 0;
//      for (int s : stat) {
//        if (s > degree) {
//          degree = s;
//        }
//      }
//      int count = Integer.MAX_VALUE;
//      for (int i = 0; i < stat.length; i++) {
//        if (stat[i] == degree) {
//          count = Math.min(count, endIndex[i] - startIndex[i] + 1);
//        }
//      }
//      return count;
//    }

  }

}
