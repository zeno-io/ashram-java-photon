package xyz.flysium.photon.algorithm.tree.binary;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 236. 二叉树的最近公共祖先
 * <p>
 * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/
 *
 * @author zeno
 */
public interface W0236_LowestCommonAncestorOfABinaryTree_1 {

  // 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
  //
  //百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，
  //    满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”

  // 13 ms
  class Solution {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
      Deque<TreeNode> queue = new LinkedList<>();
      HashMap<TreeNode, MyTreeNode> ancestor = new HashMap<>();
      TreeNode curr = null;
      TreeNode currEnd = null;
      TreeNode nextEnd = null;
      int level = 1;

      // level order traversal, and hash
      queue.offerLast(root);
      ancestor.put(root, new MyTreeNode(root, 1));
      currEnd = root;
      while (!queue.isEmpty()) {
        curr = queue.pollFirst();
        if (curr.left != null) {
          queue.offerLast(curr.left);
          ancestor.put(curr.left, new MyTreeNode(curr, level + 1));
          nextEnd = curr.left;
        }
        if (curr.right != null) {
          queue.offerLast(curr.right);
          ancestor.put(curr.right, new MyTreeNode(curr, level + 1));
          nextEnd = curr.right;
        }
        if (curr == currEnd) {
          level++;
          currEnd = nextEnd;
        }
      }
      MyTreeNode p1 = ancestor.get(p);
      MyTreeNode p2 = ancestor.get(q);
      if (p2.parent == p) {
        return p;
      }
      if (p1.parent == q) {
        return q;
      }
      int incr = Math.abs(p1.level - p2.level);
      if (incr > 0) {
        if (p1.level > p2.level) {
          for (int i = 0; i < incr; i++) {
            if (p1.parent == q) {
              return q;
            }
            p1 = ancestor.get(p1.parent);
          }
        } else {
          for (int i = 0; i < incr; i++) {
            if (p2.parent == p) {
              return p;
            }
            p2 = ancestor.get(p2.parent);
          }
        }
      }
      while (p1.parent != p2.parent) {
        if (p2.parent == p) {
          return p;
        }
        if (p1.parent == q) {
          return q;
        }
        p1 = ancestor.get(p1.parent);
        p2 = ancestor.get(p2.parent);
      }
      return p1.parent;
    }

    static class MyTreeNode {

      TreeNode parent;
      int level;

      public MyTreeNode(TreeNode parent, int level) {
        this.parent = parent;
        this.level = level;
      }

      @Override
      public String toString() {
        return "MyTreeNode{" +
          "parent=" + parent.val +
          ", level=" + level +
          '}';
      }
    }

  }

}
