package xyz.flysium.photon.jianzhioffer.search.medium;

/**
 * 剑指 Offer 12. 矩阵中的路径
 * <p>
 * https://leetcode-cn.com/problems/ju-zhen-zhong-de-lu-jing-lcof/
 *
 * @author zeno
 */
public class J0012_Exist {

//请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一格开始，每一步可以在矩阵中向左、右、上、下移动一格。如果
//一条路径经过了矩阵的某一格，那么该路径不能再次进入该格子。例如，在下面的3×4的矩阵中包含一条字符串“bfce”的路径（路径中的字母用加粗标出）。
//
// [["a","b","c","e"],
//["s","f","c","s"],
//["a","d","e","e"]]
//
// 但矩阵中不包含字符串“abfb”的路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入这个格子。
//
//
//
// 示例 1：
//
// 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "A
//BCCED"
//输出：true
//
//
// 示例 2：
//
// 输入：board = [["a","b"],["c","d"]], word = "abcd"
//输出：false
//
//
// 提示：
//
//
// 1 <= board.length <= 200
// 1 <= board[i].length <= 200
//
//
// 注意：本题与主站 79 题相同：https://leetcode-cn.com/problems/word-search/
// Related Topics 深度优先搜索
// 👍 187 👎 0


  public static void main(String[] args) {
    Solution solution = new J0012_Exist().new Solution();

  }

  // 执行耗时:5 ms,击败了98.03% 的Java用户
  // https://leetcode-cn.com/leetbook/read/illustration-of-algorithm/58d5vh/

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public boolean exist(char[][] board, String word) {
      if (board.length == 0 || word.length() == 0) {
        return false;
      }
      final int rows = board.length;
      final int cols = board[0].length;
      char c = word.charAt(0);
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
          if (board[i][j] == c) {
            boolean b = dfs(board, word, i, j, 0, rows, cols);
            if (b) {
              return true;
            }
          }
        }
      }
      return false;
    }

    private boolean dfs(char[][] board, String word, int x0, int y0, int k, int rows, int cols) {
      if (x0 < 0 || y0 < 0 || x0 >= rows || y0 >= cols || board[x0][y0] != word.charAt(k)) {
        return false;
      }
      if (k == word.length() - 1) {
        return true;
      }
      board[x0][y0] = '\0';
      boolean ans = dfs(board, word, x0, y0 - 1, k + 1, rows, cols)
        || dfs(board, word, x0, y0 + 1, k + 1, rows, cols)
        || dfs(board, word, x0 - 1, y0, k + 1, rows, cols)
        || dfs(board, word, x0 + 1, y0, k + 1, rows, cols);
      board[x0][y0] = word.charAt(k);
      return ans;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
