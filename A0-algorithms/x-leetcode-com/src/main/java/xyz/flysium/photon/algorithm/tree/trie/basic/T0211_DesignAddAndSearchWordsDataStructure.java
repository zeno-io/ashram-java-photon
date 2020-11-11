package xyz.flysium.photon.algorithm.tree.trie.basic;

/**
 * 211. 添加与搜索单词 - 数据结构设计
 * <p>
 * https://leetcode-cn.com/problems/design-add-and-search-words-data-structure/
 *
 * @author zeno
 */
public class T0211_DesignAddAndSearchWordsDataStructure {

//请你设计一个数据结构，支持 添加新单词 和 查找字符串是否与任何先前添加的字符串匹配 。
//
// 实现词典类 WordDictionary ：
//
//
// WordDictionary() 初始化词典对象
// void addWord(word) 将 word 添加到数据结构中，之后可以对它进行匹配
// bool search(word) 如果数据结构中存在字符串与 word 匹配，则返回 true ；否则，返回 false 。word 中可能包含一些 '
//.' ，每个 . 都可以表示任何一个字母。
//
//
//
//
// 示例：
//
//
//输入：
//["WordDictionary","addWord","addWord","addWord","search","search","search","se
//arch"]
//[[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
//输出：
//[null,null,null,null,false,true,true,true]
//
//解释：
//WordDictionary wordDictionary = new WordDictionary();
//wordDictionary.addWord("bad");
//wordDictionary.addWord("dad");
//wordDictionary.addWord("mad");
//wordDictionary.search("pad"); // return False
//wordDictionary.search("bad"); // return True
//wordDictionary.search(".ad"); // return True
//wordDictionary.search("b.."); // return True
//
//
//
//
// 提示：
//
//
// 1 <= word.length <= 500
// addWord 中的 word 由小写英文字母组成
// search 中的 word 由 '.' 或小写英文字母组成
// 最调用多 50000 次 addWord 和 search
//
// Related Topics 设计 字典树 回溯算法
// 👍 171 👎 0


  public static void main(String[] args) {
    WordDictionary solution = new T0211_DesignAddAndSearchWordsDataStructure().new WordDictionary();

  }

  // 执行用时：46 ms, 在所有 Java 提交中击败了98.21% 的用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class WordDictionary {

    final TrieNode root;

    /**
     * Initialize your data structure here.
     */
    public WordDictionary() {
      root = new TrieNode('\0');
    }

    /**
     * Adds a word into the data structure.
     */
    public void addWord(String word) {
      // addWord 中的 word 由小写英文字母组成
      if (word == null || word.length() == 0) {
        return;
      }
      TrieNode curr = root;
      curr.pass++;
      for (int i = 0; i < word.length(); i++) {
        char c = word.charAt(i);
        curr = putIfAbsent(curr, c);
        curr.pass++;
      }
      curr.end++;
    }

    /**
     * Returns if the word is in the data structure.
     * <p>
     * A word could contain the dot character '.' to represent any one letter.
     */
    public boolean search(String word) {
      // search 中的 word 由 '.' 或小写英文字母组成
      if (word == null || word.length() == 0) {
        return false;
      }
      TrieNode node = searchNode(root, word, 0);
      return node != null && node.end > 0;
    }

    private TrieNode searchNode(TrieNode node, String word, int s) {
      for (int i = s; i < word.length(); i++) {
        char c = word.charAt(i);
        if (c == '.') {
          for (int j = 0; j < node.next.length; j++) {
            TrieNode n = node.next[j];
            if (n == null) {
              continue;
            }
            // if end
            if (i == word.length() - 1) {
              // if it's prefix ( not word ), continue
              if (n.end == 0) {
                continue;
              }
              return n;
            } else {
              n = searchNode(n, word, i + 1);
              if (n != null) {
                // if it's prefix ( not word ), continue
                if (n.end == 0) {
                  continue;
                }
                return n;
              }
            }
          }
          return null;
        }
        node = get(node, c);
        if (node == null) {
          return null;
        }
      }
      return node;
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
      int pass = 0;
      int end = 0;
      //      final Map<Character, TrieNode> next = new HashMap<>(26);
      final TrieNode[] next = new TrieNode[26];

      public TrieNode(char c) {
        this.c = c;
      }
    }

  }

/**
 * Your WordDictionary object will be instantiated and called as such: WordDictionary obj = new
 * WordDictionary(); obj.addWord(word); boolean param_2 = obj.search(word);
 */
//leetcode submit region end(Prohibit modification and deletion)


}
