package xyz.flysium.photon.algorithm.string.easy;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T1002_FindCommonCharactersTest {

  @Test
  public void test() {
    T1002_FindCommonCharacters.Solution solution = new T1002_FindCommonCharacters.Solution();

//    Assert.assertTrue(ArraySupport.equalsNotSequentialStringList(new ArrayList(
//        Arrays.asList(ArraySupport.newStringArray("[]"))),
//      solution.commonChars(ArraySupport.newStringArray(
//        "[\"acabcddd\",\"bcbdbcbd\",\"baddbadb\",\"cbdddcac\",\"aacbcccd\",\"ccccddda\",\"cababaab\",\"addcaccd\"]"))));
//
//    Assert.assertTrue(ArraySupport.equalsNotSequentialStringList(new ArrayList(
//        Arrays.asList(ArraySupport.newStringArray("[\"e\",\"l\",\"l\"]"))),
//      solution.commonChars(ArraySupport.newStringArray(
//        "[\"bella\",\"label\",\"roller\"]"))));

    Assert.assertTrue(ArraySupport.equalsNotSequentialStringList(new ArrayList(
        Arrays.asList(ArraySupport.newStringArray("[\"c\",\"o\"]"))),
      solution.commonChars(ArraySupport.newStringArray(
        "[\"cool\",\"lock\",\"cook\"]"))));
  }

}
