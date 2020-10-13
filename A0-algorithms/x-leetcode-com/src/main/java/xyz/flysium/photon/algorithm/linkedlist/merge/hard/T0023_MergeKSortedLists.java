package xyz.flysium.photon.algorithm.linkedlist.merge.hard;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 23. 合并K个升序链表
 * <p>
 * https://leetcode-cn.com/problems/merge-k-sorted-lists/
 *
 * @author zeno
 */
public class T0023_MergeKSortedLists {

  // 给你一个链表数组，每个链表都已经按升序排列。
  // 请你将所有链表合并到一个升序链表中，返回合并后的链表。
  static class Solution {

    public ListNode mergeKLists(ListNode[] lists) {
      return merge(lists, 0, lists.length - 1);
    }

    public ListNode merge(ListNode[] lists, int l, int r) {
      if (l == r) {
        return lists[l];
      }
      if (l > r) {
        return null;
      }
      int mid = (l + r) >> 1;
      ListNode ln = merge(lists, l, mid);
      ListNode rn = merge(lists, mid + 1, r);
      return mergeTwoLists(ln, rn);
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
      ListNode dummy = new ListNode(0);
      ListNode prev = dummy;
      ListNode p1 = l1, p2 = l2;
      boolean g1, g2;
      ListNode tmp = null;
      while (p1 != null || p2 != null) {
        g1 = p2 == null || (p1 != null && p1.val <= p2.val);
        g2 = p1 == null || (p2 != null && p2.val <= p1.val);
        if (g1) {
          tmp = new ListNode(p1.val);
          prev.next = tmp;
          prev = tmp;
          p1 = p1.next;
        }
        if (g2) {
          tmp = new ListNode(p2.val);
          prev.next = tmp;
          prev = tmp;
          p2 = p2.next;
        }
      }

      return dummy.next;
    }

  }

}
