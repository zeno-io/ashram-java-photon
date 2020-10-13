package xyz.flysium.photon.algorithm.queueandstack.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 133. 克隆图
 * <p>
 * https://leetcode-cn.com/problems/clone-graph/
 *
 * @author zeno
 */
public interface U0133_CloneGraph {

  // 给你无向 连通 图中一个节点的引用，请你返回该图的 深拷贝（克隆）。
  //
  // 图中的每个节点都包含它的值 val（int） 和其邻居的列表（list[Node]）。

  // 35 ms BFS
  class Solution {

    public Node cloneGraph(Node node) {
      if (node == null) {
        return null;
      }
      HashMap<Node, Node> visited = new HashMap<>();
      LinkedList<Node> queue = new LinkedList<Node>();

      queue.offerLast(node);
      visited.putIfAbsent(node, new Node(node.val, new ArrayList<>()));
      while (!queue.isEmpty()) {
        int sz = queue.size();
        for (int x = 0; x < sz; x++) {
          Node n = queue.pollFirst();
          for (Node neighbor : n.neighbors) {
            if (!visited.containsKey(neighbor)) {
              visited.putIfAbsent(neighbor,
                new Node(neighbor.val, new ArrayList<>(neighbor.neighbors.size())));
              queue.offerLast(neighbor);
            }
            visited.get(n).neighbors.add(visited.get(neighbor));
          }
        }
      }
      return visited.get(node);
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
