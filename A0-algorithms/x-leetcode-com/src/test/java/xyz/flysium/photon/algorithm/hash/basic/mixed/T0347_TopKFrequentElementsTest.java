package xyz.flysium.photon.algorithm.hash.basic.mixed;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0347_TopKFrequentElementsTest {

  @Test
  public void test() {
    T0347_TopKFrequentElements.Solution solution = new T0347_TopKFrequentElements.Solution();
    int[] actuals = null;

    actuals = solution.topKFrequent(ArraySupport.newArray("[1,1,1,2,2,3]"), 2);
    Assert.assertEquals(true,
      ArraySupport.equalsNotSequentialStringList(
        ArraySupport.asList(ArraySupport.newArray("[1,2]")),
        ArraySupport.asList(actuals)));
  }

}
