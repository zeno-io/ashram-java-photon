package xyz.flysium.photon.algorithm.tree.nary.basic;

import java.util.LinkedList;
import java.util.List;
import xyz.flysium.photon.algorithm.tree.nary.Node;

/**
 * 429. N叉树的层序遍历
 * <p>
 * https://leetcode-cn.com/problems/n-ary-tree-level-order-traversal/
 *
 * @author zeno
 */
public interface T0429_NAryTreeLevelorderTraversal {

  // 给定一个 N 叉树，返回其节点值的层序遍历。 (即从左到右，逐层遍历)。

  class Solution {

    public List<List<Integer>> levelOrder(Node root) {
      List<List<Integer>> list = new LinkedList<>();
      order(root, list, 0);
      return list;
    }

    private void order(Node root, List<List<Integer>> list, int depth) {
      if (root == null) {
        return;
      }
      if (depth >= list.size()) {
        list.add(new LinkedList<>());
      }
      list.get(depth).add(root.val);
      for (Node node : root.children) {
        order(node, list, depth + 1);
      }
    }

  }


    /*
  // Definition for a Node.
  class Node {
      public int val;
      public List<Node> children;

      public Node() {}

      public Node(int _val) {
          val = _val;
      }

      public Node(int _val, List<Node> _children) {
          val = _val;
          children = _children;
      }
  };
  */

}
