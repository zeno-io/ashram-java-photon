package xyz.flysium.photon.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1117. H2O 生成
 * <p>
 * https://leetcode-cn.com/problems/building-h2o/
 *
 * @author zeno
 */
public class T1117_BuildingH2OV2_Lock {

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

    private final Lock lock = new ReentrantLock();
    private final Condition hydrogen = lock.newCondition();
    private final Condition oxygen = lock.newCondition();
    // hydrogen first
    private volatile boolean hFlag = true;
    private volatile boolean oFlag = false;
    // count of hydrogen each epoch
    private int h = 0;

    public H2O() {
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
      lock.lock();
      try {
        while (!hFlag) {
          hydrogen.await();
        }
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        h++;
        if (h == 2) {
          h = 0;
          hFlag = false;
          oFlag = true;
          oxygen.signal();
        }
      } finally {
        lock.unlock();
      }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
      lock.lock();
      try {
        while (!oFlag) {
          oxygen.await();
        }
        // releaseOxygen.run() outputs "H". Do not change or remove this line.
        releaseOxygen.run();
        hFlag = true;
        oFlag = false;
        hydrogen.signal();
      } finally {
        lock.unlock();
      }
    }
  }

}
