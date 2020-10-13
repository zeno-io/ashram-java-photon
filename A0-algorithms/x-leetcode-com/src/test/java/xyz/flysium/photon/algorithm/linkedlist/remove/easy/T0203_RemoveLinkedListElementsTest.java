package xyz.flysium.photon.algorithm.linkedlist.remove.easy;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.LinkedListSupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0203_RemoveLinkedListElementsTest {

  @Test
  public void test() {
    xyz.flysium.photon.algorithm.linkedlist.remove.easy.T0203_RemoveLinkedListElements.Solution solution = new xyz.flysium.photon.algorithm.linkedlist.remove.easy.T0203_RemoveLinkedListElements.Solution();
    int[] actual = null;

    actual = LinkedListSupport.toArray(solution
      .removeElements(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,1]")), 1));
    Assert.assertArrayEquals(ArraySupport.newArray("[]"), actual);

    actual = LinkedListSupport.toArray(solution
      .removeElements(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2,2,1]")), 2));
    Assert.assertArrayEquals(ArraySupport.newArray("[1,1]"), actual);

    actual = LinkedListSupport.toArray(solution
      .removeElements(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2,6,3,4,5,6]")), 6));
    Assert.assertArrayEquals(ArraySupport.newArray("[1,2,3,4,5]"), actual);

    actual = LinkedListSupport.toArray(solution
      .removeElements(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2,6,3,4,5,1]")), 1));
    Assert.assertArrayEquals(ArraySupport.newArray("[2,6,3,4,5]"), actual);
  }

}
