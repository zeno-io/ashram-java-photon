package xyz.flysium.photon.algorithm.array.move.easy;

/**
 * 453. 最小移动次数使数组元素相等
 * <p>
 * https://leetcode-cn.com/problems/minimum-moves-to-equal-array-elements/
 *
 * @author zeno
 */
public class T0453_MinimumMovesToEqualArrayElements {

  // 给定一个长度为 n 的非空整数数组，找到让数组所有元素相等的最小移动次数。每次移动将会使 n - 1 个元素增加 1。
  //  输入:
  //  [1,2,3]
  //  输出:
  //  3
  //  解释:
  //  只需要3次移动（注意每次移动会增加两个元素的值）：
  //  [1,2,3]  =>  [2,3,3]  =>  [3,4,3]  =>  [4,4,4]
  static class Solution {

    // 要使得每一个元素相等，每一步可以使n-1个元素增加1，相当于使1个元素减1。那么最快的方法当然是将较大的数减到和最小值相等。
    // 那么，需要的总次数就是每一个数和最小值的差值。
    public int minMoves(int[] nums) {
      if (nums.length <= 1) {
        return 0;
      }
      if (nums.length == 2) {
        return Math.max(nums[0], nums[1]) - Math.min(nums[0], nums[1]);
      }
      int min = Integer.MAX_VALUE;
      for (int num : nums) {
        if (num < min) {
          min = num;
        }
      }
      int ans = 0;
      for (int num : nums) {
        ans += (num - min);
      }
      return ans;
    }

  }

}
