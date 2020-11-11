package xyz.flysium.photon.jianzhioffer.datastructure.easy;

import java.util.LinkedList;
import java.util.List;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 剑指 Offer 54. 二叉搜索树的第k大节点
 * <p>
 * https://leetcode-cn.com/problems/er-cha-sou-suo-shu-de-di-kda-jie-dian-lcof/
 *
 * @author zeno
 */
public interface J0054_kthLargest {

  // 给定一棵二叉搜索树，请找出其中第k大的节点。

  // 0ms 100.00%
  class Solution {

    public int kthLargest(TreeNode root, int k) {
      if (root == null) {
        return -1;
      }
      List<Integer> max = new LinkedList<>();
      inorderTraversal(root, k, max);
      return max.size() >= k ? max.get(k - 1) : -1;
    }

    private void inorderTraversal(TreeNode root, int k, List<Integer> max) {
      if (root == null) {
        return;
      }
      // 双重检验，减少搜索
      if (max.size() >= k) {
        return;
      }
      inorderTraversal(root.right, k, max);
      max.add(root.val);
      // 双重检验，减少搜索
      if (max.size() == k) {
        return;
      }
      inorderTraversal(root.left, k, max);
    }

  }

}
