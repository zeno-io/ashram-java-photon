package xyz.flysium.photon.algorithm.tree.binary.basic;

import java.util.Deque;
import java.util.LinkedList;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 112. 路径总和
 * <p>
 * https://leetcode-cn.com/problems/path-sum/
 *
 * @author zeno
 */
public interface U0112_PathSum_1 {

  // 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
  // 说明: 叶子节点是指没有子节点的节点。

  class Solution {

    public boolean hasPathSum(TreeNode root, int sum) {
      if (root == null) {
        return false;
      }
      Deque<TreeNode> q = new LinkedList<>();
      Deque<Integer> s = new LinkedList<>();
      TreeNode n = null;
      Integer t = null;

      q.offerLast(root);
      s.offerLast(root.val);
      while (!q.isEmpty()) {
        n = q.pollFirst();
        t = s.pollFirst();
        // leaf
        if (n.left == null && n.right == null) {
          if (t == sum) {
            return true;
          }
          continue;
        }
        if (n.left != null) {
          q.offerLast(n.left);
          s.offerLast(n.left.val + t);
        }
        if (n.right != null) {
          q.offerLast(n.right);
          s.offerLast(n.right.val + t);
        }
      }
      return false;
    }

  }

}
