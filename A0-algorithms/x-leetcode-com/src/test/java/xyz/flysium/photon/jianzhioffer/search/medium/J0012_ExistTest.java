package xyz.flysium.photon.jianzhioffer.search.medium;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.jianzhioffer.search.medium.J0012_Exist.Solution;

/**
 * TODO description
 *
 * @author zeno
 */
public class J0012_ExistTest {

  @Test
  public void main() {
    Solution solution = new J0012_Exist().new Solution();
    boolean actual = false;

    actual = solution.exist(ArraySupport.toTwoDimensionalCharArray(
      "[[\"A\",\"B\"]]"), "BA");
    Assert.assertEquals(true, actual);

    actual = solution.exist(ArraySupport.toTwoDimensionalCharArray(
      "[[\"A\",\"A\"]]"), "AAA");
    Assert.assertEquals(false, actual);

    actual = solution.exist(ArraySupport.toTwoDimensionalCharArray(
      "[[\"A\",\"B\"],[\"C\",\"D\"]]"), "ABDC");
    Assert.assertEquals(true, actual);

    actual = solution.exist(ArraySupport.toTwoDimensionalCharArray(
      "[[\"A\",\"B\",\"C\",\"E\"],[\"S\",\"F\",\"C\",\"S\"],[\"A\",\"D\",\"E\",\"E\"]]"), "ABCCED");
    Assert.assertEquals(true, actual);

    actual = solution.exist(ArraySupport.toTwoDimensionalCharArray(
      "[[\"A\",\"B\",\"C\",\"E\"],[\"S\",\"F\",\"E\",\"S\"],[\"A\",\"D\",\"E\",\"E\"]]"),
      "ABCESEEEFS");
    Assert.assertEquals(true, actual);
  }

}
