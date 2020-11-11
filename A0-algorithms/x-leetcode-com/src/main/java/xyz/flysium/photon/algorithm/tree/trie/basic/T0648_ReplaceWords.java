package xyz.flysium.photon.algorithm.tree.trie.basic;

import java.util.Arrays;
import java.util.List;

/**
 * 648. 单词替换
 * <p>
 * https://leetcode-cn.com/problems/replace-words/
 *
 * @author zeno
 */
public class T0648_ReplaceWords {

//在英语中，我们有一个叫做 词根(root)的概念，它可以跟着其他一些词组成另一个较长的单词——我们称这个词为 继承词(successor)。例如，词根an，
//跟随着单词 other(其他)，可以形成新的单词 another(另一个)。
//
// 现在，给定一个由许多词根组成的词典和一个句子。你需要将句子中的所有继承词用词根替换掉。如果继承词有许多可以形成它的词根，则用最短的词根替换它。
//
// 你需要输出替换之后的句子。
//
//
//
// 示例 1：
//
// 输入：dictionary = ["cat","bat","rat"], sentence = "the cattle was rattled by th
//e battery"
//输出："the cat was rat by the bat"
//
//
// 示例 2：
//
// 输入：dictionary = ["a","b","c"], sentence = "aadsfasf absbs bbab cadsfafs"
//输出："a a b c"
//
//
// 示例 3：
//
// 输入：dictionary = ["a", "aa", "aaa", "aaaa"], sentence = "a aa a aaaa aaa aaa a
//aa aaaaaa bbb baba ababa"
//输出："a a a a a a a a bbb baba a"
//
//
// 示例 4：
//
// 输入：dictionary = ["catt","cat","bat","rat"], sentence = "the cattle was rattle
//d by the battery"
//输出："the cat was rat by the bat"
//
//
// 示例 5：
//
// 输入：dictionary = ["ac","ab"], sentence = "it is abnormal that this solution is
// accepted"
//输出："it is ab that this solution is ac"
//
//
//
//
// 提示：
//
//
// 1 <= dictionary.length <= 1000
// 1 <= dictionary[i].length <= 100
// dictionary[i] 仅由小写字母组成。
// 1 <= sentence.length <= 10^6
// sentence 仅由小写字母和空格组成。
// sentence 中单词的总量在范围 [1, 1000] 内。
// sentence 中每个单词的长度在范围 [1, 1000] 内。
// sentence 中单词之间由一个空格隔开。
// sentence 没有前导或尾随空格。
//
// Related Topics 字典树 哈希表
// 👍 82 👎 0


  public static void main(String[] args) {
    Solution solution = new T0648_ReplaceWords().new Solution();
    // "the cat was rat by the bat"
    System.out.println(solution.replaceWords(Arrays.asList("cat", "bat", "rat"),
      "the cattle was rattled by the battery"));
  }

  // 执行耗时:10 ms,击败了92.04% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public String replaceWords(List<String> dictionary, String sentence) {
      StringBuilder buf = new StringBuilder();
      for (String dict : dictionary) {
        insert(dict);
      }
      int s = 0;
      int i = 0;
      String pre = "";
      while (i < sentence.length()) {
        while (i < sentence.length() && sentence.charAt(i) == ' ') {
          i++;
        }
        s = i;
        while (i < sentence.length() && sentence.charAt(i) != ' ') {
          i++;
        }
        if (s < sentence.length() && i <= sentence.length()) {
          String word = sentence.substring(s, i);
          String root = searchRoot(word);
          // sentence 仅由小写字母和空格组成。
          // sentence 中单词之间由一个空格隔开。
          // sentence 没有前导或尾随空格。
          buf.append(pre).append(root == null ? word : root);
          pre = " ";
        }
      }
      return buf.toString();
    }

    final TrieNode root = new TrieNode('\0');

    void insert(String dict) {
      TrieNode curr = root;
      curr.pass++;
      for (int i = 0; i < dict.length(); i++) {
        char c = dict.charAt(i);
        curr = putIfAbsent(curr, c);
        curr.pass++;
      }
      curr.end++;
    }

    String searchRoot(String word) {
      if (word == null || word.length() == 0) {
        return null;
      }
      StringBuilder buf = new StringBuilder();
      TrieNode curr = root;
      for (int i = 0; i < word.length(); i++) {
        char c = word.charAt(i);
        curr = get(curr, c);
        if (curr == null) {
          return null;
        }
        buf.append(c);
        // 如果继承词有许多可以形成它的词根，则用最短的词根替换它。
        if (curr.end > 0) {
          return buf.toString();
        }
      }

      return null;
    }

    private TrieNode putIfAbsent(TrieNode node, char c) {
      TrieNode n = node.next[c - 'a'];
      if (n == null) {
        n = new TrieNode(c);
        node.next[c - 'a'] = n;
      }
      return n;
    }

    private TrieNode get(TrieNode node, char c) {
      return node.next[c - 'a'];
    }

    class TrieNode {

      final char c;
      int pass;
      int end;
      final TrieNode[] next = new TrieNode[26];

      TrieNode(char c) {
        this.c = c;
      }
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
