package xyz.flysium.photon.linkedlist;

import xyz.flysium.photon.LinkedListSupport;

/**
 * 将单向链表按某值划分成左边小、中间相等、右边大的形式
 * <p>
 * 1）把链表放入数组里，在数组上做 partition（笔试用）
 * <p>
 * 2）分成小、中、大三部分，再把各个部分之间串起来（面试用）
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T21_PartitionLinkedListSmallEqualBig {

  public static void main(String[] args) {
    ListNode head1 = new ListNode(7);
    head1.next = new ListNode(9);
    head1.next.next = new ListNode(1);
    head1.next.next.next = new ListNode(8);
    head1.next.next.next.next = new ListNode(5);
    head1.next.next.next.next.next = new ListNode(2);
    head1.next.next.next.next.next.next = new ListNode(5);
    System.out.println(LinkedListSupport.toString(head1));
    // head1 = new T21_PartitionLinkedListSmallEqualBig().listPartition1(head1, 4);
    head1 = new T21_PartitionLinkedListSmallEqualBig().listPartition2(head1, 5);
    System.out.println(LinkedListSupport.toString(head1));
  }

  private ListNode listPartition2(ListNode head1, int i) {

    return head1;
  }


}
