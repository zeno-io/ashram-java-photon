package xyz.flysium.photon.algorithm.linkedlist.rotate.medium;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 24. 两两交换链表中的节点
 * <p>
 * https://leetcode-cn.com/problems/swap-nodes-in-pairs/
 *
 * @author zeno
 */
public class T0024_SwapNodesInPairs {

  //  给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
  //  你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
  static class Solution {

    public ListNode swapPairs(ListNode head) {
      if (head == null) {
        return null;
      }
      if (head.next == null) {
        return head;
      }
      if (head.next.next == null) {
        ListNode c = head.next;
        c.next = head;
        head.next = null;
        return c;
      }
      ListNode ans = head.next;
      ListNode x = head;
      ListNode y = head;
      ListNode n = null;
      ListNode p = null;

      while (x != null && x.next != null) {
        y = x.next;
        n = y.next;

        x.next = n;
        y.next = x;
        if (p != null) {
          p.next = y;
        }

        p = x;
        x = n;
      }

      return ans;
    }

  }

}
