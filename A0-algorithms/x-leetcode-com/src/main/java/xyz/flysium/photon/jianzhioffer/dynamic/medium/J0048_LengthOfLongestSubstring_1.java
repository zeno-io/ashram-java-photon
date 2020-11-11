package xyz.flysium.photon.jianzhioffer.dynamic.medium;

/**
 * 剑指 Offer 48. 最长不含重复字符的子字符串
 * <p>
 * https://leetcode-cn.com/problems/zui-chang-bu-han-zhong-fu-zi-fu-de-zi-zi-fu-chuan-lcof/
 *
 * @author zeno
 */
public class J0048_LengthOfLongestSubstring_1 {

//请从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度。
//
//
//
// 示例 1:
//
// 输入: "abcabcbb"
//输出: 3
//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
//
//
// 示例 2:
//
// 输入: "bbbbb"
//输出: 1
//解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
//
//
// 示例 3:
//
// 输入: "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是"wke"，所以其长度为 3。
//    请注意，你的答案必须是 子串 的长度，"pwke"是一个子序列，不是子串。
//
//
//
//
// 提示：
//
//
// s.length <= 40000
//
//
// 注意：本题与主站 3 题相同：https://leetcode-cn.com/problems/longest-substring-without-rep
//eating-characters/
// Related Topics 哈希表 双指针 Sliding Window
// 👍 104 👎 0


  public static void main(String[] args) {
    Solution solution = new J0048_LengthOfLongestSubstring_1().new Solution();
    System.out.println(solution.lengthOfLongestSubstring("abcbacbb"));
    System.out.println(solution.lengthOfLongestSubstring("au"));
  }

  // 执行耗时:2 ms,击败了100.00% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int lengthOfLongestSubstring(String s) {
      if (s == null || s.length() == 0) {
        return 0;
      }
      if (s.length() == 1) {
        return 1;
      }
      final int length = s.length();
      int ans = 1;

      boolean[] hash = new boolean[128]; // ASCII
      int right = -1;
      // [left, right]
      for (int left = 0; left < length; left++) {
        if (left > 0) {
          hash[s.charAt(left - 1)] = false;
        }
        while (right + 1 < length && !hash[s.charAt(right + 1)]) {
          hash[s.charAt(right + 1)] = true;
          right++;
        }
        ans = Math.max(ans, right - left + 1);
      }

      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)


}
