package xyz.flysium.photon.algorithm.tree.binary;

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
public interface U0101_SymmetricTree_1 {

  // 给定一个二叉树，检查它是否是镜像对称的。
  class Solution {

    public boolean isSymmetric(TreeNode root) {
      if (root == null) {
        return true;
      }

      return isSymmetric(root, root);
    }

    private boolean isSymmetric(TreeNode u, TreeNode v) {
      Deque<TreeNode> q = new LinkedList<>();
      q.offerLast(u);
      q.offerLast(v);
      TreeNode x = null;
      TreeNode y = null;

      while (!q.isEmpty()) {
        x = q.pollFirst();
        y = q.pollFirst();

        if (x == null && y == null) {
          continue;
        }
        if (!(x != null && y != null)) {
          return false;
        }
        if (x.val != y.val) {
          return false;
        }
        q.offerLast(x.left);
        q.offerLast(y.right);

        q.offerLast(x.right);
        q.offerLast(y.left);
      }

      return true;
    }

  }

}
