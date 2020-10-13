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
public class T0054_SpiralMatrixTest {

  @Test
  public void test() {
    T0054_SpiralMatrix.Solution solution = new T0054_SpiralMatrix.Solution();

    int[] actuals = null;

    actuals = ArraySupport.toArray(solution.spiralOrder(ArraySupport.newTwoDimensionalArray(
      "[[1,2,3],[4,5,6],[7,8,9]]")));
    System.out.println(Arrays.toString(actuals));
    Assert.assertArrayEquals(ArraySupport.newArray("[1,2,3,6,9,8,7,4,5]"), actuals);


  }

}
