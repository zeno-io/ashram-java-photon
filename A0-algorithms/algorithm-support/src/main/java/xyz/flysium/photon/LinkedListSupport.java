/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import xyz.flysium.photon.linkedlist.DoubleNode;
import xyz.flysium.photon.linkedlist.ListNode;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public final class LinkedListSupport {

  private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

  private LinkedListSupport() {
  }

  /**
   * 随机生成一个固定长度和随机值的链表
   *
   * @param length   固定长度
   * @param maxValue 最大值（不包含）
   * @return 链表头结点
   */
  public static ListNode generateRandomLinkedList(int length, int maxValue) {
    return generateRandomLinkedList(length, length, 0, maxValue);
  }

  /**
   * 随机生成一个随机长度和随机值的链表
   *
   * @param minSize  最少个数（包含）
   * @param maxSize  最多个数（不包含）
   * @param minValue 最小值（包含）
   * @param maxValue 最大值（不包含）
   * @return 链表头结点
   */
  public static ListNode generateRandomLinkedList(int minSize, int maxSize, int minValue,
    int maxValue) {
    minSize = Math.max(minSize, 2);
    maxSize = Math.max(minSize, maxSize);
    maxValue = Math.max(minValue, maxValue);

    int length = RandomSupport.randomValue(minSize, maxSize);
    if (length <= 0) {
      return null;
    }
    ListNode head = new ListNode(RandomSupport.randomValue(minValue, maxValue), null);
    ListNode curr = head;

    for (int i = 1; i < length; i++) {
      curr.next = new ListNode(RandomSupport.randomValue(minValue, maxValue), null);
      curr = curr.next;
    }
    return head;
  }

  /**
   * 随机生成一个固定长度和随机值的双向链表
   *
   * @param length   固定长度
   * @param maxValue 最大值（不包含）
   * @return 双向链表头结点
   */
  public static DoubleNode generateRandomDoubleLinkedList(int length, int maxValue) {
    return generateRandomDoubleLinkedList(length, length, 0, maxValue);
  }

  /**
   * 随机生成一个随机长度和随机值的双向链表
   *
   * @param minSize  最少个数（包含）
   * @param maxSize  最多个数（不包含）
   * @param minValue 最小值（包含）
   * @param maxValue 最大值（不包含）
   * @return 双向链表头结点
   */
  public static DoubleNode generateRandomDoubleLinkedList(int minSize, int maxSize, int minValue,
    int maxValue) {
    minSize = Math.max(minSize, 2);
    maxSize = Math.max(minSize, maxSize);
    maxValue = Math.max(minValue, maxValue);

    int length = RandomSupport.randomValue(minSize, maxSize);
    if (length <= 0) {
      return null;
    }
    DoubleNode head = new DoubleNode(RandomSupport.randomValue(minValue, maxValue));
    DoubleNode curr = head;
    DoubleNode prev = head;

    for (int i = 1; i < length; i++) {
      curr = new DoubleNode(RandomSupport.randomValue(minValue, maxValue));
      curr.prev = prev;
      prev.next = curr;
      prev = curr;
      curr = curr.next;
    }
    return head;
  }

  /**
   * 数组转换为一个链表
   *
   * @param val 数组
   * @return 链表头结点
   */
  public static ListNode toLinkedList(int... val) {
    ListNode next = null;
    for (int i = val.length - 1; i >= 0; i--) {
      ListNode node = new ListNode(val[i]);
      node.next = next;
      next = node;
      if (i == 0) {
        return node;
      }
    }
    return null;
  }

  /**
   * 链表转换为一个数组
   *
   * @param head 链表头结点
   * @return 数组
   */
  public static int[] toArray(ListNode head) {
    List<Integer> l = new LinkedList<>();
    ListNode curr = head;
    while (curr != null) {
      l.add(curr.val);
      curr = curr.next;
    }
    int[] ans = new int[l.size()];
    for (int i = 0; i < ans.length; i++) {
      ans[i] = l.get(i);
    }
    return ans;
  }

  /**
   * 从开始到结尾遍历链表, 要求无环
   *
   * @param head 链表头结点
   * @return 遍历结果
   */
  public static String toString(ListNode head) {
    StringBuilder buf = new StringBuilder();
    ListNode curr = head;
    String pre = "";
    while (curr != null) {
      buf.append(pre).append(curr.val);
      pre = ",";
      curr = curr.next;
    }
    return buf.toString();
  }

  /**
   * 从开始到结尾遍历双向链表, 要求无环
   *
   * @param head 双向链表头结点
   * @return 遍历结果
   */
  public static String toString(DoubleNode head) {
    StringBuilder buf = new StringBuilder();
    DoubleNode curr = head;
    String pre = "";
    while (curr != null) {
      buf.append(pre).append(curr.val);
      pre = ",";
      curr = curr.next;
    }
    return buf.toString();
  }

  /**
   * 在指定位置制作出环
   *
   * @param head 链表头结点
   * @param pos  链表尾结点指向第几个位置
   */
  public static void makeCycle(ListNode head, int pos) {
    ListNode p = head;
    for (int i = 0; i < pos; i++) {
      p = p.next;
    }
    ListNode tail = head;
    while (tail != null && tail.next != null) {
      tail = tail.next;
    }
    assert tail != null;
    tail.next = p;
  }

  /**
   * 仅判断两个链表节点的值是否相等，不考虑后继节点
   *
   * @param a 链表结点1
   * @param b 链表结点2
   * @return 是否相等
   */
  protected static boolean nodeNotEquals(ListNode a, ListNode b) {
    if (a == null) {
      return b != null;
    }
    return !Objects.equals(a.val, b.val);
  }

  /**
   * 仅判断两个双向链表节点的值是否相等，不考虑后继节点
   *
   * @param a 双向链表结点1
   * @param b 双向链表结点2
   * @return 是否相等
   */
  protected static boolean nodeNotEquals(DoubleNode a, DoubleNode b) {
    if (a == null) {
      return b == null;
    }
    return !Objects.equals(a.val, b.val);
  }

  /**
   * 考虑顺序，判断两个链表是否相等, 要求无环
   *
   * @param head1 链表1头结点
   * @param head2 链表2头结点
   * @return 是否相等
   */
  public static boolean equals(ListNode head1, ListNode head2) {
    ListNode p1 = head1;
    ListNode p2 = head2;
    while (p1 != null && p2 != null) {
      if (nodeNotEquals(p1, p2)) {
        return false;
      }
      p1 = p1.next;
      p2 = p2.next;
    }
    return p1 == null && p2 == null;
  }

  /**
   * 考虑顺序，判断两个双向链表是否相等, 要求无环
   *
   * @param head1 双向链表1头结点
   * @param head2 双向链表2头结点
   * @return 是否相等
   */
  public static boolean equals(DoubleNode head1, DoubleNode head2) {
    DoubleNode p1 = head1;
    DoubleNode p2 = head2;
    DoubleNode prev1 = null;
    DoubleNode prev2 = null;
    while (p1 != null && p2 != null) {
      if (nodeNotEquals(p1, p2)) {
        return false;
      }
      prev1 = p1;
      prev2 = p2;
      p1 = p1.next;
      p2 = p2.next;
    }
    if (p1 != null || p2 != null) {
      return false;
    }
    p1 = prev1;
    p2 = prev2;

    while (p1 != null && p2 != null) {
      if (nodeNotEquals(p1, p2)) {
        return false;
      }
      p1 = p1.prev;
      p2 = p2.prev;
    }
    return p1 == null && p2 == null;
  }

  /**
   * 单向链表中找到环的第一个节点，如果没有返回 null
   *
   * @param head 链表头结点
   * @return 环的第一个节点，如果没有返回 null
   */
  public static ListNode getFirstLoopNode(ListNode head) {
    boolean hasLoop = false;
    ListNode curr = head;
    ListNode fast = head;
    while (curr != null && fast.next != null && fast.next.next != null) {
      curr = curr.next;
      fast = fast.next.next;
      // find loop
      if (curr == fast) {
        hasLoop = true;
        break;
      }
    }
    ListNode firstLoopNode = null;
    if (hasLoop) {
      curr = head;
      while (curr != null) {
        curr = curr.next;
        fast = fast.next;
        if (curr == fast) {
          firstLoopNode = curr;
          break;
        }
      }
    }
    return firstLoopNode;
  }

}
