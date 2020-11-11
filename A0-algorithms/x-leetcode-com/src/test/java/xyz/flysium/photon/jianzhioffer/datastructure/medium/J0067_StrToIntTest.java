package xyz.flysium.photon.jianzhioffer.datastructure.medium;

import org.junit.Assert;
import org.junit.Test;

/**
 * TODO description
 *
 * @author zeno
 */
public class J0067_StrToIntTest {

  @Test
  public void test() {
    J0067_StrToInt.Solution solution = new J0067_StrToInt.Solution();
    Assert.assertEquals(6666, solution.strToInt(" 6666-1 "));
    Assert.assertEquals(-5, solution.strToInt(" -5- "));
    Assert.assertEquals(2, solution.strToInt("  2- "));
    Assert.assertEquals(0, solution.strToInt("  +-2 "));
    Assert.assertEquals(0, solution.strToInt("  - 4 "));
    Assert.assertEquals(2147483647, solution.strToInt("  9223372036854775808 "));
    Assert.assertEquals(-2147483648, solution.strToInt("  -2147483648 "));
    Assert.assertEquals(-2147483648, solution.strToInt("  -2147483649 "));
    Assert.assertEquals(2147483647, solution.strToInt("  2147483647 "));
    Assert.assertEquals(2147483647, solution.strToInt("  +2147483648 "));

    Assert.assertEquals(77767676, solution.strToInt("  +77767676 wwww "));
  }

}
