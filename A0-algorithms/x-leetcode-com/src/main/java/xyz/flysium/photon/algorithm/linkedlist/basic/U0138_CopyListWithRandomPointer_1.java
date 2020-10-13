package xyz.flysium.photon.algorithm.linkedlist.basic;

import java.util.HashMap;
import xyz.flysium.photon.linkedlist.Node;

/**
 * 138. 复制带随机指针的链表
 * <p>
 * https://leetcode-cn.com/problems/copy-list-with-random-pointer/
 *
 * @author zeno
 */
public interface U0138_CopyListWithRandomPointer_1 {

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
      HashMap<Node, Node> hash = new HashMap<>();

      Node curr = head;
      while (curr != null) {
        hash.put(curr, new Node(curr.val));
        curr = curr.next;
      }

      Node copy = dummy;
      curr = head;
      while (curr != null) {
        Node copied = hash.get(curr);
        copied.random = hash.get(curr.random);
        copy.next = copied;
        copy = copied;

        curr = curr.next;
      }

      return dummy.next;
    }

  }

}
