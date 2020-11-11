package xyz.flysium.photon.concurrent.medium;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import xyz.flysium.photon.concurrent.TestSupport;

/**
 * 1115. 交替打印FooBar
 * <p>
 * https://leetcode-cn.com/problems/print-foobar-alternately/
 *
 * @author zeno
 */
public class T1115_PrintFoobarAlternatelyV4_CyclicBarrier {

  public static final int TIMES = 1000;

  public static void main(String[] args) throws InterruptedException {
    long l = TestSupport.testWithMyConsumerOneTime(false, () -> new FooBar(TIMES), (fooBar) -> {
      fooBar.foo(() -> System.out.print("foo"));
    }, (fooBar) -> {
      fooBar.bar(() -> System.out.print("bar"));
    });
    System.out.println();
    System.out.println(TestSupport.toMillisString(l));
  }

  private static class FooBar {

    private final int n;
    private final CyclicBarrier barrier = new CyclicBarrier(2);

    public FooBar(int n) {
      this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
      for (int i = 1; i <= 2 * n; i++) {
        // printFoo.run() outputs "foo". Do not change or remove this line.
        // 奇数
        if ((i & (~i + 1)) == 1) {
          printFoo.run();
        }
        try {
          barrier.await();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
      }
    }

    public void bar(Runnable printBar) throws InterruptedException {
      for (int i = 1; i <= 2 * n; i++) {
        // printBar.run() outputs "bar". Do not change or remove this line.
        // 偶数
        if ((i & (~i + 1)) != 1) {
          printBar.run();
        }
        try {
          barrier.await();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
      }
    }
  }

}

