package xyz.flysium.photon.jianzhioffer.find.easy;

import xyz.flysium.photon.ArraySupport;

/**
 * 剑指 Offer 11. 旋转数组的最小数字
 * <p>
 * https://leetcode-cn.com/problems/xuan-zhuan-shu-zu-de-zui-xiao-shu-zi-lcof/
 *
 * @author zeno
 */
public class J0011_MinArray {

//把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。例如，数组 [3,4,5,1,2
//] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。
//
// 示例 1：
//
// 输入：[3,4,5,1,2]
//输出：1
//
//
// 示例 2：
//
// 输入：[2,2,2,0,1]
//输出：0
//
//
// 注意：本题与主站 154 题相同：https://leetcode-cn.com/problems/find-minimum-in-rotated-sor
//ted-array-ii/
// Related Topics 二分查找
// 👍 166 👎 0


  public static void main(String[] args) {
    Solution solution = new J0011_MinArray().new Solution();
    System.out.println(solution.minArray(ArraySupport.newArray("[7,8,9,1,2,3,4,5,6]")));
    System.out.println(solution.minArray(ArraySupport.newArray("[2,2,2,0,1]")));
  }

  // 执行耗时:0 ms,击败了100.00% 的Java用户
  // https://leetcode-cn.com/leetbook/read/illustration-of-algorithm/5055b1/
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int minArray(int[] numbers) {
      if (numbers.length == 1 || numbers[0] < numbers[numbers.length - 1]) {
        return numbers[0];
      }
      int low = 0;
      int high = numbers.length - 1;
      while (low < high) {
        // low <= mid < high
        int mid = (low + high) >>> 1;
        // x  on  right
        if (numbers[mid] > numbers[high]) {
          low = mid + 1;
        }
        // x  on  left
        else if (numbers[mid] < numbers[high]) {
          high = mid;
        } else {
          high--;
        }
      }
      return numbers[low];
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
