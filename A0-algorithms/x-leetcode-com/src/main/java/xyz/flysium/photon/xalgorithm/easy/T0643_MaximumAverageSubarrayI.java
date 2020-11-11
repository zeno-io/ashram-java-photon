package xyz.flysium.photon.xalgorithm.easy;

/**
 * 643. 子数组最大平均数 I
 * <p>
 * https://leetcode-cn.com/problems/maximum-average-subarray-i/
 *
 * @author zeno
 */
public class T0643_MaximumAverageSubarrayI {

//给定 n 个整数，找出平均数最大且长度为 k 的连续子数组，并输出该最大平均数。
//
// 示例 1:
//
// 输入: [1,12,-5,-6,50,3], k = 4
//输出: 12.75
//解释: 最大平均数 (12-5-6+50)/4 = 51/4 = 12.75
//
//
//
//
// 注意:
//
//
// 1 <= k <= n <= 30,000。
// 所给数据范围 [-10,000，10,000]。
//
// Related Topics 数组
// 👍 113 👎 0


  public static void main(String[] args) {
    Solution solution = new T0643_MaximumAverageSubarrayI().new Solution();

  }

  // 执行耗时:2 ms,击败了100.00% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public double findMaxAverage(int[] nums, int k) {

      int sum = 0;
      for (int i = 0; i < k; i++) {
        sum += nums[i];
      }
      int maxSum = sum;
      for (int i = k; i < nums.length; i++) {
        sum = sum + nums[i] - nums[i - k];
        maxSum = Math.max(maxSum, sum);
      }
      return maxSum * 1.0 / k;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
