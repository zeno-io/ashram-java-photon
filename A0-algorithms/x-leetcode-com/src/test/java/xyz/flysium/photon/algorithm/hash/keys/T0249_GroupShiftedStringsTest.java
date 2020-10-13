package xyz.flysium.photon.algorithm.hash.keys;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0249_GroupShiftedStringsTest {

  @Test
  public void test() {
    T0249_GroupShiftedStrings.Solution solution = new T0249_GroupShiftedStrings.Solution();
    List<List<String>> actuals = null;
    List<List<String>> excepted = null;

    actuals = solution.groupStrings(
      ArraySupport.newStringArray("[\"abc\",\"bcd\",\"acef\",\"xyz\",\"az\",\"ba\",\"a\",\"z\"]"));
    excepted = ArraySupport
      .toStringListList("[[\"acef\"],[\"a\",\"z\"],[\"abc\",\"bcd\",\"xyz\"],[\"az\",\"ba\"]]");
    Assert.assertTrue(getMessage(actuals, excepted),
      ArraySupport.equalsNotSequentialStringListList(excepted, actuals));

    actuals = solution.groupStrings(
      ArraySupport
        .newStringArray("[\"abcdedfghijklmnopqrstuvwxyz\",\"xyzabcdedfghijklmnopqrstuvw\"]"));
    excepted = ArraySupport
      .toStringListList("[[\"abcdedfghijklmnopqrstuvwxyz\"],[\"xyzabcdedfghijklmnopqrstuvw\"]]");
    Assert.assertTrue(getMessage(actuals, excepted),
      ArraySupport.equalsNotSequentialStringListList(excepted, actuals));

    actuals = solution.groupStrings(
      ArraySupport
        .newStringArray(
          "[\"fpbnsbrkbcyzdmmmoisaa\",\"cpjtwqcdwbldwwrryuclcngw\",\"a\","
            + "\"fnuqwejouqzrif\",\"js\",\"qcpr\",\"zghmdiaqmfelr\",\"iedda\",\"l\","
            + "\"dgwlvcyubde\",\"lpt\",\"qzq\",\"zkddvitlk\",\"xbogegswmad\",\"mkndeyrh\","
            + "\"llofdjckor\",\"lebzshcb\",\"firomjjlidqpsdeqyn\",\"dclpiqbypjpfafukqmjnjg\","
            + "\"lbpabjpcmkyivbtgdwhzlxa\",\"wmalmuanxvjtgmerohskwil\",\"yxgkdlwtkekavapflheieb\","
            + "\"oraxvssurmzybmnzhw\",\"ohecvkfe\",\"kknecibjnq\",\"wuxnoibr\",\"gkxpnpbfvjm\",\"lwpphufxw\","
            + "\"sbs\",\"txb\",\"ilbqahdzgij\",\"i\",\"zvuur\",\"yfglchzpledkq\",\"eqdf\",\"nw\","
            + "\"aiplrzejplumda\",\"d\",\"huoybvhibgqibbwwdzhqhslb\",\"rbnzendwnoklpyyyauemm\"]"));
    excepted = ArraySupport
      .toStringListList(
        "[[\"a\",\"d\",\"i\",\"l\"],[\"eqdf\",\"qcpr\"],[\"lpt\",\"txb\"],[\"yfglchzpledkq\",\"zghmdiaqmfelr\"],[\"kknecibjnq\",\"llofdjckor\"],[\"cpjtwqcdwbldwwrryuclcngw\",\"huoybvhibgqibbwwdzhqhslb\"],[\"lbpabjpcmkyivbtgdwhzlxa\",\"wmalmuanxvjtgmerohskwil\"],[\"iedda\",\"zvuur\"],[\"js\",\"nw\"],[\"lebzshcb\",\"ohecvkfe\"],[\"dgwlvcyubde\",\"ilbqahdzgij\"],[\"lwpphufxw\",\"zkddvitlk\"],[\"qzq\",\"sbs\"],[\"dclpiqbypjpfafukqmjnjg\",\"yxgkdlwtkekavapflheieb\"],[\"mkndeyrh\",\"wuxnoibr\"],[\"firomjjlidqpsdeqyn\",\"oraxvssurmzybmnzhw\"],[\"gkxpnpbfvjm\",\"xbogegswmad\"],[\"fpbnsbrkbcyzdmmmoisaa\",\"rbnzendwnoklpyyyauemm\"],[\"aiplrzejplumda\",\"fnuqwejouqzrif\"]]");
    Assert.assertTrue(getMessage(actuals, excepted),
      ArraySupport.equalsNotSequentialStringListList(excepted, actuals));
  }

  private String getMessage(List<List<String>> actuals, List<List<String>> excepted) {
    return "\nExpected :" + Arrays.toString(ArraySupport.toSortListArray(excepted))
      + "\nActual   :"
      + Arrays.toString(ArraySupport.toSortListArray(actuals));
  }

}
