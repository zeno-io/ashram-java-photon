package xyz.flysium.photon.algorithm.tree.binary;

import xyz.flysium.photon.tree.Node;

/**
 * 117. 填充每个节点的下一个右侧节点指针 II
 * <p>
 * https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node-ii/
 *
 * @author zeno
 */
public interface W0117_PopulatingNextRightPointersInEachNodeII {

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

    // 0ms
    public Node connect(Node root) {
      if (root == null) {
        return null;
      }
      // 使用已建立的 next 指针
      Node curr = root;

      while (curr != null) {
        Node nextLevelStartDummy = new Node(0);
        Node l = nextLevelStartDummy;
        while (curr != null) {
          // try to connect left child
          if (curr.left != null) {
            l.next = curr.left;
            l = l.next;
          }
          // try to connect right child
          if (curr.right != null) {
            l.next = curr.right;
            l = l.next;
          }
          curr = curr.next;
        }
        if (l == nextLevelStartDummy) {
          break;
        }
        // go to nextLevelStart
        curr = nextLevelStartDummy.next;
      }

      return root;
    }

  }

}
