package xyz.flysium.photon.algorithm.linkedlist.remove.medium;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 19. 删除链表的倒数第N个节点
 * <p>
 * https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
 *
 * @author zeno
 */
public class T0019_RemoveNthNodeFromEndOfList {

  // 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
  static class Solution {

    public ListNode removeNthFromEnd(ListNode head, int n) {
      if (head == null || head.next == null) {
        return null;
      }
      ListNode curr = head;

      for (int i = 0; i < n; i++) {
        curr = curr.next;
      }

      if (curr == null) {
        return head.next;
      }
      ListNode pred = head;

      while (curr.next != null) {
        curr = curr.next;
        pred = pred.next;
      }
      pred.next = pred.next.next;

      return head;
    }

  }

}
