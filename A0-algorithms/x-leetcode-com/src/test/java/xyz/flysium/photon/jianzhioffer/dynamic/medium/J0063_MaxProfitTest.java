package xyz.flysium.photon.jianzhioffer.dynamic.medium;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.jianzhioffer.dynamic.medium.J0063_MaxProfit.Solution;

/**
 * TODO description
 *
 * @author zeno
 */
public class J0063_MaxProfitTest {

  @Test
  public void main() {
    Solution solution = new J0063_MaxProfit().new Solution();
    int actual = 0;

    actual = solution.maxProfit(ArraySupport.newArray("[7,1,5,3,6,4]"));
    Assert.assertEquals(5, actual);

    actual = solution.maxProfit(ArraySupport.newArray("[7,6,4,3,1]"));
    Assert.assertEquals(0, actual);

    actual = solution.maxProfit(ArraySupport.newArray("[7,0,4,3,1]"));
    Assert.assertEquals(4, actual);

    actual = solution.maxProfit(ArraySupport.newArray("[2,1,2,1,0,1,2]"));
    Assert.assertEquals(2, actual);

    actual = solution.maxProfit(ArraySupport.newArray("[3,3,5,0,0,3,1,4]"));
    Assert.assertEquals(4, actual);
  }

  @Test
  public void main1() {
    J0063_MaxProfit_1.Solution solution = new J0063_MaxProfit_1().new Solution();
    int actual = 0;

    actual = solution.maxProfit(ArraySupport.newArray("[7,1,5,3,6,4]"));
    Assert.assertEquals(5, actual);

    actual = solution.maxProfit(ArraySupport.newArray("[7,6,4,3,1]"));
    Assert.assertEquals(0, actual);

    actual = solution.maxProfit(ArraySupport.newArray("[7,0,4,3,1]"));
    Assert.assertEquals(4, actual);

    actual = solution.maxProfit(ArraySupport.newArray("[2,1,2,1,0,1,2]"));
    Assert.assertEquals(2, actual);

    actual = solution.maxProfit(ArraySupport.newArray("[3,3,5,0,0,3,1,4]"));
    Assert.assertEquals(4, actual);
  }

}
