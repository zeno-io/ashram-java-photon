package xyz.flysium.photon.algorithm.tree.binary.basic;

import xyz.flysium.photon.tree.Node;

/**
 * 117. 填充每个节点的下一个右侧节点指针 II
 * <p>
 * https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node-ii/
 *
 * @author zeno
 */
public interface W0117_PopulatingNextRightPointersInEachNodeII_1 {

  // 给定一个二叉树：
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

    // 1ms
    public Node connect(Node root) {
      if (root == null) {
        return null;
      }
      // 使用已建立的 next 指针
      Node currLevelStart = root;
      Node curr = null;
      Node nextLevelStart = null;

      while (currLevelStart != null) {
        curr = currLevelStart;
        nextLevelStart = null;
        while (curr != null) {
          // try to connect left and right
          if (curr.left != null) {
            curr.left.next = curr.right;
          }
          // try to connect last (left, right?) child
          // and the first (left, right?) child of next available node (has children)
          Node childEnd = curr.right != null ? curr.right : curr.left;
          if (childEnd != null) {
            Node available = curr.next;
            while (available != null) {
              if (available.left != null || available.right != null) {
                break;
              }
              available = available.next;
            }
            // if available
            if (available != null) {
              childEnd.next = (available.left != null) ? available.left : available.right;
            }
            //  nextLevelStart
            if (nextLevelStart == null) {
              nextLevelStart = curr.left != null ? curr.left : curr.right;
            }
          }
          curr = curr.next;
        }
        // go to nextLevelStart
        currLevelStart = nextLevelStart;
      }

      return root;
    }

  }

}
