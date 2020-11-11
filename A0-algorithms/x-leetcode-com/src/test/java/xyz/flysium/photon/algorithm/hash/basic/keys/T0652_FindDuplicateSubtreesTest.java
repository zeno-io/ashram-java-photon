package xyz.flysium.photon.algorithm.hash.basic.keys;

import java.util.ArrayList;
import java.util.List;
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
public class T0652_FindDuplicateSubtreesTest {


  @Test
  public void test() {
    T0652_FindDuplicateSubtrees.Solution solution = new T0652_FindDuplicateSubtrees.Solution();
    List<TreeNode> actuals = null;
    List<List<Integer>> excepted = null;

    actuals = solution.findDuplicateSubtrees(
      TreeSupport.newBinaryTree(ArraySupport.newIntegerArray("[2,1,11,11,null,1]")));
    excepted = ArraySupport.toStringListList("[]");
    Assert.assertTrue(check(actuals, excepted));

    actuals = solution.findDuplicateSubtrees(
      TreeSupport.newBinaryTree(ArraySupport.newIntegerArray("[1,2,3,4,null,2,4,null,null,4]")));
    excepted = ArraySupport.toStringListList("[[2,4],[4]]");
    Assert.assertTrue(check(actuals, excepted));
  }

  private boolean check(List<TreeNode> l, List<List<Integer>> excepted) {
    List<List<Integer>> actual = new ArrayList<>();
    for (TreeNode treeNode : l) {
      List<Integer> el = TreeSupport.preorderTraversal(treeNode);
      actual.add(el);
    }
    return ArraySupport.equalsNotSequentialStringListList(excepted, actual);
  }

}
