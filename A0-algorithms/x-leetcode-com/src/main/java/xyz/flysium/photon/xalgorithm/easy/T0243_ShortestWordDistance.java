package xyz.flysium.photon.xalgorithm.easy;

/**
 * 243. 最短单词距离
 * <p>
 * https://leetcode-cn.com/problems/shortest-word-distance/
 *
 * @author zeno
 */
public class T0243_ShortestWordDistance {

//给定一个单词列表和两个单词 word1 和 word2，返回列表中这两个单词之间的最短距离。
//
// 示例:
//假设 words = ["practice", "makes", "perfect", "coding", "makes"]
//
// 输入: word1 = “coding”, word2 = “practice”
//输出: 3
//
//
// 输入: word1 = "makes", word2 = "coding"
//输出: 1
//
//
// 注意:
//你可以假设 word1 不等于 word2, 并且 word1 和 word2 都在列表里。
// Related Topics 数组
// 👍 39 👎 0


  public static void main(String[] args) {
    Solution solution = new T0243_ShortestWordDistance().new Solution();

  }

  // 执行用时：2 ms, 在所有 Java 提交中击败了97.50% 的用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int shortestDistance(String[] words, String word1, String word2) {
      int idx1 = -1;
      int idx2 = -1;
      int ans = words.length;
      for (int i = 0; i < words.length; i++) {
        String word = words[i];
        if (word.equals(word1)) {
          idx1 = i;
          if (idx2 >= 0) {
            ans = Math.min(ans, idx1 - idx2);
          }
        } else if (word.equals(word2)) {
          idx2 = i;
          if (idx1 >= 0) {
            ans = Math.min(ans, idx2 - idx1);
          }
        }
      }
      return ans;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
