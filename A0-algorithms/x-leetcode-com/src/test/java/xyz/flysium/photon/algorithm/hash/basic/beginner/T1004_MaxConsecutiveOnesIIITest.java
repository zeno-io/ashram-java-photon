package xyz.flysium.photon.algorithm.hash.basic.beginner;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.algorithm.array.traverse.medium.T1004_MaxConsecutiveOnesIII;
import xyz.flysium.photon.algorithm.array.traverse.medium.T1004_MaxConsecutiveOnesIII_1;

/**
 * TODO description
 *
 * @author zeno
 */
public class T1004_MaxConsecutiveOnesIIITest {

  @Test
  public void test() {
    T1004_MaxConsecutiveOnesIII.Solution solution = new T1004_MaxConsecutiveOnesIII.Solution();
//    Assert.assertEquals(6,
//      solution.longestOnes(ArraySupport.newArray("[1,1,1,0,0,0,1,1,1,1,0]"), 2));
    Assert.assertEquals(10,
      solution.longestOnes(ArraySupport.newArray("[0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1]"), 3));
  }

  @Test
  public void test1() {
    T1004_MaxConsecutiveOnesIII_1.Solution solution = new T1004_MaxConsecutiveOnesIII_1.Solution();
    Assert.assertEquals(6,
      solution.longestOnes(ArraySupport.newArray("[1,1,1,0,0,0,1,1,1,1,0]"), 2));
    Assert.assertEquals(10,
      solution.longestOnes(ArraySupport.newArray("[0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1]"), 3));
  }


}
