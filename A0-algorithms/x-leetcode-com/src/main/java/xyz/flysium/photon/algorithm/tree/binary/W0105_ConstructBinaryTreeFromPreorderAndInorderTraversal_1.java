package xyz.flysium.photon.algorithm.tree.binary;

import java.util.HashMap;
import java.util.Map;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 105. 从前序与中序遍历序列构造二叉树
 * <p>
 * https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 *
 * @author zeno
 */
public interface W0105_ConstructBinaryTreeFromPreorderAndInorderTraversal_1 {

  //根据一棵树的前序遍历与中序遍历构造二叉树。
  //
  //注意:
  //你可以假设树中没有重复的元素。
  //
  //例如，给出
  //
  //前序遍历 preorder = [3,9,20,15,7] 头左右
  //中序遍历 inorder = [9,3,15,20,7] 左头右
  //
  //返回如下的二叉树：
  //
  //    3
  //   / \
  //  9  20
  //    /  \
  //   15   7
  class Solution {

    public TreeNode buildTree(int[] preorder, int[] inorder) {
      if (preorder.length == 0) {
        return null;
      }
      // 建立（元素，下标）键值对的哈希表
      Map<Integer, Integer> inorderIndexMap = new HashMap<>();
      for (int i = 0; i < inorder.length; i++) {
        inorderIndexMap.put(inorder[i], i);
      }
      // 从后序遍历的最后一个元素开始
      return buildFromPreorderAndInorder(preorder, 0, inorderIndexMap, 0, inorder.length - 1);
    }

    private TreeNode buildFromPreorderAndInorder(int[] preorder, int preRoot,
      Map<Integer, Integer> inorderIndexMap, int inLeft, int inRight) {
      // 如果这里没有节点构造二叉树了，就结束
      if (inLeft > inRight) {
        return null;
      }
      // 选择 rootIndex 位置的元素作为当前子树根节点
      int rootValue = preorder[preRoot];

      // 根据 root 所在位置分成左右两棵子树
      TreeNode root = new TreeNode(rootValue);
      int inRoot = inorderIndexMap.get(rootValue);

      // 前序遍历 ->
      // 构造左子树
      root.left = buildFromPreorderAndInorder(preorder, preRoot + 1, inorderIndexMap, inLeft,
        inRoot - 1);
      // 构造右子树
      root.right = buildFromPreorderAndInorder(preorder, preRoot + (inRoot - inLeft) + 1,
        inorderIndexMap, inRoot + 1, inRight);

      return root;
    }

  }

}
