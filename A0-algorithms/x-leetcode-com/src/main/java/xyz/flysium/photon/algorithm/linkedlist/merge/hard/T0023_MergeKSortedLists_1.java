package xyz.flysium.photon.algorithm.linkedlist.merge.hard;

import java.util.PriorityQueue;
import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 23. 合并K个升序链表
 * <p>
 * https://leetcode-cn.com/problems/merge-k-sorted-lists/
 *
 * @author zeno
 */
public class T0023_MergeKSortedLists_1 {

  // 给你一个链表数组，每个链表都已经按升序排列。
  // 请你将所有链表合并到一个升序链表中，返回合并后的链表。
  static class Solution {

    // 6ms
    public ListNode mergeKLists(ListNode[] lists) {
      if (lists.length == 0) {
        return null;
      }
      if (lists.length == 1) {
        return lists[0];
      }
      PriorityQueue<Integer> q = new PriorityQueue<>();
      for (ListNode l : lists) {
        ListNode c = l;
        while (c != null) {
          q.offer(c.val);
          c = c.next;
        }
      }
      if (q.isEmpty()) {
        return null;
      }
      ListNode dummy = new ListNode(0);
      ListNode prev = dummy;
      ListNode n = null;

      while (!q.isEmpty()) {
        n = new ListNode(q.poll());
        prev.next = n;
        prev = n;
      }

      return dummy.next;
    }

  }

}
