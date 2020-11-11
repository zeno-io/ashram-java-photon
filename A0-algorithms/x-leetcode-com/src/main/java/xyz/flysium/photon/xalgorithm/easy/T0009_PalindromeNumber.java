package xyz.flysium.photon.xalgorithm.easy;

/**
 * 9. 回文数
 * <p>
 * https://leetcode-cn.com/problems/palindrome-number/
 *
 * @author zeno
 */
public class T0009_PalindromeNumber {

//判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
//
// 示例 1:
//
// 输入: 121
//输出: true
//
//
// 示例 2:
//
// 输入: -121
//输出: false
//解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
//
//
// 示例 3:
//
// 输入: 10
//输出: false
//解释: 从右向左读, 为 01 。因此它不是一个回文数。
//
//
// 进阶:
//
// 你能不将整数转为字符串来解决这个问题吗？
// Related Topics 数学
// 👍 1283 👎 0


  public static void main(String[] args) {
    Solution solution = new T0009_PalindromeNumber().new Solution();
    System.out.println(solution.isPalindrome(123));
    System.out.println(solution.isPalindrome(121));
    System.out.println(solution.isPalindrome(-121));
  }

// 执行用时：8 ms, 在所有 Java 提交中击败了99.94% 的用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public boolean isPalindrome(int x) {
      if (x < 0) {
        return false;
      }
      if (x != 0 && x % 10 == 0) {
        return false;
      }
      int a = x;
      int r = 0;
      while (a > r) {
        r = r * 10 + a % 10;
        a = a / 10;
      }
      return r == a || a == r / 10;
    }

//    public boolean isPalindrome(int x) {
//      if (x < 0) {
//        return false;
//      }
//      int a = x;
//      int r = 0;
//      while (a != 0) {
//        r = r * 10 + a % 10;
//        a = a / 10;
//      }
//      return r == x;
//    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
