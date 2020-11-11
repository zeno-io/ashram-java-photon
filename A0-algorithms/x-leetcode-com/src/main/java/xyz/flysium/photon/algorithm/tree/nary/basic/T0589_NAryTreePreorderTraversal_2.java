package xyz.flysium.photon.algorithm.tree.nary.basic;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import xyz.flysium.photon.algorithm.tree.nary.Node;

/**
 * 589. N叉树的前序遍历
 * <p>
 * https://leetcode-cn.com/problems/n-ary-tree-preorder-traversal/
 *
 * @author zeno
 */
public interface T0589_NAryTreePreorderTraversal_2 {

  // 给定一个 N 叉树，返回其节点值的前序遍历。

  class Solution {

    public List<Integer> preorder(Node root) {
      if (root == null) {
        return Collections.emptyList();
      }
      List<Integer> ans = new LinkedList<>();
      Deque<Node> stack = new LinkedList<>();
      Node node = null;

      stack.push(root);
      while (!stack.isEmpty()) {
        node = stack.pop();
        ans.add(node.val);
        if (node.children != null && !node.children.isEmpty()) {
          ListIterator<Node> it = node.children.listIterator(node.children.size());
          while (it.hasPrevious()) {
            Node n = it.previous();
            if (n != null) {
              stack.push(n);
            }
          }
        }
      }
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
