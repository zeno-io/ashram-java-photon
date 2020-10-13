package xyz.flysium.photon.algorithm.array.dimensional.easy;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0661_ImageSmootherTest {

  @Test
  public void test() {
    T0661_ImageSmoother.Solution solution = new T0661_ImageSmoother.Solution();
    int[][] actual = null;

    actual = solution
      .imageSmoother(ArraySupport.newTwoDimensionalArray("[[1,1,1],[1,0,1],[1,1,1]]"));
    Assert.assertArrayEquals(ArraySupport.newTwoDimensionalArray("[[0, 0, 0],[0, 0, 0],[0, 0, 0]]"),
      actual);

    actual = solution.imageSmoother(
      ArraySupport.newTwoDimensionalArray("[[2,3,4],[5,6,7],[8,9,10],[11,12,13],[14,15,16]]"));
    System.out.println(ArraySupport.toString(actual));
    Assert.assertArrayEquals(
      ArraySupport.newTwoDimensionalArray("[[4,4,5],[5,6,6],[8,9,9],[11,12,12],[13,13,14]]"),
      actual);
  }

}
