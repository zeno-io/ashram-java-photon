package xyz.flysium.photon.algorithm.queueandstack.search;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.algorithm.queueandstack.search.basic.T0200_NumberOfIslands;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0200_NumberOfIslandsTest {

  @Test
  public void test() {
    T0200_NumberOfIslands.Solution solution = new T0200_NumberOfIslands.Solution();
    char[][] grid = null;

    grid = ArraySupport.toTwoDimensionalCharArray(
      "[[\"1\",\"1\",\"1\",\"1\",\"0\"],"
        + "[\"1\",\"1\",\"0\",\"1\",\"0\"],"
        + "[\"1\",\"1\",\"0\",\"0\",\"0\"],"
        + "[\"0\",\"0\",\"0\",\"0\",\"0\"]]");
    Assert.assertEquals(1, solution.numIslands(grid));

    grid = ArraySupport.toTwoDimensionalCharArray(
      "[[\"1\",\"1\",\"0\",\"0\",\"0\"],"
        + "[\"1\",\"1\",\"0\",\"0\",\"0\"],"
        + "[\"0\",\"0\",\"1\",\"0\",\"0\"],"
        + "[\"0\",\"0\",\"0\",\"1\",\"1\"]]");
    Assert.assertEquals(3, solution.numIslands(grid));

    grid = ArraySupport.toTwoDimensionalCharArray(
      "[[\"1\",\"1\",\"1\"],[\"0\",\"1\",\"0\"],[\"1\",\"1\",\"1\"]]");
    Assert.assertEquals(1, solution.numIslands(grid));

    grid = ArraySupport.toTwoDimensionalCharArray(
      "[[\"1\",\"0\",\"1\",\"1\",\"1\"],[\"1\",\"0\",\"1\",\"0\",\"1\"],[\"1\",\"1\",\"1\",\"0\",\"1\"]]");
    Assert.assertEquals(1, solution.numIslands(grid));

    grid = ArraySupport.toTwoDimensionalCharArray(
      "[[\"1\",\"0\",\"1\",\"1\",\"0\",\"1\",\"1\"]]");
    Assert.assertEquals(3, solution.numIslands(grid));
  }

}
