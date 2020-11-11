package xyz.flysium.photon.algorithm.tree.trie.hard;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.algorithm.tree.trie.hard.T0425_WordSquares.Solution;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0425_WordSquaresTest {

  @Test
  public void test() {
    Solution solution = new T0425_WordSquares().new Solution();
    List<List<String>> excepted = null;
    List<List<String>> actuals = null;

    actuals = solution.wordSquares(
      ArraySupport.newStringArray(
        "[\"aa\",\"bb\"]"));
    excepted = ArraySupport.toStringListList(
      "[[\"aa\",\"aa\"],[\"bb\",\"bb\"]]");
    Assert.assertTrue(ArraySupport.equalsNotSequentialStringListList(excepted, actuals));

    actuals = solution.wordSquares(
      ArraySupport.newStringArray(
        "[\"a\"]"));
    excepted = ArraySupport.toStringListList(
      "[[\"a\"]]");
    Assert.assertTrue(ArraySupport.equalsNotSequentialStringListList(excepted, actuals));

    actuals = solution.wordSquares(
      ArraySupport.newStringArray(
        "[\"area\",\"lead\",\"wall\",\"lady\",\"ball\"]"));
    excepted = ArraySupport.toStringListList(
      "[[\"ball\",\"area\",\"lead\",\"lady\"],[\"wall\",\"area\",\"lead\",\"lady\"]]");
    Assert.assertTrue(ArraySupport.equalsNotSequentialStringListList(excepted, actuals));

    actuals = solution.wordSquares(
      ArraySupport.newStringArray(
        "[\"abat\",\"baba\",\"atan\",\"atal\"]\n"));
    excepted = ArraySupport.toStringListList(
      "[[\"baba\",\"abat\",\"baba\",\"atal\"],[\"baba\",\"abat\",\"baba\",\"atan\"]]");
    Assert.assertTrue(ArraySupport.equalsNotSequentialStringListList(excepted, actuals));

  }


}
