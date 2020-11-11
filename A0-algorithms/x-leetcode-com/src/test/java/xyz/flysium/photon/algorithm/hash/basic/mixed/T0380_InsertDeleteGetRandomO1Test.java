package xyz.flysium.photon.algorithm.hash.basic.mixed;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0380_InsertDeleteGetRandomO1Test {

  @Test
  public void test() {
    T0380_InsertDeleteGetRandomO1.RandomizedSet solution = new T0380_InsertDeleteGetRandomO1.RandomizedSet();
    Assert.assertEquals(true, solution.insert(1));
    Assert.assertEquals(false, solution.remove(2));
    Assert.assertEquals(true, solution.insert(2));
    Assert.assertEquals(true, Arrays.asList(1, 2).contains(solution.getRandom()));
    Assert.assertEquals(true, solution.remove(1));
    Assert.assertEquals(false, solution.insert(2));
    Assert.assertEquals(true, Arrays.asList(2).contains(solution.getRandom()));

    solution = new T0380_InsertDeleteGetRandomO1.RandomizedSet();
    Assert.assertEquals(true, solution.insert(0));
    Assert.assertEquals(true, solution.insert(1));
    Assert.assertEquals(true, solution.remove(0));
    Assert.assertEquals(true, solution.insert(2));
    Assert.assertEquals(true, solution.remove(1));
    Assert.assertEquals(true, Arrays.asList(2).contains(solution.getRandom()));

    solution = new T0380_InsertDeleteGetRandomO1.RandomizedSet();
    Assert.assertEquals(false, solution.remove(0));
    Assert.assertEquals(false, solution.remove(0));
    Assert.assertEquals(true, solution.insert(0));
    Assert.assertEquals(true, solution.remove(0));
    Assert.assertEquals(true, solution.insert(0));
  }
}
