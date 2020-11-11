package xyz.flysium.photon.jianzhioffer.search.medium;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.TreeSupport;
import xyz.flysium.photon.jianzhioffer.search.medium.J0026_IsSubStructure.Solution;

/**
 * TODO description
 *
 * @author zeno
 */
public class J0026_IsSubStructureTest {

  @Test
  public void main() {
    Solution solution = new J0026_IsSubStructure().new Solution();
    boolean actual = false;

    actual = solution
      .isSubStructure(TreeSupport.newBinaryTree(ArraySupport.newIntegerArray("[1,2,3]")),
        TreeSupport.newBinaryTree(ArraySupport.newIntegerArray("[3]")));
    Assert.assertEquals(true, actual);

    actual = solution
      .isSubStructure(TreeSupport.newBinaryTree(ArraySupport.newIntegerArray("[1,2,3]")),
        TreeSupport.newBinaryTree(ArraySupport.newIntegerArray("[3,1]")));
    Assert.assertEquals(false, actual);

    actual = solution
      .isSubStructure(TreeSupport.newBinaryTree(ArraySupport.newIntegerArray("[3,4,5,1,2]")),
        TreeSupport.newBinaryTree(ArraySupport.newIntegerArray("[4,1]")));
    Assert.assertEquals(true, actual);

    actual = solution
      .isSubStructure(
        TreeSupport.newBinaryTree(ArraySupport.newIntegerArray("[4,2,3,4,5,6,7,8,9]")),
        TreeSupport.newBinaryTree(ArraySupport.newIntegerArray("[4,8,9]")));
    Assert.assertEquals(true, actual);

    actual = solution
      .isSubStructure(
        TreeSupport.newBinaryTree(ArraySupport.newIntegerArray("[1,0,1,-4,-3]")),
        TreeSupport.newBinaryTree(ArraySupport.newIntegerArray("[1,-4]")));
    Assert.assertEquals(false, actual);
  }
}

