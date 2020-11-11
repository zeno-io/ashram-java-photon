package xyz.flysium.photon.jianzhioffer.dynamic.medium;

/**
 * 剑指 Offer 48. 最长不含重复字符的子字符串
 * <p>
 * https://leetcode-cn.com/problems/zui-chang-bu-han-zhong-fu-zi-fu-de-zi-zi-fu-chuan-lcof/
 *
 * @author zeno
 */
public class J0048_LengthOfLongestSubstring {

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
    Solution solution = new J0048_LengthOfLongestSubstring().new Solution();
    System.out.println(solution.lengthOfLongestSubstring("abcbacbb"));
    System.out.println(solution.lengthOfLongestSubstring("au"));
  }

  // 执行耗时:3 ms,击败了98.50% 的Java用户
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

      // dp[x] 表示以第x索引为结尾的字符串中最长的不重复的子串的长度
      // 固定右边界 j ，设字符 s[j] 左边距离最近的相同字符为 s[i]，即 s[i] = s[j]
      // (1) i <0 ，即 s[j] 左边无相同字符，则 dp[j]=dp[j−1]+1
      // (2) d[j-1] < j-i  说明 s[i] 在 d[j-1]的子串区间之外（左边或右边）  d[i] = d[j-1] + 1
      // (3) d[j-1] >= j-i 说明 s[i] 在 d[j-1]的子串区间之中 ，则 dp[j] 的左边界由 s[i] 决定，  d[i] = j-i
      // i <0 -> d[j-1] < j -> d[j-1] < j - i, 合并分支1，2
      int[] dp = new int[length];
      dp[0] = 1;
      int ans = 1;

      int[] hash = new int[128];
      hash[s.charAt(0)] = 1;

      for (int j = 1; j < length; j++) {
        char c = s.charAt(j);
        int i = hash[c] - 1;
        if (i < 0 || dp[j - 1] < j - i) {
          dp[j] = dp[j - 1] + 1;
        } else {
          dp[j] = j - i;
        }
        ans = Math.max(ans, dp[j]);
        hash[c] = j + 1;
      }

      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)


}
