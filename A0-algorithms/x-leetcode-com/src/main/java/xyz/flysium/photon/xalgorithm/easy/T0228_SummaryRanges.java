package xyz.flysium.photon.xalgorithm.easy;

import java.util.LinkedList;
import java.util.List;

/**
 * 228. 汇总区间
 * <p>
 * https://leetcode-cn.com/problems/summary-ranges/
 *
 * @author zeno
 */
public class T0228_SummaryRanges {

//给定一个无重复元素的有序整数数组 nums 。
//
// 返回 恰好覆盖数组中所有数字 的 最小有序 区间范围列表。也就是说，nums 的每个元素都恰好被某个区间范围所覆盖，并且不存在属于某个范围但不属于 num
//s 的数字 x 。
//
// 列表中的每个区间范围 [a,b] 应该按如下格式输出：
//
//
// "a->b" ，如果 a != b
// "a" ，如果 a == b
//
//
//
//
// 示例 1：
//
// 输入：nums = [0,1,2,4,5,7]
//输出：["0->2","4->5","7"]
//解释：区间范围是：
//[0,2] --> "0->2"
//[4,5] --> "4->5"
//[7,7] --> "7"
//
//
// 示例 2：
//
// 输入：nums = [0,2,3,4,6,8,9]
//输出：["0","2->4","6","8->9"]
//解释：区间范围是：
//[0,0] --> "0"
//[2,4] --> "2->4"
//[6,6] --> "6"
//[8,9] --> "8->9"
//
//
// 示例 3：
//
// 输入：nums = []
//输出：[]
//
//
// 示例 4：
//
// 输入：nums = [-1]
//输出：["-1"]
//
//
// 示例 5：
//
// 输入：nums = [0]
//输出：["0"]
//
//
//
//
// 提示：
//
//
// 0 <= nums.length <= 20
// -231 <= nums[i] <= 231 - 1
// nums 中的所有值都 互不相同
//
// Related Topics 数组
// 👍 71 👎 0


  public static void main(String[] args) {
    Solution solution = new T0228_SummaryRanges().new Solution();

  }

  // 执行用时：0 ms, 在所有 Java 提交中击败了100.00% 的用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public List<String> summaryRanges(int[] nums) {
      int minIdx = 0;
      int maxIdx = 0;
      final int len = nums.length;
      List<String> ans = new LinkedList<>();
      while (minIdx < len) {
        maxIdx = minIdx;
        while (maxIdx + 1 < len && nums[maxIdx + 1] - 1 == nums[maxIdx]) {
          maxIdx = maxIdx + 1;
        }
        if (minIdx != maxIdx) {
          ans.add(
            new StringBuilder().append(nums[minIdx]).append("->").append(nums[maxIdx]).toString());
        } else {
          ans.add(String.valueOf(nums[minIdx]));
        }
        minIdx = maxIdx + 1;
      }
      return ans;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)
}

