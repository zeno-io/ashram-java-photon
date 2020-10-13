package xyz.flysium.photon.algorithm.linkedlist.basic;

import xyz.flysium.photon.linkedlist.Node;

/**
 * 138. 复制带随机指针的链表
 * <p>
 * https://leetcode-cn.com/problems/copy-list-with-random-pointer/
 *
 * @author zeno
 */
public interface U0138_CopyListWithRandomPointer {

  // 给定一个链表，每个节点包含一个额外增加的随机指针，该指针可以指向链表中的任何节点或空节点。
  // 要求返回这个链表的 深拷贝。
  /*
  // Definition for a Node.
  class Node {
      int val;
      Node next;
      Node random;

      public Node(int val) {
          this.val = val;
          this.next = null;
          this.random = null;
      }
  }
  */
  class Solution {

    public Node copyRandomList(Node head) {
      Node dummy = new Node(0);
      Node pred = null;
      Node curr = null;
      Node next = null;

      //  handle the copy
      curr = head;
      while (curr != null) {
        next = curr.next;

        // insert into curr and next
        curr.next = new Node(curr.val);
        curr.next.next = next;

        pred = curr;
        curr = next;
      }

      // handle the random
      curr = head;
      while (curr != null) {
//        Node copied = curr.next; //hash.get(curr);
        if (curr.random != null) {
          curr.next.random = curr.random.next;//hash.get(curr.random);
        }
//        copy.next = copied;
//        copy = copied;
//        curr = curr.next;
        curr = curr.next.next;
      }

      // separate
      Node copy = dummy;
      curr = head;
      while (curr != null) {
        // link
        Node copied = curr.next;
        copy.next = copied;
        copy = copied;
        // separate
        curr.next = curr.next.next;
        // go to next
        curr = curr.next;
      }

      return dummy.next;
    }

  }

}
