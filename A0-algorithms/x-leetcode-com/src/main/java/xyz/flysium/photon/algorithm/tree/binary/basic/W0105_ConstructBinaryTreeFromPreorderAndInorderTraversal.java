package xyz.flysium.photon.algorithm.tree.binary.basic;

import java.util.Deque;
import java.util.LinkedList;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 105. 从前序与中序遍历序列构造二叉树
 * <p>
 * https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 *
 * @author zeno
 */
public interface W0105_ConstructBinaryTreeFromPreorderAndInorderTraversal {

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

    //
    //    preorder = [3, 9, 8, 5, 4, 10, 20, 15, 7]
    //    inorder = [4, 5, 8, 10, 9, 3, 15, 20, 7]
    //         3
    //        / \
    //       9  20
    //      /  /  \
    //     8  15   7
    //    / \
    //   5  10
    //  /
    // 4
    public TreeNode buildTree(int[] preorder, int[] inorder) {
      if (preorder.length == 0) {
        return null;
      }
      TreeNode root = new TreeNode(preorder[0]);
      // 栈维护当前节点的所有还没有考虑过右儿子的祖先节点
      Deque<TreeNode> stack = new LinkedList<>();
      int in = 0;

      stack.push(root);
      int pre = 1;
      while (pre < preorder.length) {
        TreeNode node = stack.peek();
        int value = preorder[pre];
        // 判断栈顶元素是否与中序的一致，如果一致，说明是前序连续的元素是栈顶元素的左孩子
        if (inorder[in] != node.val) {
          TreeNode e = new TreeNode(value);
          node.left = e;
          stack.push(e);
        }
        // 否则，前序中的元素是栈中某一个祖先元素的右孩子
        else {
          // 栈中的节点的顺序和它们在前序遍历中出现的顺序是一致的，
          // 而且每一个节点的右儿子都还没有被遍历过，那么这些节点的顺序和它们在中序遍历中出现的顺序一定是相反的。
          // 因此我们可以通过比较栈中的元素，与中序的元素，如果一致，弹出，直到不一致
          while (!stack.isEmpty() && inorder[in] == stack.peek().val) {
            node = stack.pop();
            in++;
          }
          // 找到，并关联为右孩子
          TreeNode e = new TreeNode(value);
          node.right = e;
          stack.push(e);
        }
        pre++;
      }

      return root;
    }

  }

}
