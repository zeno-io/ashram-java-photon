package xyz.flysium.photon.algorithm.array.move.easy;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0453_MinimumMovesToEqualArrayElementsTest {

  @Test
  public void test() {
    T0453_MinimumMovesToEqualArrayElements.Solution solution = new T0453_MinimumMovesToEqualArrayElements.Solution();
    Assert.assertEquals(0, solution.minMoves(ArraySupport.newArray("[-1]")));
    Assert.assertEquals(2, solution.minMoves(ArraySupport.newArray("[-1, 1]")));
    Assert.assertEquals(7, solution.minMoves(ArraySupport.newArray("[-2, 5]")));
    Assert.assertEquals(1, solution.minMoves(ArraySupport.newArray("[1, 1, 2]")));
    Assert.assertEquals(0, solution.minMoves(ArraySupport.newArray("[1, 1, 1]")));
    Assert.assertEquals(3, solution.minMoves(ArraySupport.newArray("[1, 2, 3]")));
    Assert.assertEquals(6, solution.minMoves(ArraySupport.newArray("[4, 6, 8]")));
  }
}
