package xyz.flysium.photon.algorithm.linkedlist.rotate.medium;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.LinkedListSupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0092_ReverseLinkedListIITest {

  @Test
  public void test() {
    T0092_ReverseLinkedListII.Solution solution = new T0092_ReverseLinkedListII.Solution();
    int[] actual = null;

    actual = LinkedListSupport.toArray(solution
      .reverseBetween(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2,3]")), 2, 3));
    Assert.assertArrayEquals(ArraySupport.newArray("[1,3,2]"), actual);

    actual = LinkedListSupport.toArray(solution
      .reverseBetween(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2]")), 1, 2));
    Assert.assertArrayEquals(ArraySupport.newArray("[2,1]"), actual);

    actual = LinkedListSupport.toArray(solution
      .reverseBetween(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2,3,4,5]")), 1, 3));
    Assert.assertArrayEquals(ArraySupport.newArray("[3,2,1,4,5]"), actual);

    actual = LinkedListSupport.toArray(solution
      .reverseBetween(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2,3,4,5,6,7,8]")), 2,
        4));
    Assert.assertArrayEquals(ArraySupport.newArray("[1,4,3,2,5,6,7,8]"), actual);
  }

}
