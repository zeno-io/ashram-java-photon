package xyz.flysium.photon.algorithm.string.basic;

/**
 * 125. 验证回文串
 * <p>
 * https://leetcode-cn.com/problems/valid-palindrome/
 *
 * @author zeno
 */
public class T0125_ValidPalindrome {

//给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
//
// 说明：本题中，我们将空字符串定义为有效的回文串。
//
// 示例 1:
//
// 输入: "A man, a plan, a canal: Panama"
//输出: true
//
//
// 示例 2:
//
// 输入: "race a car"
//输出: false
//
// Related Topics 双指针 字符串
// 👍 284 👎 0


  public static void main(String[] args) {
    Solution solution = new T0125_ValidPalindrome().new Solution();
    System.out.println(solution.isPalindrome("A man, a plan, a canal: Panama"));
  }

  // 执行耗时:3 ms,击败了93.96% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public boolean isPalindrome(String s) {
      if (s.length() == 0) {
        return true;
      }
      int l = 0;
      int r = s.length() - 1;
      while (l < r) {
        while (l < s.length() && !Character.isLetterOrDigit(s.charAt(l))) {
          l++;
        }
        while (r >= 0 && !Character.isLetterOrDigit(s.charAt(r))) {
          r--;
        }
        if (l >= s.length() || r < 0) {
          break;
        }
        if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))) {
          return false;
        }
        l++;
        r--;
      }
      return true;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)


}
