package xyz.flysium.photon.algorithm.tree.binary;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.TreeSupport;
import xyz.flysium.photon.tree.TreeNode;

/**
 * TODO description
 *
 * @author zeno
 */
public class U0101_SymmetricTreeTest {

  @Test
  public void test() {
    U0101_SymmetricTree.Solution solution = new U0101_SymmetricTree.Solution();
    TreeNode root = null;

    Assert.assertTrue(solution.isSymmetric(null));

    root = TreeSupport.newBinaryTree(1, 0);
    Assert.assertFalse(solution.isSymmetric(root));

    root = TreeSupport.newBinaryTree(1, 2, 2, null, 3, null, 3);
    Assert.assertFalse(solution.isSymmetric(root));

    root = TreeSupport.newBinaryTree(1, 2, 2, 3, 4, 4, 3);
    Assert.assertTrue(solution.isSymmetric(root));

    root = TreeSupport.newBinaryTree(2, 3, 3, 4, 5, 5, 4, null, null, 8, 9, null, null, 9, 8);
    Assert.assertFalse(solution.isSymmetric(root));
  }


  @Test
  public void test1() {
    U0101_SymmetricTree_1.Solution solution = new U0101_SymmetricTree_1.Solution();
    TreeNode root = null;

    Assert.assertTrue(solution.isSymmetric(null));

    root = TreeSupport.newBinaryTree(1, 0);
    Assert.assertFalse(solution.isSymmetric(root));

    root = TreeSupport.newBinaryTree(1, 2, 2, null, 3, null, 3);
    Assert.assertFalse(solution.isSymmetric(root));

    root = TreeSupport.newBinaryTree(1, 2, 2, 3, 4, 4, 3);
    Assert.assertTrue(solution.isSymmetric(root));

    root = TreeSupport.newBinaryTree(2, 3, 3, 4, 5, 5, 4, null, null, 8, 9, null, null, 9, 8);
    Assert.assertFalse(solution.isSymmetric(root));
  }

  @Test
  public void test2() {
    U0101_SymmetricTree_2.Solution solution = new U0101_SymmetricTree_2.Solution();
    TreeNode root = null;

    Assert.assertTrue(solution.isSymmetric(null));

    root = TreeSupport.newBinaryTree(1, 0);
    Assert.assertFalse(solution.isSymmetric(root));

    root = TreeSupport.newBinaryTree(1, 2, 2, null, 3, null, 3);
    Assert.assertFalse(solution.isSymmetric(root));

    root = TreeSupport.newBinaryTree(1, 2, 2, 3, 4, 4, 3);
    Assert.assertTrue(solution.isSymmetric(root));

    root = TreeSupport.newBinaryTree(2, 3, 3, 4, 5, 5, 4, null, null, 8, 9, null, null, 9, 8);
    Assert.assertFalse(solution.isSymmetric(root));
  }

}
