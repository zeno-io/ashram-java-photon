package xyz.flysium.photon.algorithm.string.easy;

/**
 * 345. 反转字符串中的元音字母
 * <p>
 * https://leetcode-cn.com/problems/reverse-vowels-of-a-string/
 *
 * @author zeno
 */
public class T0345_ReverseVowelsOfAString {

//编写一个函数，以字符串作为输入，反转该字符串中的元音字母。
//
//
//
// 示例 1：
//
// 输入："hello"
//输出："holle"
//
//
// 示例 2：
//
// 输入："leetcode"
//输出："leotcede"
//
//
//
// 提示：
//
//
// 元音字母不包含字母 "y" 。
//
// Related Topics 双指针 字符串
// 👍 120 👎 0


  public static void main(String[] args) {
    Solution solution = new T0345_ReverseVowelsOfAString().new Solution();
    System.out.println(solution.reverseVowels("hello"));
    System.out.println(solution.reverseVowels("aA"));
  }

  // 执行耗时:2 ms,击败了100.00% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public String reverseVowels(String s) {
      // a e i o u
      char[] cs = s.toCharArray();
      int l = 0;
      int r = s.length() - 1;
      while (l < r) {
        while (l < r && l < cs.length && !(cs[l] == 'a' || cs[l] == 'e' || cs[l] == 'i'
          || cs[l] == 'o' || cs[l] == 'u' || cs[l] == 'A' || cs[l] == 'E' || cs[l] == 'I'
          || cs[l] == 'O' || cs[l] == 'U')) {
          l++;
        }
        while (l < r && r >= 0 && !(cs[r] == 'a' || cs[r] == 'e' || cs[r] == 'i'
          || cs[r] == 'o' || cs[r] == 'u' || cs[r] == 'A' || cs[r] == 'E' || cs[r] == 'I'
          || cs[r] == 'O' || cs[r] == 'U')) {
          r--;
        }
        if (cs[l] != cs[r]) {
          char c = cs[l];
          cs[l] = cs[r];
          cs[r] = c;
        }
        l++;
        r--;
      }
      return String.valueOf(cs);
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
