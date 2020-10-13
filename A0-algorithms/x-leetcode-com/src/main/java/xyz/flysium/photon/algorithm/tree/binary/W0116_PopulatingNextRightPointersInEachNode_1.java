package xyz.flysium.photon.algorithm.tree.binary;

import java.util.Deque;
import java.util.LinkedList;
import xyz.flysium.photon.tree.Node;

/**
 * 116. 填充每个节点的下一个右侧节点指针
 * <p>
 * https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node/
 *
 * @author zeno
 */
public interface W0116_PopulatingNextRightPointersInEachNode_1 {

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

    // 7ms
    public Node connect(Node root) {
      if (root == null) {
        return null;
      }
      Deque<Node> q = new LinkedList<>();
      Deque<Node> l = new LinkedList<>();
      Node currEnd = root;
      Node nextEnd = null;

      // level order traversal, and connect
      q.offerLast(root);
      while (!q.isEmpty()) {
        Node node = q.pollFirst();
        // add to level queue
        l.offerLast(node);
        if (node.left != null) {
          q.offerLast(node.left);
          nextEnd = node.left;
        }
        if (node.right != null) {
          q.offerLast(node.right);
          nextEnd = node.right;
        }
        if (node == currEnd) {
          // connect each
          Node pred = null;
          while (!l.isEmpty()) {
            Node e = l.pollFirst();
            if (pred != null) {
              pred.next = e;
            }
            pred = e;
          }
          currEnd = nextEnd;
        }
      }
      return root;
    }

  }

}
