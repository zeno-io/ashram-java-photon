package xyz.flysium.photon.algorithm.tree.trie.hard;

import java.util.ArrayList;
import java.util.List;

/**
 * 212. 单词搜索 II
 * <p>
 * https://leetcode-cn.com/problems/word-search-ii/
 *
 * @author zeno
 */
public class T0212_WordSearchIi {

//给定一个二维网格 board 和一个字典中的单词列表 words，找出所有同时在二维网格和字典中出现的单词。
//
// 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使用。
//
//
// 示例:
//
// 输入:
//words = ["oath","pea","eat","rain"] and board =
//[
//  ['o','a','a','n'],
//  ['e','t','a','e'],
//  ['i','h','k','r'],
//  ['i','f','l','v']
//]
//
//输出:["eat","oath"]
//
// 说明:
//你可以假设所有输入都由小写字母 a-z 组成。
//
// 提示:
//
//
// 你需要优化回溯算法以通过更大数据量的测试。你能否早点停止回溯？
// 如果当前单词不存在于所有单词的前缀中，则可以立即停止回溯。什么样的数据结构可以有效地执行这样的操作？散列表是否可行？为什么？ 前缀树如何？如果你想学习如何
//实现一个基本的前缀树，请先查看这个问题： 实现Trie（前缀树）。
//
// Related Topics 字典树 回溯算法
// 👍 267 👎 0


  public static void main(String[] args) {
    Solution solution = new T0212_WordSearchIi().new Solution();

  }

  // 执行耗时:11 ms,击败了84.32% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public List<String> findWords(char[][] board, String[] words) {
      TrieNode trie = new TrieNode('\0');
      // initialize trie
      for (String word : words) {
        TrieNode node = trie;
        for (int i = 0; i < word.length(); i++) {
          char c = word.charAt(i);
          node = putIfAbsent(node, c);
        }
        node.word = word;
      }
      // search
      List<String> ans = new ArrayList<>();
      final int rows = board.length;
      final int cols = board[0].length;

      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
          if (trie.next[board[i][j] - 'a'] == null) {
            continue;
          }
          search(ans, trie, board, rows, cols, i, j);
        }
      }
      return ans;
    }

    private void search(List<String> ans, TrieNode node, char[][] board,
      int rows, int cols, int x, int y) {
      if (x < 0 || y < 0 || x >= rows || y >= cols) {
        return;
      }
      if (board[x][y] == '*') {
        return;
      }
      char c = board[x][y];
      node = node.next[c - 'a'];
      if (node == null) {
        return;
      }
      if (node.word != null) {
        ans.add(node.word);
        node.word = null; // 防止结果重复
      }
      board[x][y] = '*';
      search(ans, node, board, rows, cols, x, y - 1);
      search(ans, node, board, rows, cols, x, y + 1);
      search(ans, node, board, rows, cols, x - 1, y);
      search(ans, node, board, rows, cols, x + 1, y);
      // 回溯
      board[x][y] = c;
    }

    private TrieNode putIfAbsent(TrieNode node, char c) {
      TrieNode n = node.next[c - 'a'];
      if (n == null) {
        n = new TrieNode(c);
        node.next[c - 'a'] = n;
      }
      return n;
    }

    class TrieNode {

      final char c;
      String word;
      TrieNode[] next = new TrieNode[26];

      TrieNode(char c) {
        this.c = c;
      }
    }
  }
//leetcode submit region end(Prohibit modification and deletion)


}
