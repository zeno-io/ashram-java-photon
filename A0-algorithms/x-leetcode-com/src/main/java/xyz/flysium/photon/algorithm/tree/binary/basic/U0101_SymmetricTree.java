package xyz.flysium.photon.algorithm.tree.binary.basic;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 101. 对称二叉树
 * <p>
 * https://leetcode-cn.com/problems/symmetric-tree/
 *
 * @author zeno
 */
public interface U0101_SymmetricTree {

  // 给定一个二叉树，检查它是否是镜像对称的。
  class Solution {

    public boolean isSymmetric(TreeNode root) {
      if (root == null) {
        return true;
      }
      return isSymmetric(root.left, root.right);
    }

    // 如果同时满足下面的条件，两个树互为镜像：
    // 它们的两个根结点具有相同的值
    // 每个树的右子树都与另一个树的左子树镜像对称
    public boolean isSymmetric(TreeNode p, TreeNode q) {
      if (p == null && q == null) {
        return true;
      }
      if (!(p != null && q != null)) {
        return false;
      }
      return p.val == q.val && isSymmetric(p.left, q.right) && isSymmetric(p.right, q.left);
    }

  }

}
