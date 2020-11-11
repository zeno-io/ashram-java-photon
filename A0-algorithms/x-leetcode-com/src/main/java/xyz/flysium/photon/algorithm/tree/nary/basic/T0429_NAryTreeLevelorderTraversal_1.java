package xyz.flysium.photon.algorithm.tree.nary.basic;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import xyz.flysium.photon.algorithm.tree.nary.Node;

/**
 * 429. N叉树的层序遍历
 * <p>
 * https://leetcode-cn.com/problems/n-ary-tree-level-order-traversal/
 *
 * @author zeno
 */
public interface T0429_NAryTreeLevelorderTraversal_1 {

  // 给定一个 N 叉树，返回其节点值的层序遍历。 (即从左到右，逐层遍历)。

  class Solution {

    public List<List<Integer>> levelOrder(Node root) {
      if (root == null) {
        return Collections.emptyList();
      }
      List<List<Integer>> ans = new LinkedList<>();
      Deque<Node> queue = new LinkedList<>();
      Node node = null;
      int levelIndex = 0;

      queue.offerLast(root);
      while (!queue.isEmpty()) {
        int sz = queue.size();
        List<Integer> l = new LinkedList<>();
        for (int x = 0; x < sz; x++) {
          node = queue.pollFirst();
          l.add(node.val);
          if (node.children != null && !node.children.isEmpty()) {
            ListIterator<Node> it = node.children.listIterator(0);
            while (it.hasNext()) {
              Node n = it.next();
              if (n != null) {
                queue.offerLast(n);
              }
            }
          }
        }
        ans.add(l);
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
