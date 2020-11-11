package xyz.flysium.photon.algorithm.array.basic;

import java.util.Arrays;

/**
 * 80. 删除排序数组中的重复项 II
 * <p>
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array-ii/
 *
 * @author zeno
 */
public class T0080_RemoveDuplicatesFromSortedArrayII {

//给定一个增序排列数组 nums ，你需要在 原地 删除重复出现的元素，使得每个元素最多出现两次，返回移除后数组的新长度。
//
// 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
//
//
//
// 说明：
//
// 为什么返回数值是整数，但输出的答案是数组呢？
//
// 请注意，输入数组是以“引用”方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
//
// 你可以想象内部操作如下：
//
//
//// nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
//int len = removeDuplicates(nums);
//
//// 在函数里修改输入数组对于调用者是可见的。
//// 根据你的函数返回的长度, 它会打印出数组中该长度范围内的所有元素。
//for (int i = 0; i < len; i++) {
//  print(nums[i]);
//}
//
//
//g
// 示例 1：
//
//
//输入：nums = [1,1,1,2,2,3]
//输出：5, nums = [1,1,2,2,3]
//解释：函数应返回新长度 length = 5, 并且原数组的前五个元素被修改为 1, 1, 2, 2, 3 。 你不需要考虑数组中超出新长度后面的元素。
//
//
// 示例 2：
//
//
//输入：nums = [0,0,1,1,1,1,2,3,3]
//输出：7, nums = [0,0,1,1,2,3,3]
//解释：函数应返回新长度 length = 7, 并且原数组的前五个元素被修改为0, 0, 1, 1, 2, 3, 3 。 你不需要考虑数组中超出新长度后面
//的元素。
//
//
//
//
// 提示：
//
//
// 0 <= nums.length <= 3 * 104
// -104 <= nums[i] <= 104
// nums 按递增顺序排列
//
// Related Topics 数组 双指针
// 👍 301 👎 0


  public static void main(String[] args) {
    Solution solution = new T0080_RemoveDuplicatesFromSortedArrayII().new Solution();
    int[] arr = {1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3};
    int res = solution.removeDuplicates(arr);
    System.out.println(Arrays.toString(Arrays.copyOfRange(arr, 0, res)));
  }

  // 执行耗时:1 ms,击败了96.27% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int removeDuplicates(int[] nums) {
      int last = 0;
      int i = 0;
      int cnt = 0;
      int prevNumber = 0;
      while (i < nums.length) {
        prevNumber = nums[last];
        cnt = 0;
        while (i < nums.length && nums[i] == prevNumber) {
          i++;
          cnt++;
        }
        if (i < nums.length) {
          last++;
          if (cnt >= 2) {
            nums[last] = prevNumber;
            last++;
          }
          nums[last] = nums[i];
        }
      }
      if (cnt >= 2) {
        last++;
        nums[last] = prevNumber;
      }
      return last + 1;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
