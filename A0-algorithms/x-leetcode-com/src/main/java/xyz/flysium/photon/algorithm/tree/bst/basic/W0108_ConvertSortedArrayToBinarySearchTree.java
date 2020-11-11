package xyz.flysium.photon.algorithm.tree.bst.basic;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 108. 将有序数组转换为二叉搜索树
 * <p>
 * https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree/
 *
 * @author zeno
 */
public interface W0108_ConvertSortedArrayToBinarySearchTree {

  // 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
  //
  // 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。

  class Solution {

    public TreeNode sortedArrayToBST(int[] nums) {
      if (nums.length == 0) {
        return null;
      }
      if (nums.length == 1) {
        return new TreeNode(nums[0]);
      }
      final int length = nums.length;
      TreeNode root = binaryInsert(nums, 0, length - 1);
      return root;
    }

    private TreeNode binaryInsert(int[] nums, int l, int r) {
      if (l > r) {
        return null;
      }
      if (l >= r) {
        return new TreeNode(nums[l]);
      }
      int mid = l + ((r - l + 1) >> 1);
      TreeNode root = new TreeNode(nums[mid]);
      root.left = binaryInsert(nums, l, mid - 1);
      root.right = binaryInsert(nums, mid + 1, r);
      return root;
    }

  }

}
