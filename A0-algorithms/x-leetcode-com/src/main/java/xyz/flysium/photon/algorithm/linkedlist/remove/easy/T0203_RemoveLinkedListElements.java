package xyz.flysium.photon.algorithm.linkedlist.remove.easy;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 203. 移除链表元素
 * <p>
 * https://leetcode-cn.com/problems/remove-linked-list-elements/
 *
 * @author zeno
 */
public class T0203_RemoveLinkedListElements {

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
  // 删除链表中等于给定值 val 的所有节点。
  static class Solution {

    public ListNode removeElements(ListNode head, int val) {
      if (head == null) {
        return null;
      }
      ListNode dummy = new ListNode(0);
      dummy.next = head;
      ListNode pred = dummy;
      ListNode curr = head;
      ListNode next = null;
      while (curr != null) {
        next = curr.next;
        if (curr.val == val) {
          pred.next = next;
        } else {
          pred = curr;
        }
        curr = next;
      }
      return dummy.next;
    }

  }

}
