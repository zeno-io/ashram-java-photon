package xyz.flysium.photon.algorithm.array.statistics.easy;

/**
 * 645. 错误的集合
 * <p>
 * https://leetcode-cn.com/problems/set-mismatch/
 *
 * @author zeno
 */
public class T0645_SetMismatch {

  static class Solution {

    public int[] findErrorNums(int[] nums) {
      int[] count = new int[nums.length + 1];
      for (int num : nums) {
        count[num]++;
      }
      int duplicateNumber = 1;
      int missNumber = 1;
      for (int i = 1; i < count.length; i++) {
        if (count[i] == 2) {
          duplicateNumber = i;
        }
        if (count[i] == 0) {
          missNumber = i;
        }
      }
      return new int[]{duplicateNumber, missNumber};
    }

  }
}
