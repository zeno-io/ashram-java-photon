package xyz.flysium.photon.algorithm.queueandstack.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 133. 克隆图
 * <p>
 * https://leetcode-cn.com/problems/clone-graph/
 *
 * @author zeno
 */
public interface U0133_CloneGraph_1 {

  // 给你无向 连通 图中一个节点的引用，请你返回该图的 深拷贝（克隆）。
  //
  // 图中的每个节点都包含它的值 val（int） 和其邻居的列表（list[Node]）。

  // 36 ms DFS
  class Solution {

    public Node cloneGraph(Node node) {
      if (node == null) {
        return null;
      }
      return dfs(node, new HashMap<>());
    }

    private Node dfs(Node node, Map<Integer, Node> hash) {
      if (hash.containsKey(node.val)) {
        return hash.get(node.val);
      }
      Node cloned = new Node(node.val);
      hash.putIfAbsent(node.val, cloned);
      cloned.neighbors = new ArrayList<>(node.neighbors.size());
      for (Node neighbor : node.neighbors) {
        Node n = dfs(neighbor, hash);
        cloned.neighbors.add(n);
      }
      return cloned;
    }

  }

    /*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/

}
