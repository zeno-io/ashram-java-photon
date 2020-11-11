package xyz.flysium.photon.algorithm.hash.basic;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.algorithm.hash.basic.beginner.T0202_HappyNumber;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0202_HappyNumberTest {

  @Test
  public void test() {
    T0202_HappyNumber.Solution solution = new T0202_HappyNumber.Solution();

    Assert.assertEquals(true, solution.isHappy(1));
    Assert.assertEquals(false, solution.isHappy(2));
    Assert.assertEquals(false, solution.isHappy(3));
    Assert.assertEquals(false, solution.isHappy(4));
    Assert.assertEquals(false, solution.isHappy(5));
    Assert.assertEquals(false, solution.isHappy(6));
    Assert.assertEquals(true, solution.isHappy(7));
    Assert.assertEquals(false, solution.isHappy(8));
    Assert.assertEquals(false, solution.isHappy(9));
    Assert.assertEquals(true, solution.isHappy(10));
    Assert.assertEquals(false, solution.isHappy(11));
    Assert.assertEquals(false, solution.isHappy(12));
    Assert.assertEquals(true, solution.isHappy(13));
    Assert.assertEquals(false, solution.isHappy(14));
    Assert.assertEquals(false, solution.isHappy(15));
    Assert.assertEquals(false, solution.isHappy(16));
    Assert.assertEquals(false, solution.isHappy(17));
    Assert.assertEquals(false, solution.isHappy(18));
    Assert.assertEquals(true, solution.isHappy(19));
    Assert.assertEquals(false, solution.isHappy(20));
  }
}
