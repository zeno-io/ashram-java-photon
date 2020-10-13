package xyz.flysium.photon.algorithm.array.dimensional.medium;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0289_GameOfLifeTest {

  @Test
  public void test() {
    T0289_GameOfLife.Solution solution = new T0289_GameOfLife.Solution();

    int[][] matrix = null;

    matrix = ArraySupport.newTwoDimensionalArray(
      "[[0,1,0],[0,0,1],[1,1,1],[0,0,0]]");
    solution.gameOfLife(matrix);
    System.out.println(ArraySupport.toString(matrix));
    Assert.assertArrayEquals(
      ArraySupport.newTwoDimensionalArray("[[0,0,0],[1,0,1],[0,1,1],[0,1,0]]"),
      matrix);
  }

}
