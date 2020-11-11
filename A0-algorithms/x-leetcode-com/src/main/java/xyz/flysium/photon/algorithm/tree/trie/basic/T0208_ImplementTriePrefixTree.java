package xyz.flysium.photon.algorithm.tree.trie.basic;

/**
 * 208. 实现 Trie (前缀树)
 * <p>
 * https://leetcode-cn.com/problems/implement-trie-prefix-tree/
 *
 * @author zeno
 */
public class T0208_ImplementTriePrefixTree {

//实现一个 Trie (前缀树)，包含 insert, search, 和 startsWith 这三个操作。
//
// 示例:
//
// Trie trie = new Trie();
//
//trie.insert("apple");
//trie.search("apple");   // 返回 true
//trie.search("app");     // 返回 false
//trie.startsWith("app"); // 返回 true
//trie.insert("app");
//trie.search("app");     // 返回 true
//
// 说明:
//
//
// 你可以假设所有的输入都是由小写字母 a-z 构成的。
// 保证所有输入均为非空字符串。
//
// Related Topics 设计 字典树
// 👍 439 👎 0


  public static void main(String[] args) {
    Trie solution = new T0208_ImplementTriePrefixTree().new Trie();

  }

  // 执行耗时:40 ms,击败了92.91% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Trie {

    final TrieNode root;

    /**
     * Initialize your data structure here.
     */
    public Trie() {
      root = new TrieNode('\0');
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
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
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
      return searchCount(word) > 0;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
      return startsWithCount(prefix) > 0;
    }

    // 查询某个字符串在结构中还有几个
    private int searchCount(String word) {
      TrieNode node = searchNode(word);
      return (node == null) ? 0 : node.end;
    }

    // 查询有多少个字符串，是以 s 做前缀的
    private int startsWithCount(String prefix) {
      TrieNode node = searchNode(prefix);
      return (node == null) ? 0 : node.pass;
    }

    private TrieNode searchNode(String word) {
      if (word == null || word.length() == 0) {
        return null;
      }
      TrieNode node = root;
      for (int i = 0; i < word.length(); i++) {
        char c = word.charAt(i);
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
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
//leetcode submit region end(Prohibit modification and deletion)


}
