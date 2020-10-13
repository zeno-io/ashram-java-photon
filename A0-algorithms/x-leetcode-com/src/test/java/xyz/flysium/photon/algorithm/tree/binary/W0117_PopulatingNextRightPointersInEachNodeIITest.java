package xyz.flysium.photon.algorithm.tree.binary;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.StringSupport;
import xyz.flysium.photon.TreeSupport;
import xyz.flysium.photon.tree.Node;

/**
 * TODO description
 *
 * @author zeno
 */
public class W0117_PopulatingNextRightPointersInEachNodeIITest {

  @Test
  public void test() {
    W0117_PopulatingNextRightPointersInEachNodeII.Solution solution = new W0117_PopulatingNextRightPointersInEachNodeII.Solution();
    final String levelChar = "#";
    Node root = null;
    String[] actual = null;
    String[] expects = null;

    actual = TreeSupport.toStringsByNextPointers(solution.connect(
      TreeSupport.newBinaryTree2(3, 9, 20, null, null, 15, 7)), levelChar);
    expects = ArraySupport.newStringArray(
      StringSupport.appendStringForJSON("[3,#,9,20,#,15,7,#]"));
    Assert.assertArrayEquals(expects, actual);

    actual = TreeSupport.toStringsByNextPointers(solution.connect(
      TreeSupport.newBinaryTree2(1, 2, 3, 4, 5, null, 7)), levelChar);
    expects = ArraySupport.newStringArray(
      StringSupport.appendStringForJSON("[1,#,2,3,#,4,5,7,#]"));
    Assert.assertArrayEquals(expects, actual);

    root = solution.connect(
      TreeSupport.newBinaryTree2(1, 2, 3, 4, 5, null, 6, 7, null, null, null, null, 8));
    actual = TreeSupport.toStringsByNextPointers(root, levelChar);
    expects = ArraySupport.newStringArray(
      StringSupport.appendStringForJSON("[1,#,2,3,#,4,5,6,#,7,8,#]"));
    Assert.assertArrayEquals(expects, actual);
  }

  @Test
  public void test1() {
    W0117_PopulatingNextRightPointersInEachNodeII_1.Solution solution = new W0117_PopulatingNextRightPointersInEachNodeII_1.Solution();
    final String levelChar = "#";
    Node root = null;
    String[] actual = null;
    String[] expects = null;

    actual = TreeSupport.toStringsByNextPointers(solution.connect(
      TreeSupport.newBinaryTree2(3, 9, 20, null, null, 15, 7)), levelChar);
    expects = ArraySupport.newStringArray(
      StringSupport.appendStringForJSON("[3,#,9,20,#,15,7,#]"));
    Assert.assertArrayEquals(expects, actual);

    actual = TreeSupport.toStringsByNextPointers(solution.connect(
      TreeSupport.newBinaryTree2(1, 2, 3, 4, 5, null, 7)), levelChar);
    expects = ArraySupport.newStringArray(
      StringSupport.appendStringForJSON("[1,#,2,3,#,4,5,7,#]"));
    Assert.assertArrayEquals(expects, actual);

    root = solution.connect(
      TreeSupport.newBinaryTree2(1, 2, 3, 4, 5, null, 6, 7, null, null, null, null, 8));
    actual = TreeSupport.toStringsByNextPointers(root, levelChar);
    expects = ArraySupport.newStringArray(
      StringSupport.appendStringForJSON("[1,#,2,3,#,4,5,6,#,7,8,#]"));
    Assert.assertArrayEquals(expects, actual);
  }

}
