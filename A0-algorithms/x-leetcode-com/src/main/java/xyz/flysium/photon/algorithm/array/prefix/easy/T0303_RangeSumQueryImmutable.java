package xyz.flysium.photon.algorithm.array.prefix.easy;

/**
 * 303. 区域和检索 - 数组不可变
 * <p>
 * https://leetcode-cn.com/problems/range-sum-query-immutable/
 *
 * @author zeno
 */
public class T0303_RangeSumQueryImmutable {

  /**
   * Your NumArray object will be instantiated and called as such:
   * <p>
   * NumArray obj = new NumArray(nums);
   * <p>
   * int param_1 = obj.sumRange(i,j);
   */
  static class NumArray {

    private final int[] sums;

    public NumArray(int[] nums) {
      this.sums = nums;
      int sum = 0;
      for (int i = 0; i < nums.length; i++) {
        sum += nums[i];
        sums[i] = sum;
      }
    }

    public int sumRange(int i, int j) {
      // sums(0) = sum(nums(0))
      // sums(1) = sum(nums(0)+nums(1))
      // sums(2) = sum(nums(0)+nums(1)+nums(2))
      // sums(3) = sum(nums(0)+nums(1)+nums(2)+nums(3))
      //...
      // sum(i,j) =  sum(nums(i)+...+nums(j) = sum(nums(0)+nums(1)+...nums(j)) - sums(nums(0)+nums(1)+..nums(i-1)) = sums(j) - sums(i-1)
      return sums[j] - (i == 0 ? 0 : sums[i - 1]);
    }

  }


}
