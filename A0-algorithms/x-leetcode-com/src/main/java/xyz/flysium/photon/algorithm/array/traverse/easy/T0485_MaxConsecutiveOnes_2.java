package xyz.flysium.photon.algorithm.array.traverse.easy;

/**
 * 485. 最大连续1的个数
 * <p>
 * https://leetcode-cn.com/problems/max-consecutive-ones/
 *
 * @author zeno
 */
public class T0485_MaxConsecutiveOnes_2 {

  // 4ms 8.80%
  static class Solution {

    public int findMaxConsecutiveOnes(int[] nums) {
      int left = 0;
      int right = 0;
      int ans = 0;
      // 滑动窗口
      while (right < nums.length) {
        int n = nums[right];
        right++;
        if (n != 1) {
          left = right;
          continue;
        }
        //System.out.println(String.format("window: [%d, %d)\n", left, right));
        while (nums[left] != 1) {
          left++;
        }
        ans = Math.max(ans, right - left);
      }
      return ans;
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
