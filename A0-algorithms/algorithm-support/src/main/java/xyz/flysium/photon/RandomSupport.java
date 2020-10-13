package xyz.flysium.photon;

import java.util.concurrent.ThreadLocalRandom;

/**
 * TODO description
 *
 * @author zeno
 */
public final class RandomSupport {

  private RandomSupport() {
  }

  private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

  public static int randomValue(int maxValue) {
    return RANDOM.nextInt(maxValue);
  }

  public static int randomValue(int minValue, int maxValue) {
    return (minValue == maxValue) ? minValue : RANDOM.nextInt(minValue, maxValue);
  }

}
