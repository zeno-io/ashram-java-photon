package xyz.flysium.photon.algorithm.tree.bst;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 235. 二叉搜索树的最近公共祖先
 * <p>
 * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
 *
 * @author zeno
 */
public interface U0235_LowestCommonAncestorOfABinarySearchTree {

  // 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
  //
  //  百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，
  //  满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
  //

  // 所有节点的值都是唯一的。
  // p、q 为不同节点且均存在于给定的二叉搜索树中。

  // 	6 ms
  class Solution {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
      if (root == null) {
        return null;
      }
      TreeNode ancestor = root;
      while (true) {
        if (p.val < ancestor.val && q.val < ancestor.val) {
          ancestor = ancestor.left;
        } else if (p.val > ancestor.val && q.val > ancestor.val) {
          ancestor = ancestor.right;
        } else {
          break;
        }
      }
      return ancestor;
    }

  }

}
