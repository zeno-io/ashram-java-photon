package xyz.flysium.photon.algorithm.linkedlist.remove.easy;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 237. 删除链表中的节点
 * <p>
 * https://leetcode-cn.com/problems/delete-node-in-a-linked-list/
 *
 * @author zeno
 */
public class T0237_DeleteNodeInALinkedList {

  /**
   * Definition for singly-linked list.
   * <pre>
   *   public class ListNode {
   *       int val;
   *       ListNode next;
   *       ListNode(int x) { val = x; }
   *   }
   * </pre>
   */
  static class Solution {

    // 请编写一个函数，使其可以删除某个链表中给定的（非末尾）节点。传入函数的唯一参数为 要被删除的节点 。
    public void deleteNode(ListNode node) {
      ListNode prev = node;
      ListNode curr = node;
      while (curr.next != null) {
        curr.val = curr.next.val;
        prev = curr;
        curr = curr.next;
      }
      prev.next = null;
    }

  }

}
