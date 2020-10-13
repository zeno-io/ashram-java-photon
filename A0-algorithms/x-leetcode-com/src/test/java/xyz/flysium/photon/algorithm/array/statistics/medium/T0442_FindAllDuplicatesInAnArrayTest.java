package xyz.flysium.photon.algorithm.array.statistics.medium;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0442_FindAllDuplicatesInAnArrayTest {

  @Test
  public void test() {
    T0442_FindAllDuplicatesInAnArray.Solution solution = new T0442_FindAllDuplicatesInAnArray.Solution();
    Assert.assertArrayEquals(ArraySupport.newArray("[2, 3]"),
      ArraySupport
        .toArray(solution.findDuplicates(ArraySupport.newArray("[4, 3, 2, 7, 8, 2, 3, 1]"))));
    Assert.assertArrayEquals(ArraySupport.newArray("[3, 2]"),
      ArraySupport
        .toArray(solution.findDuplicates(ArraySupport.newArray("[2, 3, 4, 3, 2, 7, 8, 1]"))));
    Assert.assertArrayEquals(ArraySupport.newArray("[1]"),
      ArraySupport.toArray(solution.findDuplicates(ArraySupport.newArray("[1, 1]"))));
  }

}
