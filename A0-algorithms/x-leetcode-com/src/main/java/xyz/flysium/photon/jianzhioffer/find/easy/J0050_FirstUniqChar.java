package xyz.flysium.photon.jianzhioffer.find.easy;

/**
 * 剑指 Offer 50. 第一个只出现一次的字符
 * <p>
 * https://leetcode-cn.com/problems/di-yi-ge-zhi-chu-xian-yi-ci-de-zi-fu-lcof/
 *
 * @author zeno
 */
public class J0050_FirstUniqChar {

//在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。 s 只包含小写字母。
//
// 示例:
//
// s = "abaccdeff"
//返回 "b"
//
//s = ""
//返回 " "
//
//
//
//
// 限制：
//
// 0 <= s 的长度 <= 50000
// Related Topics 哈希表
// 👍 52 👎 0


  public static void main(String[] args) {
    Solution solution = new J0050_FirstUniqChar().new Solution();
    // l
    System.out.println(solution.firstUniqChar("leetcode"));
    // v
    System.out.println(solution.firstUniqChar("loveleetcode"));
  }

  // 执行耗时:2 ms,击败了100.00% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public char firstUniqChar(String s) {
      int idx = -1;
      for (char c = 'a'; c <= 'z'; c++) {
        int i = s.indexOf(c);
        if (i >= 0 && i == s.lastIndexOf(c)) {
          idx = (idx == -1) ? i : Math.min(idx, i);
        }
      }
      return (idx == -1) ? ' ' : s.charAt(idx);
    }

//    public char firstUniqChar(String s) {
//      int[] hash = new int[128];
//      for (int i = 0; i < s.length(); i++) {
//        char c = s.charAt(i);
//        hash[c]++;
//      }
//      for (int i = 0; i < s.length(); i++) {
//        char c = s.charAt(i);
//        if (hash[c] == 1) {
//          return c;
//        }
//      }
//      return ' ';
//    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
