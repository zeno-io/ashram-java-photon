package xyz.flysium.photon.jianzhioffer.datastructure.easy;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.jianzhioffer.datastructure.medium.J0020_IsNumber;
import xyz.flysium.photon.jianzhioffer.datastructure.medium.J0020_IsNumber_1;

/**
 * TODO description
 *
 * @author zeno
 */
public class J0020_IsNumberTest {

  @Test
  public void test() {
    J0020_IsNumber.Solution solution = new J0020_IsNumber.Solution();
    Assert.assertEquals(true, solution.isNumber("+100"));
    Assert.assertEquals(true, solution.isNumber("+100.8e-8"));
    Assert.assertEquals(true, solution.isNumber("5e2"));
    Assert.assertEquals(true, solution.isNumber("-123"));
    Assert.assertEquals(true, solution.isNumber("3.1415"));
    Assert.assertEquals(true, solution.isNumber("-1E-16"));
    Assert.assertEquals(true, solution.isNumber("0123"));
    Assert.assertEquals(false, solution.isNumber("e+5.4"));
    Assert.assertEquals(false, solution.isNumber("-."));
    Assert.assertEquals(false, solution.isNumber(".e1"));
    Assert.assertEquals(false, solution.isNumber("e56"));
    Assert.assertEquals(false, solution.isNumber("E-8"));
    Assert.assertEquals(false, solution.isNumber("+E1-8"));
    Assert.assertEquals(true, solution.isNumber("-80E-8"));
    Assert.assertEquals(false, solution.isNumber(""));
    Assert.assertEquals(false, solution.isNumber("456+"));
    Assert.assertEquals(false, solution.isNumber("+"));
    Assert.assertEquals(false, solution.isNumber("."));
    Assert.assertEquals(false, solution.isNumber("e"));
    Assert.assertEquals(false, solution.isNumber(" "));
    Assert.assertEquals(false, solution.isNumber("     "));
    Assert.assertEquals(false, solution.isNumber(" 1 2 3 "));
    Assert.assertEquals(false, solution.isNumber("1  .2  3"));
    Assert.assertEquals(false, solution.isNumber("12 e3"));
    Assert.assertEquals(false, solution.isNumber("12e+5.4"));
    Assert.assertEquals(false, solution.isNumber("12e-5."));
  }

  @Test
  public void test1() {
    J0020_IsNumber_1.Solution solution = new J0020_IsNumber_1.Solution();
    Assert.assertEquals(true, solution.isNumber("+100"));
    Assert.assertEquals(true, solution.isNumber("+100.8e-8"));
    Assert.assertEquals(true, solution.isNumber("5e2"));
    Assert.assertEquals(true, solution.isNumber("-123"));
    Assert.assertEquals(true, solution.isNumber("3.1415"));
    Assert.assertEquals(true, solution.isNumber("-1E-16"));
    Assert.assertEquals(true, solution.isNumber("0123"));
    Assert.assertEquals(false, solution.isNumber("e+5.4"));
    Assert.assertEquals(false, solution.isNumber("-."));
    Assert.assertEquals(false, solution.isNumber(".e1"));
    Assert.assertEquals(false, solution.isNumber("e56"));
    Assert.assertEquals(false, solution.isNumber("E-8"));
    Assert.assertEquals(false, solution.isNumber("+E1-8"));
    Assert.assertEquals(true, solution.isNumber("-80E-8"));
    Assert.assertEquals(false, solution.isNumber(""));
    Assert.assertEquals(false, solution.isNumber("456+"));
    Assert.assertEquals(false, solution.isNumber("+"));
    Assert.assertEquals(false, solution.isNumber("."));
    Assert.assertEquals(false, solution.isNumber("e"));
    Assert.assertEquals(false, solution.isNumber(" "));
    Assert.assertEquals(false, solution.isNumber("     "));
    Assert.assertEquals(false, solution.isNumber(" 1 2 3 "));
    Assert.assertEquals(false, solution.isNumber("1  .2  3"));
    Assert.assertEquals(false, solution.isNumber("12 e3"));
    Assert.assertEquals(false, solution.isNumber("12e+5.4"));
    Assert.assertEquals(false, solution.isNumber("12e-5."));
  }

}
