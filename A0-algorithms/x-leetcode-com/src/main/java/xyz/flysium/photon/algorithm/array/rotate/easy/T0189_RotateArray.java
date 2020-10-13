package xyz.flysium.photon.algorithm.array.rotate.easy;

/**
 * 189. 旋转数组
 * <p>
 * https://leetcode-cn.com/problems/rotate-array/
 *
 * @author zeno
 */
public class T0189_RotateArray {

  // 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
  //  输入: [1,2,3,4,5,6,7] 和 k = 3
  //  输出: [5,6,7,1,2,3,4]
  static class Solution {

    public void rotate(int[] nums, int k) {
      final int t = k % nums.length;
      // reverse
      reverse(nums, 0, nums.length - 1);
      reverse(nums, 0, t - 1);
      reverse(nums, t, nums.length - 1);
    }

    private void reverse(int[] arr, int l, int r) {
      int length = r - l + 1;
      for (int i = 0; i < (length >> 1); i++) {
        swap(arr, l + i, r - i);
      }
    }

    private void swap(int[] arr, int x, int y) {
      if (x == y) {
        return;
      }
      arr[x] = arr[x] ^ arr[y];
      arr[y] = arr[x] ^ arr[y];
      arr[x] = arr[x] ^ arr[y];
    }

  }

}
