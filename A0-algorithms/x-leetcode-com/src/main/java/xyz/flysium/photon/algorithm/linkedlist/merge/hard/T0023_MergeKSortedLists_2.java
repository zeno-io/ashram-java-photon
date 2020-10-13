package xyz.flysium.photon.algorithm.linkedlist.merge.hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 23. 合并K个升序链表
 * <p>
 * https://leetcode-cn.com/problems/merge-k-sorted-lists/
 *
 * @author zeno
 */
public class T0023_MergeKSortedLists_2 {

  // 给你一个链表数组，每个链表都已经按升序排列。
  // 请你将所有链表合并到一个升序链表中，返回合并后的链表。
  static class Solution {

    // 24ms
    public ListNode mergeKLists(ListNode[] lists) {
      if (lists.length == 0) {
        return null;
      }
      if (lists.length == 1) {
        return lists[0];
      }
      List<ListNode> l = new ArrayList<>(lists.length);
      l.addAll(Arrays.asList(lists));

      ListNode dummy = new ListNode(0);
      ListNode prev = dummy;
      ListNode p = null;
      ListNode tmp = null;
      while (notEnd(l)) {
        int minIndex = minValueIndexOf(l);
        int val = l.get(minIndex).val;
        for (int i = 0; i < l.size(); i++) {
          if (l.get(i) == null || l.get(i).val != val) {
            continue;
          }
          p = l.get(i);
          tmp = new ListNode(p.val);
          prev.next = tmp;
          prev = tmp;
          l.set(i, p.next);
        }
      }
      return dummy.next;
    }

    private int minValueIndexOf(List<ListNode> l) {
      int minIndex = -1;
      for (int i = 0; i < l.size(); i++) {
        ListNode p = l.get(i);
        if (p != null && (minIndex < 0 || p.val < l.get(minIndex).val)) {
          minIndex = i;
        }
      }
      return minIndex;
    }

    private boolean notEnd(List<ListNode> l) {
      for (ListNode n : l) {
        if (n != null) {
          return true;
        }
      }
      return false;
    }

  }

}
