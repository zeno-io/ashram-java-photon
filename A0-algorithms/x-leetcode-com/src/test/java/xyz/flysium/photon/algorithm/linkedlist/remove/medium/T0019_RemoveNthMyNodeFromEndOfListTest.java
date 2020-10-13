package xyz.flysium.photon.algorithm.linkedlist.remove.medium;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.LinkedListSupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0019_RemoveNthMyNodeFromEndOfListTest {

  @Test
  public void test() {
    T0019_RemoveNthNodeFromEndOfList.Solution solution = new T0019_RemoveNthNodeFromEndOfList.Solution();
    int[] actual = null;

    actual = LinkedListSupport.toArray(solution
      .removeNthFromEnd(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1]")), 1));
    Assert.assertArrayEquals(ArraySupport.newArray("[]"), actual);

    actual = LinkedListSupport.toArray(solution
      .removeNthFromEnd(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2]")), 1));
    Assert.assertArrayEquals(ArraySupport.newArray("[1]"), actual);

    actual = LinkedListSupport.toArray(solution
      .removeNthFromEnd(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2,3]")), 1));
    Assert.assertArrayEquals(ArraySupport.newArray("[1,2]"), actual);

    actual = LinkedListSupport.toArray(solution
      .removeNthFromEnd(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2,3,4]")), 1));
    Assert.assertArrayEquals(ArraySupport.newArray("[1,2,3]"), actual);

    actual = LinkedListSupport.toArray(solution
      .removeNthFromEnd(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2]")), 2));
    Assert.assertArrayEquals(ArraySupport.newArray("[2]"), actual);

    actual = LinkedListSupport.toArray(solution
      .removeNthFromEnd(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2,3]")), 2));
    Assert.assertArrayEquals(ArraySupport.newArray("[1,3]"), actual);

    actual = LinkedListSupport.toArray(solution
      .removeNthFromEnd(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2,3,4]")), 2));
    Assert.assertArrayEquals(ArraySupport.newArray("[1,2,4]"), actual);

    actual = LinkedListSupport.toArray(solution
      .removeNthFromEnd(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2,3,4,5]")), 2));
    Assert.assertArrayEquals(ArraySupport.newArray("[1,2,3,5]"), actual);

    actual = LinkedListSupport.toArray(solution
      .removeNthFromEnd(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2,3]")), 3));
    Assert.assertArrayEquals(ArraySupport.newArray("[2,3]"), actual);

    actual = LinkedListSupport.toArray(solution
      .removeNthFromEnd(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2,3,4]")), 3));
    Assert.assertArrayEquals(ArraySupport.newArray("[1,3,4]"), actual);

    actual = LinkedListSupport.toArray(solution
      .removeNthFromEnd(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2,3,4,5]")), 3));
    Assert.assertArrayEquals(ArraySupport.newArray("[1,2,4,5]"), actual);

    actual = LinkedListSupport.toArray(solution
      .removeNthFromEnd(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2,3,4,5]")), 4));
    Assert.assertArrayEquals(ArraySupport.newArray("[1,3,4,5]"), actual);

  }
}
