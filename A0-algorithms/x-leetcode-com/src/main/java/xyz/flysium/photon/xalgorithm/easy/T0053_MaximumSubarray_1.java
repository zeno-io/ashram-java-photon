package xyz.flysium.photon.xalgorithm.easy;

import xyz.flysium.photon.ArraySupport;

/**
 * 53. 最大子序和
 * <p>
 * https://leetcode-cn.com/problems/maximum-subarray/
 *
 * @author zeno
 */
public class T0053_MaximumSubarray_1 {

//给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
//
// 示例:
//
// 输入: [-2,1,-3,4,-1,2,1,-5,4]
//输出: 6
//解释:连续子数组[4,-1,2,1] 的和最大，为6。
//
//
// 进阶:
//
// 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
// Related Topics 数组 分治算法 动态规划
// 👍 2563 👎 0


  public static void main(String[] args) {
    Solution solution = new T0053_MaximumSubarray_1().new Solution();
    System.out.println(solution.maxSubArray(ArraySupport.newArray("[-2,1,-3,4,-1,2,1,-5,4]")));
  }

  // 执行用时：1 ms, 在所有 Java 提交中击败了95.79% 的用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int maxSubArray(int[] nums) {
      MyInfo info = maxSubArray(nums, 0, nums.length - 1);
      return info.maxSubArrayLR;
    }

    private MyInfo maxSubArray(int[] nums, int l, int r) {
      if (l == r) {
        return new MyInfo(nums[l], nums[l], nums[l], nums[l]);
      }
      int m = (l + r) >> 1;
      MyInfo lSub = maxSubArray(nums, l, m);
      MyInfo rSub = maxSubArray(nums, m + 1, r);
      int sumLR = lSub.sumLR + rSub.sumLR;
      int maxSubArrayL2 = Math.max(lSub.maxSubArrayL2, lSub.sumLR + rSub.maxSubArrayL2);
      int maxSubArray2R = Math.max(rSub.maxSubArray2R, lSub.maxSubArray2R + rSub.sumLR);
      int maxSubArrayLR = Math.max(Math.max(lSub.maxSubArrayLR, rSub.maxSubArrayLR),
        lSub.maxSubArray2R + rSub.maxSubArrayL2);
      return new MyInfo(maxSubArrayL2, maxSubArray2R, maxSubArrayLR,
        sumLR);
    }

    class MyInfo {

      //  [l,r]  内以 l 为左端点的最大子段和
      int maxSubArrayL2;
      //  [l,r] 内以 r 为右端点的最大子段和
      int maxSubArray2R;
      //  [l,r] 内的最大子段和
      int maxSubArrayLR;
      //  [l,r] 的区间和
      int sumLR;

      public MyInfo(int maxSubArrayL2, int maxSubArray2R, int maxSubArrayLR, int sumLR) {
        this.maxSubArrayL2 = maxSubArrayL2;
        this.maxSubArray2R = maxSubArray2R;
        this.maxSubArrayLR = maxSubArrayLR;
        this.sumLR = sumLR;
      }
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
