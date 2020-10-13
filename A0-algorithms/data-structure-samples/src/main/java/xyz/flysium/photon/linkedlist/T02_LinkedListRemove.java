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

package xyz.flysium.photon.linkedlist;

import xyz.flysium.photon.LinkedListSupport;

/**
 * 把给定值都删除
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T02_LinkedListRemove {


  public static void main(String[] args) {
    T02_LinkedListRemove that = new T02_LinkedListRemove();
    ListNode head = LinkedListSupport.generateRandomLinkedList(10, 100);
    System.out.println(LinkedListSupport.toString(head));
    ListNode head1 = that.removeElement(head, head.val);
    System.out.println(LinkedListSupport.toString(head1));

    head = LinkedListSupport.toLinkedList(1, 1, 1, 1, 2, 2, 2, 3, 4, 5, 5, 5, 5);
    System.out.println(LinkedListSupport.toString(head));
    head1 = that.removeElement(head, 1);
    System.out.println(LinkedListSupport.toString(head1));

    head = LinkedListSupport.toLinkedList(1, 1, 1, 1, 2, 2, 2, 3, 4, 5, 5, 5, 5);
    System.out.println(LinkedListSupport.toString(head));
    head1 = that.removeElement(head, 2);
    System.out.println(LinkedListSupport.toString(head1));

    head = LinkedListSupport.toLinkedList(1, 1, 1, 1, 2, 2, 2, 3, 4, 5, 5, 5, 5);
    System.out.println(LinkedListSupport.toString(head));
    head1 = that.removeElement(head, 5);
    System.out.println(LinkedListSupport.toString(head1));
  }

  public ListNode removeElement(ListNode head, int value) {
    if (head == null) {
      return null;
    }
    while (head != null) {
      if (head.val != value) {
        break;
      }
      head = head.next;
    }
    // head 来到 第一个需要删的位置
    ListNode prev = head;
    ListNode curr = head;

    while (curr != null) {
      if (curr.val != value) {
        prev.next = curr.next;
      } else {
        prev = curr;
      }
      curr = curr.next;
    }
    return head;
  }

}

