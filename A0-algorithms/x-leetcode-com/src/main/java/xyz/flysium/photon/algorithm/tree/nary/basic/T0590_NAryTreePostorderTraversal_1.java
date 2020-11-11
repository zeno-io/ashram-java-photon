package xyz.flysium.photon.algorithm.tree.nary.basic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import xyz.flysium.photon.algorithm.tree.nary.Node;

/**
 * 590. N叉树的后序遍历
 * <p>
 * https://leetcode-cn.com/problems/n-ary-tree-postorder-traversal/
 *
 * @author zeno
 */
public interface T0590_NAryTreePostorderTraversal_1 {

  // 给定一个 N 叉树，返回其节点值的后序遍历。

  class Solution {

    public List<Integer> postorder(Node root) {
      if (root == null) {
        return Collections.emptyList();
      }
      return postorderTraversal(root);
    }

    public List<Integer> postorderTraversal(Node root) {
      if (root == null) {
        return Collections.emptyList();
      }
      List<Integer> ans = new LinkedList<>();
      if (root.children != null) {
        for (Node n : root.children) {
          ans.addAll(postorderTraversal(n));
        }
      }
      ans.add(root.val);
      return ans;
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
