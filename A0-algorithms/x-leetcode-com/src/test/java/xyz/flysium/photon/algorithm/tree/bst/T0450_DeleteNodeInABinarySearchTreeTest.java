package xyz.flysium.photon.algorithm.tree.bst;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.TreeSupport;
import xyz.flysium.photon.tree.TreeNode;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0450_DeleteNodeInABinarySearchTreeTest {


  @Test
  public void test() {
    T0450_DeleteNodeInABinarySearchTree.Solution solution = new T0450_DeleteNodeInABinarySearchTree.Solution();
    TreeNode root = null;
    TreeNode result = null;
    String[] actual = null;
    String[] expects = null;

    //          5
    //         / \
    //        3   6
    //       / \   \
    //      2   4   7
    root = TreeSupport.newBinaryTree(5, 3, 6, 2, 4, null, 7);
    result = solution.deleteNode(root, 3);
    expects = TreeSupport.toStrings(root, "#");
    actual = TreeSupport.toStrings(result, "#");
    Assert.assertArrayEquals(expects, actual);

  }

}
