package xyz.flysium.photon.concurrent.medium;

import xyz.flysium.photon.concurrent.TestSupport;

/**
 * 1117. H2O 生成
 * <p>
 * https://leetcode-cn.com/problems/building-h2o/
 *
 * @author zeno
 */
public class T1117_BuildingH2OV1_WaitNotify {

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
    private boolean hFlag = true;
    private boolean oFlag = false;
    // count of hydrogen each epoch
    private int h = 0;

    public H2O() {
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
      synchronized (this) {
        while (!hFlag) {
          wait();
        }
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        h++;
        if (h == 2) {
          h = 0;
          hFlag = false;
          oFlag = true;
          notifyAll();
        }
      }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
      synchronized (this) {
        while (!oFlag) {
          wait();
        }
        // releaseOxygen.run() outputs "H". Do not change or remove this line.
        releaseOxygen.run();
        hFlag = true;
        oFlag = false;
        notifyAll();
      }
    }
  }

}
