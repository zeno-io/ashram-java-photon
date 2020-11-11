package xyz.flysium.photon.algorithm.tree.binary.basic;

import java.util.Deque;
import java.util.LinkedList;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 101. 对称二叉树
 * <p>
 * https://leetcode-cn.com/problems/symmetric-tree/
 *
 * @author zeno
 */
public interface U0101_SymmetricTree_2 {

  // 给定一个二叉树，检查它是否是镜像对称的。
  // 	3 ms
  class Solution {

    public boolean isSymmetric(TreeNode root) {
      if (root == null) {
        return true;
      }
      LinkedList<TreeNodeExt> n1 = new LinkedList<>();
      LinkedList<TreeNodeExt> n2 = new LinkedList<>();
      // level order traversal, and add null
      Deque<TreeNodeExt> queue = new LinkedList<>();
      TreeNodeExt node = null;
      TreeNodeExt e = null;
      TreeNodeExt currEnd = null;
      TreeNodeExt nextEnd = null;
      TreeNodeExt a = null;
      TreeNodeExt b = null;

      TreeNodeExt rootExt = new TreeNodeExt(root);
      queue.offerLast(rootExt);
      currEnd = rootExt;
      while (!queue.isEmpty()) {
        node = queue.pollFirst();
        if (node != rootExt) {
          n1.offerLast(node);
          n2.offerFirst(node);
        }
        if (node.node != null) {
          e = new TreeNodeExt(node.node.left, true);
          queue.offerLast(e);
          nextEnd = e;

          e = new TreeNodeExt(node.node.right, false);
          queue.offerLast(e);
          nextEnd = e;
        }
        if (node == currEnd) {
//          if (node != rootExt && (n1.size() % 2) != 0) {
//            return false;
//          }
          while (!n1.isEmpty()) {
            a = n1.pollFirst();
            b = n2.pollFirst();
            // for root
            if (a.node == b.node) {
              continue;
            }
            if (!(a.node != null && b.node != null)) {
              return false;
            }
            if (a.node.val != b.node.val || a.leftOfParent == b.leftOfParent) {
              return false;
            }
          }
          n1 = new LinkedList<>();
          n2 = new LinkedList<>();
          currEnd = nextEnd;
        }
      }
      return true;
    }

    static class TreeNodeExt {

      TreeNode node;

      // left child of parent or not ?
      boolean leftOfParent = true;

      public TreeNodeExt(TreeNode node) {
        this(node, true);
      }

      public TreeNodeExt(TreeNode node, boolean leftOfParent) {
        this.node = node;
        this.leftOfParent = leftOfParent;
      }

    }

  }

}
