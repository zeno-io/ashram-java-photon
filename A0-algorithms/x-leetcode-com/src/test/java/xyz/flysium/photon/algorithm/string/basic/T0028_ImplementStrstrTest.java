package xyz.flysium.photon.algorithm.string.basic;

import org.junit.Assert;
import org.junit.Test;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0028_ImplementStrstrTest {

  @Test
  public void test() {
    U0028_ImplementStrstr.Solution solution = new U0028_ImplementStrstr.Solution();

    int actual = 0;

    actual = solution.strStr("abc", "");
    Assert.assertEquals(0, actual);

    actual = solution.strStr("abc", "a");
    Assert.assertEquals(0, actual);

    actual = solution.strStr("abc", "xy");
    Assert.assertEquals(-1, actual);

    actual = solution.strStr("hello", "ll");
    Assert.assertEquals(2, actual);
  }

  @Test
  public void test1() {
    U0028_ImplementStrstr_1.Solution solution = new U0028_ImplementStrstr_1.Solution();

    int actual = 0;

    actual = solution.strStr("abc", "");
    Assert.assertEquals(0, actual);

    actual = solution.strStr("abc", "a");
    Assert.assertEquals(0, actual);

    actual = solution.strStr("abc", "xy");
    Assert.assertEquals(-1, actual);

    actual = solution.strStr("hello", "ll");
    Assert.assertEquals(2, actual);
  }

}
