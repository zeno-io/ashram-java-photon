package xyz.flysium.photon.algorithm.array.dimensional.medium;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0498_DiagonalTraverseTest {

  @Test
  public void test() {
    T0498_DiagonalTraverse.Solution solution = new T0498_DiagonalTraverse.Solution();

    int[] actuals = null;

    actuals = solution.findDiagonalOrder(ArraySupport.newTwoDimensionalArray(
      "[[1,2,3,4],[5,6,7,8],[9,10,11,12]]"));
    System.out.println(Arrays.toString(actuals));
    Assert.assertArrayEquals(ArraySupport.newArray("[1,2,5,9,6,3,4,7,10,11,8,12]"), actuals);
  }
}
