package xyz.flysium.photon.algorithm.linkedlist.basic;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 234. 回文链表
 * <p>
 * https://leetcode-cn.com/problems/palindrome-linked-list/
 *
 * @author zeno
 */
public interface T0234_PalindromeLinkedList {

  // 请判断一个链表是否为回文链表。
  class Solution {

    public boolean isPalindrome(ListNode head) {
      if (head == null) {
        return true;
      }
      ListNode slow = head;
      ListNode fast = head.next;

      // find the middle node
      while (slow != null && fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
      }
      boolean even = fast != null;
      ListNode mid = slow;

      // reverse the right part
      ListNode pred = null;
      ListNode curr = mid.next;
      ListNode next = null;
      while (curr != null) {
        next = curr.next;
        curr.next = pred;
        pred = curr;
        curr = next;
      }
      ListNode tail = pred;

      // check whether it 's palindrome
      boolean palindrome = true;
      ListNode left = head;
      ListNode right = tail;
      while (left != null && right != null) {
        if (!even && left == mid) {
          break;
        }
        if (left.val != right.val) {
          palindrome = false;
          break;
        }
        left = left.next;
        right = right.next;
      }

      // recover the right part
      pred = null;
      curr = tail;
      next = null;
      while (curr != null) {
        next = curr.next;
        curr.next = pred;
        pred = curr;
        curr = next;
      }
      mid.next = pred;

      return palindrome;
    }

  }

}
