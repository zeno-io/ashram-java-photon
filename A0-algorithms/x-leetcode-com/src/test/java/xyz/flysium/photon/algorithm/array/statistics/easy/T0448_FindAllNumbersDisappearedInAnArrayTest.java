package xyz.flysium.photon.algorithm.array.statistics.easy;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0448_FindAllNumbersDisappearedInAnArrayTest {

  @Test
  public void test() {
    T0448_FindAllNumbersDisappearedInAnArray.Solution solution = new T0448_FindAllNumbersDisappearedInAnArray.Solution();
    int[] actuals = ArraySupport
      .toArray(solution.findDisappearedNumbers(ArraySupport.newArray("[4, 3, 2, 7, 8, 2, 3, 1]")));
    Assert.assertArrayEquals(ArraySupport.newArray("[5, 6]"),
      actuals);

    actuals = ArraySupport
      .toArray(solution.findDisappearedNumbers(ArraySupport.newArray("[1, 1]")));
    Assert.assertArrayEquals(ArraySupport.newArray("[2]"), actuals);
    int[] in = new int[50000];
    for (int i = 0; i < in.length; i++) {
      in[i] = i + 1;
    }
    in[1000] = in[888];
    in[1001] = in[666];
    actuals = ArraySupport.toArray(solution.findDisappearedNumbers(in));
    Assert.assertArrayEquals(ArraySupport.newArray("[1001, 1002]"), actuals);
  }

}
