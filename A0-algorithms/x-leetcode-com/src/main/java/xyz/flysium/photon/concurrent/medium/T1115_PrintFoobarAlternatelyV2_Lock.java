package xyz.flysium.photon.concurrent.medium;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import xyz.flysium.photon.concurrent.TestSupport;

/**
 * 1115. 交替打印FooBar
 * <p>
 * https://leetcode-cn.com/problems/print-foobar-alternately/
 *
 * @author zeno
 */
public class T1115_PrintFoobarAlternatelyV2_Lock {

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
    private final Lock lock = new ReentrantLock();
    private final Condition fooCondition = lock.newCondition();
    private final Condition barCondition = lock.newCondition();
    private final CountDownLatch latch = new CountDownLatch(1);

    public FooBar(int n) {
      this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
      latch.countDown();
      lock.lock();
      try {
        for (int i = 0; i < n; i++) {
          // printFoo.run() outputs "foo". Do not change or remove this line.
          printFoo.run();
          barCondition.signal();
          fooCondition.await();
        }
        barCondition.signal();
      } finally {
        lock.unlock();
      }
    }

    public void bar(Runnable printBar) throws InterruptedException {
      latch.await();
      lock.lock();
      try {
        for (int i = 0; i < n; i++) {
          // printBar.run() outputs "bar". Do not change or remove this line.
          printBar.run();
          fooCondition.signal();
          barCondition.await();
        }
        fooCondition.signal();
      } finally {
        lock.unlock();
      }
    }
  }

}

