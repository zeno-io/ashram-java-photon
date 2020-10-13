package xyz.flysium.photon.algorithm.hash.basic;

import org.junit.Assert;
import org.junit.Test;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0359_LoggerRateLimiterTest {

  @Test
  public void test() {
    T0359_LoggerRateLimiter.Logger logger = new T0359_LoggerRateLimiter.Logger();

    Assert.assertEquals(true, logger.shouldPrintMessage(1, "foo"));
    Assert.assertEquals(true, logger.shouldPrintMessage(1, "bar"));
    Assert.assertEquals(false, logger.shouldPrintMessage(3, "foo"));
    Assert.assertEquals(false, logger.shouldPrintMessage(8, "bar"));
    Assert.assertEquals(false, logger.shouldPrintMessage(10, "foo"));
    Assert.assertEquals(true, logger.shouldPrintMessage(11, "foo"));
    Assert.assertEquals(false, logger.shouldPrintMessage(11, "foo"));
    Assert.assertEquals(true, logger.shouldPrintMessage(11, "bar"));
  }

}
