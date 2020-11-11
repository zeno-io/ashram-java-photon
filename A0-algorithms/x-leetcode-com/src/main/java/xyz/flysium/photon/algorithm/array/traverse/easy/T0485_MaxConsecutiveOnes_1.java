package xyz.flysium.photon.algorithm.array.traverse.easy;

/**
 * 485. 最大连续1的个数
 * <p>
 * https://leetcode-cn.com/problems/max-consecutive-ones/
 *
 * @author zeno
 */
public class T0485_MaxConsecutiveOnes_1 {

  static class Solution {

    public int findMaxConsecutiveOnes(int[] nums) {
      int left = 0;
      int right = 0;
      int ans = 0;
      // 滑动窗口
      while (right < nums.length) {
        if (nums[right] == 1) {
          right++;
        } else {
          ans = Math.max(right - left, ans);
          right++;
          left = right;
        }
      }
      return Math.max(right - left, ans);
    }

//    public int findMaxConsecutiveOnes(int[] nums) {
//      int count = 0;
//      int start = -1;
//      int end = -1;
//      int index = 0;
//      while (index < nums.length) {
//        if (nums[index] == 1 && (index == 0 || nums[index - 1] != 1)) {
//          start = index;
//          end = start + 1;
//          while (end < nums.length) {
//            if (nums[end] != 1) {
//              break;
//            }
//            end++;
//          }
//          count = Math.max(end - start, count);
//          index = end + 1;
//          continue;
//        }
//        index++;
//      }
//      return count;
//    }

  }

}
