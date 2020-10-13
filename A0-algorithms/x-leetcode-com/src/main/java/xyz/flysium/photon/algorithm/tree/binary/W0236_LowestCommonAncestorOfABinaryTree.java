package xyz.flysium.photon.algorithm.tree.binary;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 236. 二叉树的最近公共祖先
 * <p>
 * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/
 *
 * @author zeno
 */
public interface W0236_LowestCommonAncestorOfABinaryTree {

  // 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
  //
  //百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，
  //    满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”

  // 7ms
  class Solution {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
      if (root == null) {
        return null;
      }
      return dfs(root, p, q);
    }

    // 通过递归对二叉树进行后序遍历，当遇到节点 p 或 q 时返回。
    // 从底至顶回溯，当节点 p, q 在节点 root 的异侧时，节点 root 即为最近公共祖先，则向上返回 root 。
    public TreeNode dfs(TreeNode root, TreeNode p, TreeNode q) {
      if (root == null || root == p || root == q) {
        return root;
      }

      TreeNode left = dfs(root.left, p, q);
      TreeNode right = dfs(root.right, p, q);

      if (left != null && right != null) {
        return root;
      }
      return left != null ? left : right;
    }

  }

}
