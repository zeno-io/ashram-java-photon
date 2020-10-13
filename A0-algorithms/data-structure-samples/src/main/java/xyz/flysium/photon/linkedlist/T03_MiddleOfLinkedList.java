package xyz.flysium.photon.linkedlist;

import java.util.ArrayList;

/**
 * 找到链表的中间节点？
 *
 * @author zeno
 */
public class T03_MiddleOfLinkedList {

  public static void main(String[] args) {
    ListNode test = null;
    test = new ListNode(0);
    test.next = new ListNode(1);
    test.next.next = new ListNode(2);
    test.next.next.next = new ListNode(3);
    test.next.next.next.next = new ListNode(4);
    test.next.next.next.next.next = new ListNode(5);
    test.next.next.next.next.next.next = new ListNode(6);
    test.next.next.next.next.next.next.next = new ListNode(7);
    test.next.next.next.next.next.next.next.next = new ListNode(8);

    ListNode ans1 = null;
    ListNode ans2 = null;

    ans1 = midOrUpMidNode(test);
    ans2 = right1(test);
    System.out.println(ans1 != null ? ans1.val : "无");
    System.out.println(ans2 != null ? ans2.val : "无");

    ans1 = midOrDownMidNode(test);
    ans2 = right2(test);
    System.out.println(ans1 != null ? ans1.val : "无");
    System.out.println(ans2 != null ? ans2.val : "无");

    ans1 = midOrUpMidPreNode(test);
    ans2 = right3(test);
    System.out.println(ans1 != null ? ans1.val : "无");
    System.out.println(ans2 != null ? ans2.val : "无");

    ans1 = midOrDownMidPreNode(test);
    ans2 = right4(test);
    System.out.println(ans1 != null ? ans1.val : "无");
    System.out.println(ans2 != null ? ans2.val : "无");
  }

  // 奇数长度返回中点，偶数长度返回上中点
  // index((len-1)/2)
  // 如 长度为3，返回第 1 个节点（0~2）；  长度为4，返回第 1 个节点（0~3）；
  public static ListNode midOrUpMidNode(ListNode head) {
    if (head == null || head.next == null || head.next.next == null) {
      return head;
    }
    // 链表有3个点或以上
    ListNode slow = head.next;
    ListNode fast = head.next.next;
    while (fast.next != null && fast.next.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    return slow;
  }

  // 奇数长度返回中点，偶数长度返回下中点
  // index(len/2)
  // 如 长度为3，返回第 1 个节点（0~2）；  长度为4，返回第 2 个节点（0~3）；
  public static ListNode midOrDownMidNode(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }
    ListNode slow = head.next;
    ListNode fast = head.next;
    while (fast.next != null && fast.next.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    return slow;
  }

  // 奇数长度返回中点前一个，偶数长度返回上中点前一个
  // index((len-1)/2)-1 或  index((len-3)/2)
  // 如 长度为3，返回第 0 个节点（0~2）；  长度为4，返回第 0 个节点（0~3）；
  public static ListNode midOrUpMidPreNode(ListNode head) {
    if (head == null || head.next == null || head.next.next == null) {
      return null;
    }
    ListNode slow = head;
    ListNode fast = head.next.next;
    while (fast.next != null && fast.next.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    return slow;
  }

  // 奇数长度返回中点前一个，偶数长度返回下中点前一个
  // index(len/2)-1 或  index((len-2)/2)
  // 如 长度为3，返回第 0 个节点（0~2）；  长度为4，返回第 1 个节点（0~3）；
  public static ListNode midOrDownMidPreNode(ListNode head) {
    if (head == null || head.next == null) {
      return null;
    }
    if (head.next.next == null) {
      return head;
    }
    ListNode slow = head;
    ListNode fast = head.next;
    while (fast.next != null && fast.next.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    return slow;
  }

  public static ListNode right1(ListNode head) {
    if (head == null) {
      return null;
    }
    ListNode cur = head;
    ArrayList<ListNode> arr = new ArrayList<>();
    while (cur != null) {
      arr.add(cur);
      cur = cur.next;
    }
    return arr.get((arr.size() - 1) / 2);
  }

  public static ListNode right2(ListNode head) {
    if (head == null) {
      return null;
    }
    ListNode cur = head;
    ArrayList<ListNode> arr = new ArrayList<>();
    while (cur != null) {
      arr.add(cur);
      cur = cur.next;
    }
    return arr.get(arr.size() / 2);
  }

  public static ListNode right3(ListNode head) {
    if (head == null || head.next == null || head.next.next == null) {
      return null;
    }
    ListNode cur = head;
    ArrayList<ListNode> arr = new ArrayList<>();
    while (cur != null) {
      arr.add(cur);
      cur = cur.next;
    }
    return arr.get((arr.size() - 3) / 2);
  }

  public static ListNode right4(ListNode head) {
    if (head == null || head.next == null) {
      return null;
    }
    ListNode cur = head;
    ArrayList<ListNode> arr = new ArrayList<>();
    while (cur != null) {
      arr.add(cur);
      cur = cur.next;
    }
    return arr.get((arr.size() - 2) / 2);
  }

}
