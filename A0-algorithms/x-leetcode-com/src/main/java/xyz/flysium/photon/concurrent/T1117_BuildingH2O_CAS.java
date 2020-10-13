package xyz.flysium.photon.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1117. H2O 生成
 * <p>
 * https://leetcode-cn.com/problems/building-h2o/
 *
 * @author zeno
 */
public class T1117_BuildingH2O_CAS {

  // private static final String IN = "OOHHOHHHH";
  // private static final int LEN = IN.length();
  // 3 的倍数
  private static final int LEN = 999;

  public static void main(String[] args) throws InterruptedException {
    long l = TestSupport.testWithMyConsumerOneTime(false, H2O::new, (inst) -> {
      int i = 0;
      while (i < (2 * LEN / 3)) {
        inst.hydrogen(() -> System.out.print("H"));
        i++;
      }
    }, (inst) -> {
      int i = 0;
      while (i < (LEN / 3)) {
        inst.oxygen(() -> System.out.print("O"));
        i++;
      }
    });
    System.out.println();
    System.out.println(TestSupport.toMillisString(l));
  }

  private static class H2O {

    // hydrogen first
    private volatile boolean hFlag = true;
    private volatile boolean oFlag = false;
    // count of hydrogen each epoch
    private final AtomicInteger h = new AtomicInteger();

    public H2O() {
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
      while (!hFlag) {
        // TimeUnit.NANOSECONDS.sleep(1);
        Thread.yield();
      }
      // releaseHydrogen.run() outputs "H". Do not change or remove this line.
      releaseHydrogen.run();
      h.incrementAndGet();
      if (h.get() == 2) {
        h.set(0);
        hFlag = false;
        oFlag = true;
      }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
      while (!oFlag) {
        // TimeUnit.NANOSECONDS.sleep(1);
        Thread.yield();
      }
      // releaseOxygen.run() outputs "H". Do not change or remove this line.
      releaseOxygen.run();
      hFlag = true;
      oFlag = false;
    }
  }

}
