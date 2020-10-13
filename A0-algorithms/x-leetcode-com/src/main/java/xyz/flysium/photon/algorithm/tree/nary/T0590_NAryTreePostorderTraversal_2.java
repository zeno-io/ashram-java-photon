package xyz.flysium.photon.algorithm.tree.nary;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * 590. N叉树的后序遍历
 * <p>
 * https://leetcode-cn.com/problems/n-ary-tree-postorder-traversal/
 *
 * @author zeno
 */
public interface T0590_NAryTreePostorderTraversal_2 {

  // 给定一个 N 叉树，返回其节点值的后序遍历。

  class Solution {

    public List<Integer> postorder(Node root) {
      if (root == null) {
        return Collections.emptyList();
      }
      LinkedList<Integer> ans = new LinkedList<>();
      Deque<Node> stack = new LinkedList<>();
      Node node = null;

      stack.push(root);
      while (!stack.isEmpty()) {
        node = stack.pop();
        ans.addFirst(node.val);
        if (node.children != null && !node.children.isEmpty()) {
          ListIterator<Node> it = node.children.listIterator(0);
          while (it.hasNext()) {
            Node n = it.next();
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
