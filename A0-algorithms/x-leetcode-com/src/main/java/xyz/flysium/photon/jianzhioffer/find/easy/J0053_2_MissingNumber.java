package xyz.flysium.photon.jianzhioffer.find.easy;

/**
 * 剑指 Offer 53 - II. 0～n-1中缺失的数字
 * <p>
 * https://leetcode-cn.com/problems/que-shi-de-shu-zi-lcof/
 *
 * @author zeno
 */
public class J0053_2_MissingNumber {

//一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。在范围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出
//这个数字。
//
//
//
// 示例 1:
//
// 输入: [0,1,3]
//输出: 2
//
//
// 示例 2:
//
// 输入: [0,1,2,3,4,5,6,7,9]
//输出: 8
//
//
//
// 限制：
//
// 1 <= 数组长度 <= 10000
// Related Topics 数组 二分查找
// 👍 71 👎 0


  public static void main(String[] args) {
    Solution solution = new J0053_2_MissingNumber().new Solution();

  }

  // 执行耗时:0 ms,击败了100.00% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int missingNumber(int[] nums) {
      int low = 0;
      int high = nums.length - 1;
      while (low <= high) {
        int mid = (low + high) >>> 1;
        if (nums[mid] == mid) {
          low = mid + 1;
        } else {
          high = mid - 1;
        }
      }
      return low;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
