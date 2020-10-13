package xyz.flysium.photon.algorithm.tree.binary;

import xyz.flysium.photon.tree.Node;

/**
 * 116. 填充每个节点的下一个右侧节点指针
 * <p>
 * https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node/
 *
 * @author zeno
 */
public interface W0116_PopulatingNextRightPointersInEachNode {

  // 给定一个完美二叉树，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
  //
  // struct Node {
  //  int val;
  //  Node *left;
  //  Node *right;
  //  Node *next;
  // }
  //
  // 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
  //
  // 初始状态下，所有 next 指针都被设置为 NULL。

  // 你只能使用常量级额外空间。
  // 使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。
  class Solution {

    public Node connect(Node root) {
      if (root == null) {
        return null;
      }
      // 使用已建立的 next 指针
      Node curr = root;
      Node nextStart = curr.left;

      while (curr.left != null) {
        nextStart = curr.left;
        while (curr != null) {
          // connect left and right child
          curr.left.next = curr.right;
          // connect right child and the left child of next node
          if (curr.next != null) {
            curr.right.next = curr.next.left;
          }
          curr = curr.next;
        }
        // next start
        curr = nextStart;
      }

      return root;
    }

  }

}
