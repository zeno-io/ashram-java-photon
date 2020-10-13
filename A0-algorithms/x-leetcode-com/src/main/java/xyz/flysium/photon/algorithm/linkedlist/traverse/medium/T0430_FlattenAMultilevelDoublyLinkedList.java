package xyz.flysium.photon.algorithm.linkedlist.traverse.medium;

import xyz.flysium.photon.linkedlist.Node;

/**
 * 430. 扁平化多级双向链表
 * <p>
 * https://leetcode-cn.com/problems/flatten-a-multilevel-doubly-linked-list/
 *
 * @author zeno
 */
public class T0430_FlattenAMultilevelDoublyLinkedList {

  /*
    // Definition for a Node.
    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    };
    */
  static class Solution {

    public Node flatten(Node head) {
      Node parent = null;
      Node parentNext = null;
      Node childHead = null;
      Node childTail = null;
      Node curr = head;

      while (curr != null) {
        if (curr.child != null) {
          parent = curr;
          parentNext = parent.next;
          childHead = curr.child;

          // find the tail of child
          curr = childHead;
          while (curr.next != null) {
            curr = curr.next;
          }
          childTail = curr;

          // connect child in [parent, parentNext]
          parent.next = childHead;
          childHead.prev = parent;
          childTail.next = parentNext;
          if (parentNext != null) {
            parentNext.prev = childTail;
          }
          parent.child = null;

          curr = childHead;
          continue;
        }
        curr = curr.next;
      }

      return head;
    }

  }

}
