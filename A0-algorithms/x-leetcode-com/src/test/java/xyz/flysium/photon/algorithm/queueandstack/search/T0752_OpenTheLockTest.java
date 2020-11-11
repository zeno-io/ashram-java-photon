package xyz.flysium.photon.algorithm.queueandstack.search;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.algorithm.queueandstack.search.basic.T0752_OpenTheLock;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0752_OpenTheLockTest {

  @Test
  public void test() {
    T0752_OpenTheLock.Solution solution = new T0752_OpenTheLock.Solution();

    // 可能的移动序列为 "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202"。
    // 注意 "0000" -> "0001" -> "0002" -> "0102" -> "0202" 这样的序列是不能解锁的，
    // 因为当拨动到 "0102" 时这个锁就会被锁定。
    Assert.assertEquals(6, solution.openLock(ArraySupport.newStringArray(
      "[\"0201\",\"0101\",\"0102\",\"1212\",\"2002\"]"), "0202"));

    // 把最后一位反向旋转一次即可 "0000" -> "0009"。
    Assert.assertEquals(1, solution.openLock(ArraySupport.newStringArray(
      "[\"8888\"]"), "0009"));

    // 无法旋转到目标数字且不被锁定。
    Assert.assertEquals(-1, solution.openLock(ArraySupport.newStringArray(
      "[\"8887\",\"8889\",\"8878\",\"8898\",\"8788\",\"8988\",\"7888\",\"9888\"]"), "8888"));

  }

}
