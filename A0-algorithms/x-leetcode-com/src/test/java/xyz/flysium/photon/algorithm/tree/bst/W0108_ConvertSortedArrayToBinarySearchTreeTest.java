package xyz.flysium.photon.algorithm.tree.bst;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.TreeSupport;
import xyz.flysium.photon.tree.TreeNode;

/**
 * TODO description
 *
 * @author zeno
 */
public class W0108_ConvertSortedArrayToBinarySearchTreeTest {

  @Test
  public void test() {
    W0108_ConvertSortedArrayToBinarySearchTree.Solution solution = new W0108_ConvertSortedArrayToBinarySearchTree.Solution();
    TreeNode root = null;
    String[] actual = null;
    String[] expects = null;

    root = solution.sortedArrayToBST(ArraySupport.newArray("[-10,-3,0,5,9]"));
    actual = TreeSupport.toStrings(root, "#");
    expects = TreeSupport.toStrings(TreeSupport.newBinaryTree(0, -3, 9, -10, null, 5), "#");
    Assert.assertArrayEquals(expects, actual);
  }

}
