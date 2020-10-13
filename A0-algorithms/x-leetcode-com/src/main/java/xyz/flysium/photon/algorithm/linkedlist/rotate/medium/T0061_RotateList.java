package xyz.flysium.photon.algorithm.linkedlist.rotate.medium;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 61. 旋转链表
 * <p>
 * https://leetcode-cn.com/problems/rotate-list/
 *
 * @author zeno
 */
public class T0061_RotateList {

  // 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
  static class Solution {

    public ListNode rotateRight(ListNode head, int k) {
      if (head == null) {
        return head;
      }
      ListNode curr = head;
      final int length = length(head);
      int t = k % length;
      if (t == 0) {
        return head;
      }
      ListNode fast = head;
      int i = 0;
      while (i < t && fast != null) {
        i++;
        fast = fast.next;
      }
      while (curr != null && fast.next != null) {
        curr = curr.next;
        fast = fast.next;
      }
      ListNode newHead = curr.next;
      curr.next = null;
      fast.next = head;
      return newHead;
    }

    private int length(ListNode head) {
      ListNode curr = head;
      int length = 0;
      while (curr != null) {
        length++;
        curr = curr.next;
      }
      return length;
    }

  }

}
