package xyz.flysium.photon.xalgorithm.easy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 830. 较大分组的位置
 * <p>
 * https://leetcode-cn.com/problems/positions-of-large-groups/
 *
 * @author zeno
 */
public class T0830_PositionsOfLargeGroups {

//在一个由小写字母构成的字符串 S 中，包含由一些连续的相同字符所构成的分组。
//
// 例如，在字符串 S = "abbxxxxzyy" 中，就含有 "a", "bb", "xxxx", "z" 和 "yy" 这样的一些分组。
//
// 我们称所有包含大于或等于三个连续字符的分组为较大分组。找到每一个较大分组的起始和终止位置。
//
// 最终结果按照字典顺序输出。
//
// 示例 1:
//
//
//输入: "abbxxxxzzy"
//输出: [[3,6]]
//解释: "xxxx" 是一个起始于 3 且终止于 6 的较大分组。
//
//
// 示例 2:
//
//
//输入: "abc"
//输出: []
//解释: "a","b" 和 "c" 均不是符合要求的较大分组。
//
//
// 示例 3:
//
//
//输入: "abcdddeeeeaabbbcd"
//输出: [[3,5],[6,9],[12,14]]
//
// 说明: 1 <= S.length <= 1000
// Related Topics 数组
// 👍 57 👎 0


  public static void main(String[] args) {
    Solution solution = new T0830_PositionsOfLargeGroups().new Solution();
    // [[3,6]]
    System.out.println(solution.largeGroupPositions("abbxxxxzzy"));
    // []
    System.out.println(solution.largeGroupPositions("abc"));
    // [[3,5],[6,9],[12,14]]
    System.out.println(solution.largeGroupPositions("abcdddeeeeaabbbcd"));
  }

  // 执行用时：1 ms, 在所有 Java 提交中击败了100.00%

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public List<List<Integer>> largeGroupPositions(String s) {
      char[] cs = s.toCharArray();
      int i = 0;
      int j = 0;
      final int len = cs.length;
      List<List<Integer>> ans = new LinkedList<>();
      while (j < len) {
        i = j;
        while (j + 1 < len && cs[j + 1] == cs[j]) {
          j = j + 1;
        }
        if (j - i + 1 >= 3) {
          List<Integer> l = new ArrayList<>(2);
          l.add(i);
          l.add(j);
          ans.add(l);
        }
        j = j + 1;
      }
      return ans;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
