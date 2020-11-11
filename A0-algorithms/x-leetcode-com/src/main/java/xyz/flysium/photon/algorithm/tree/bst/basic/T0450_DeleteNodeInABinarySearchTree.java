package xyz.flysium.photon.algorithm.tree.bst.basic;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 450. 删除二叉搜索树中的节点
 * <p>
 * https://leetcode-cn.com/problems/delete-node-in-a-bst/
 *
 * @author zeno
 */
public interface T0450_DeleteNodeInABinarySearchTree {

  // 给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。
  //
  //一般来说，删除节点可分为两个步骤：
  //
  //    首先找到需要删除的节点；
  //    如果找到了，删除它。
  //
  //说明： 要求算法时间复杂度为 O(h)，h 为树的高度。

  class Solution {

    public TreeNode deleteNode(TreeNode root, int key) {
      TreeNode dummy = new TreeNode(Integer.MAX_VALUE);
      dummy.left = deleteTreeNode(root, key);
      return dummy.left;
    }

    // 返回的值会挂到父亲的左孩子指针或右孩子指针，返回null，表示直接删除
    private TreeNode deleteTreeNode(TreeNode root, int key) {
      if (root == null) {
        return null;
      }
      // delete from the right subtree
      if (key > root.val) {
        root.right = deleteTreeNode(root.right, key);
        return root;
      }
      // delete from the left subtree
      if (key < root.val) {
        root.left = deleteTreeNode(root.left, key);
        return root;
      }
      // 1. 如果目标节点没有子节点，我们可以直接移除该目标节点。
      if (root.left == null && root.right == null) {
        return null;
      }
      // 2. 如果目标节只有一个子节点，我们可以用其子节点作为替换。
      // 3. 如果目标节点有两个子节点，我们需要用其中序后继节点或者前驱节点来替换，再删除该目标节点。
      if (root.right != null) {
        int successor = successor(root);
        root.val = successor;
        root.right = deleteTreeNode(root.right, successor);
        return root;
      }
      int predecessor = predecessor(root);
      root.val = predecessor;
      root.left = deleteTreeNode(root.left, predecessor);
      return root;
    }

    // One step right and then always left
    private int successor(TreeNode root) {
      assert (root.right != null);
      TreeNode c = root.right;
//      if (c == null) {
//        return null;
//      }
      while (c.left != null) {
        c = c.left;
      }
      return c.val;
    }

    // One step left and then always right
    private int predecessor(TreeNode root) {
      assert (root.left != null);
      TreeNode c = root.left;
//      if (c == null) {
//        return null;
//      }
      while (c.right != null) {
        c = c.right;
      }
      return c.val;
    }

  }

}
