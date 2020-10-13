package xyz.flysium.photon.algorithm.hash.basic;

/**
 * 1. 两数之和
 * <p>
 * https://leetcode-cn.com/problems/two-sum/
 *
 * @author zeno
 */
public interface T0001_TwoSum {

  // 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
  //
  // 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。

  // 0ms 100.00%
  class Solution {

    private static final int CAPACITY = 2048;
    private static final int XOR = CAPACITY - 1;

    public int[] twoSum(int[] nums, int target) {
//      Map<Integer, Integer> hash = new HashMap<>(nums.length);
      int[] map = new int[CAPACITY];
      // 你可以假设每种输入只会对应一个答案 -> not duplicate
      for (int i = 0; i < nums.length; i++) {
//        int index = indexOf(target - nums[i]);
        int index = (target - nums[i]) & XOR;
        if (map[index] > 0) {
          return new int[]{map[index] - 1, i};
        }
        // value = index + 1
//        map[indexOf(nums[i])] = i + 1;
        map[nums[i] & XOR] = i + 1;
      }
      return new int[]{};
    }

//    private int indexOf(int value) {
//      return value & XOR;
//    }

  }

}
