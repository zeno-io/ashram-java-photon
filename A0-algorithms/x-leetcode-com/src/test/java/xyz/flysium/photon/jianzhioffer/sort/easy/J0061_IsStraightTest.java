package xyz.flysium.photon.jianzhioffer.sort.easy;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.jianzhioffer.sort.easy.J0061_IsStraight.Solution;

/**
 * TODO description
 *
 * @author zeno
 */
public class J0061_IsStraightTest {

  @Test
  public void main() {
    Solution solution = new J0061_IsStraight().new Solution();
    // true
    Assert.assertEquals(true, solution.isStraight(ArraySupport.newArray("[1, 2, 3, 4, 5]")));
    // true
    Assert.assertEquals(true, solution.isStraight(ArraySupport.newArray("[0, 0, 1, 2, 5]")));
    // false
    Assert.assertEquals(false, solution.isStraight(ArraySupport.newArray("[0, 0, 1, 2, 6]")));
    // true
    Assert.assertEquals(true, solution.isStraight(ArraySupport.newArray("[0, 5, 6, 0, 7]")));
    // false
    Assert.assertEquals(false, solution.isStraight(ArraySupport.newArray("[0, 1, 13, 3, 5]")));
    // true
    Assert.assertEquals(true, solution.isStraight(ArraySupport.newArray("[11, 0, 9, 0, 0]")));
    // false
    Assert.assertEquals(false, solution.isStraight(ArraySupport.newArray("[0, 12, 11, 11, 0]")));
  }
}
