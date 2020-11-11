package xyz.flysium.photon.jianzhioffer.twopointers.easy;

/**
 * 剑指 Offer 21. 调整数组顺序使奇数位于偶数前面
 * <p>
 * https://leetcode-cn.com/problems/diao-zheng-shu-zu-shun-xu-shi-qi-shu-wei-yu-ou-shu-qian-mian-lcof/
 *
 * @author zeno
 */
public class J0021_Exchange_1 {

//输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分。
//
//
//
// 示例：
//
// 输入：nums =[1,2,3,4]
//输出：[1,3,2,4]
//注：[3,1,2,4] 也是正确的答案之一。
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 50000
// 1 <= nums[i] <= 10000
//
// 👍 49 👎 0


  public static void main(String[] args) {
    Solution solution = new J0021_Exchange_1().new Solution();

  }

  // 执行耗时:3 ms,击败了33.50% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int[] exchange(int[] nums) {
      final int length = nums.length;
      int ot = -1;
      int et = length;
      int i = 0;
      while (i < et) {
        int num = nums[i];
        // odd
        if ((num & 1) == 1) {
          swap(nums, i, ++ot);
          i++;
        } else {
          swap(nums, i, --et);
        }
      }
      return nums;
    }

    private void swap(int[] array, int i, int j) {
      if (i == j) {
        return;
      }
      array[i] = array[i] ^ array[j];
      array[j] = array[i] ^ array[j];
      array[i] = array[i] ^ array[j];
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
