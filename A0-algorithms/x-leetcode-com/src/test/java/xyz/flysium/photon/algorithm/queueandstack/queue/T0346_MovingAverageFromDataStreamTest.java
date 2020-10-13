package xyz.flysium.photon.algorithm.queueandstack.queue;

import org.junit.Assert;
import org.junit.Test;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0346_MovingAverageFromDataStreamTest {

  @Test
  public void test() {
    T0346_MovingAverageFromDataStream.MovingAverage m
      = new T0346_MovingAverageFromDataStream.MovingAverage(3);
    Assert.assertEquals(1.0, m.next(1), 0);
    Assert.assertEquals((1 + 10) / 2.0, m.next(10), 0);
    Assert.assertEquals((1 + 10 + 3) / 3.0, m.next(3), 0);
    Assert.assertEquals((10 + 3 + 5) / 3.0, m.next(5), 0);
  }

  @Test
  public void test1() {
    T0346_MovingAverageFromDataStream_1.MovingAverage m
      = new T0346_MovingAverageFromDataStream_1.MovingAverage(3);
    Assert.assertEquals(1.0, m.next(1), 0);
    Assert.assertEquals((1 + 10) / 2.0, m.next(10), 0);
    Assert.assertEquals((1 + 10 + 3) / 3.0, m.next(3), 0);
    Assert.assertEquals((10 + 3 + 5) / 3.0, m.next(5), 0);
  }


  @Test
  public void test2() {
    T0346_MovingAverageFromDataStream_2.MovingAverage m
      = new T0346_MovingAverageFromDataStream_2.MovingAverage(3);
    Assert.assertEquals(1.0, m.next(1), 0);
    Assert.assertEquals((1 + 10) / 2.0, m.next(10), 0);
    Assert.assertEquals((1 + 10 + 3) / 3.0, m.next(3), 0);
    Assert.assertEquals((10 + 3 + 5) / 3.0, m.next(5), 0);
  }

}
