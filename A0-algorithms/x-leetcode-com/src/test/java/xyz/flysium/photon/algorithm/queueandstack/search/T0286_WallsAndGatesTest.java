package xyz.flysium.photon.algorithm.queueandstack.search;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0286_WallsAndGatesTest {

  @Test
  public void test() {
    T0286_WallsAndGates.Solution solution = new T0286_WallsAndGates.Solution();
    int[][] excepted = null;
    int[][] actual = null;

    excepted = ArraySupport.newTwoDimensionalArray(
      "[[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]");
    solution.wallsAndGates(excepted);
    actual = ArraySupport.newTwoDimensionalArray("[[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]");
    Assert.assertArrayEquals(excepted, actual);
  }

  @Test
  public void test1() {
    T0286_WallsAndGates_1.Solution solution = new T0286_WallsAndGates_1.Solution();
    int[][] excepted = null;
    int[][] actual = null;

    excepted = ArraySupport.newTwoDimensionalArray(
      "[[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]");
    solution.wallsAndGates(excepted);
    actual = ArraySupport.newTwoDimensionalArray("[[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]");
    Assert.assertArrayEquals(excepted, actual);
  }

  @Test
  public void test2() {
    T0286_WallsAndGates_2.Solution solution = new T0286_WallsAndGates_2.Solution();
    int[][] excepted = null;
    int[][] actual = null;

    excepted = ArraySupport.newTwoDimensionalArray(
      "[[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]");
    solution.wallsAndGates(excepted);
    actual = ArraySupport.newTwoDimensionalArray("[[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]");
    Assert.assertArrayEquals(excepted, actual);
  }

}
