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
public class T21_PartitionLinkedListLessEqualMore {

  public static void main(String[] args) {
    ListNode head1 = new ListNode(7);
    head1.next = new ListNode(9);
    head1.next.next = new ListNode(1);
    head1.next.next.next = new ListNode(8);
    head1.next.next.next.next = new ListNode(5);
    head1.next.next.next.next.next = new ListNode(2);
    head1.next.next.next.next.next.next = new ListNode(5);
    System.out.println(LinkedListSupport.toString(head1));
    //head1 = new T21_PartitionLinkedListLessEqualMore().listPartition1(head1, 4);
    head1 = new T21_PartitionLinkedListLessEqualMore().listPartition2(head1, 5);
    System.out.println(LinkedListSupport.toString(head1));
  }

  // 把链表放入数组里，在数组上做 partition（笔试用）
  private ListNode listPartition1(ListNode head1, int pivot) {
    if (head1 == null) {
      return null;
    }
    int len = 0;
    ListNode c = head1;
    while (c != null) {
      c = c.next;
      len++;
    }
    ListNode[] arr = new ListNode[len];
    int i = 0;
    c = head1;
    while (c != null) {
      arr[i++] = c;
      c = c.next;
    }
    // arr partition
    int ltIndex = -1;
    int gtIndex = len;
    int index = 0;
    while (index < gtIndex) {
      if (arr[index].val < pivot) {
        ltIndex++;
        swap(arr, ltIndex, index);
        index++;
      } else if (arr[index].val == pivot) {
        index++;
      } else {
        gtIndex--;
        swap(arr, gtIndex, index);
      }
    }
    for (i = 1; i != arr.length; i++) {
      arr[i - 1].next = arr[i];
    }
    arr[i - 1].next = null;
    return arr[0];
  }

  private void swap(ListNode[] array, int i, int j) {
    if (i == j) {
      return;
    }
    ListNode tmp = array[i];
    array[i] = array[j];
    array[j] = tmp;
  }

  // 分成小、中、大三部分，再把各个部分之间串起来（面试用）
  private ListNode listPartition2(ListNode head, int pivot) {
    if (head == null) {
      return null;
    }
    // less than head tail
    ListNode ltDummyHead = new ListNode(0);
    ListNode ltCurr = ltDummyHead;
    // equal head tail
    ListNode eqDummyHead = new ListNode(0);
    ListNode eqCurr = eqDummyHead;
    // great than head tail
    ListNode gtDummyHead = new ListNode(0);
    ListNode gtCurr = gtDummyHead;
    // current,  next node
    ListNode next = null;
    ListNode curr = head;

    while (curr != null) {
      next = curr.next;
      curr.next = null;
      if (curr.val < pivot) {
        ltCurr.next = curr;
        ltCurr = curr;
      } else if (curr.val == pivot) {
        eqCurr.next = curr;
        eqCurr = curr;
      } else {
        gtCurr.next = curr;
        gtCurr = curr;
      }
      curr = next;
    }
    ListNode dummy = new ListNode(0);
    if (ltDummyHead.next != null) {
      dummy.next = ltDummyHead.next;
      if (eqDummyHead.next != null) {
        ltCurr.next = eqDummyHead.next;
        eqCurr.next = gtDummyHead.next;
      } else if (gtDummyHead.next != null) {
        ltCurr.next = gtDummyHead.next;
      }
    } else if (eqDummyHead.next != null) {
      dummy.next = eqDummyHead.next;
      eqCurr.next = gtDummyHead.next;
    } else if (gtDummyHead.next != null) {
      dummy.next = gtDummyHead.next;
    }

    return dummy.next;
  }

}
