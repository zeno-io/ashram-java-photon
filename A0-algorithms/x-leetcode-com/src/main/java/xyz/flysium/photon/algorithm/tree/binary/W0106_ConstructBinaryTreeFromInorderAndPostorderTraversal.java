package xyz.flysium.photon.algorithm.tree.binary;

import java.util.Deque;
import java.util.LinkedList;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 106. 从中序与后序遍历序列构造二叉树
 * <p>
 * https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
 *
 * @author zeno
 */
public interface W0106_ConstructBinaryTreeFromInorderAndPostorderTraversal {

  //根据一棵树的中序遍历与后序遍历构造二叉树。
  //注意:
  //你可以假设树中没有重复的元素。
  //
  //例如，给出
  //
  //中序遍历 inorder = [9,3,15,20,7]  左头右
  //后序遍历 postorder = [9,15,7,20,3] 左右头
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
    //    inorder = [15, 9, 10, 3, 20, 5, 7, 8, 4]
    //    postorder = [15, 10, 9, 5, 4, 8, 7, 20, 3]
    //
    //       3
    //       / \
    //      9  20
    //     / \   \
    //    15 10   7
    //           / \
    //          5   8
    //               \
    //                4
    //
    public TreeNode buildTree(int[] inorder, int[] postorder) {
      if (postorder.length == 0) {
        return null;
      }
      TreeNode root = new TreeNode(postorder[postorder.length - 1]);
      // 栈维护当前节点的所有还没有考虑过左儿子的祖先节点
      Deque<TreeNode> stack = new LinkedList<>();
      int in = inorder.length - 1;

      stack.push(root);
      int post = postorder.length - 2;
      while (post >= 0) {
        TreeNode node = stack.peek();
        int value = postorder[post];
        // 判断栈顶元素是否与中序的一致，如果一致，说明是前序连续的元素是栈顶元素的右孩子
        if (inorder[in] != node.val) {
          TreeNode e = new TreeNode(value);
          node.right = e;
          stack.push(e);
        }
        // 否则，后序中的元素是栈中某一个祖先元素的左孩子
        else {
          // 栈中的节点的顺序和它们在反向前序遍历中出现的顺序是一致的，
          // 而且每一个节点的左儿子都还没有被遍历过，那么这些节点的顺序和它们在反向中序遍历中出现的顺序一定是相反的。
          // 因此我们可以通过比较栈中的元素，与中序的元素，如果一致，弹出，直到不一致
          while (!stack.isEmpty() && inorder[in] == stack.peek().val) {
            node = stack.pop();
            in--;
          }
          // 找到，并关联为左孩子
          TreeNode e = new TreeNode(value);
          node.left = e;
          stack.push(e);
        }
        post--;
      }

      return root;
    }

  }

}
