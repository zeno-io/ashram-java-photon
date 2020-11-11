package xyz.flysium.photon.concurrent.medium;

import xyz.flysium.photon.concurrent.TestSupport;

/**
 * 1115. 交替打印FooBar
 * <p>
 * https://leetcode-cn.com/problems/print-foobar-alternately/
 *
 * @author zeno
 */
public class T1115_PrintFoobarAlternatelyV1_WaitNotify {

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
    private volatile int flag = 1;

    public FooBar(int n) {
      this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
      synchronized (this) {
        for (int i = 0; i < n; i++) {
          while (flag != 1) {
            wait();
          }
          // printFoo.run() outputs "foo". Do not change or remove this line.
          printFoo.run();

          flag = 0;
          notify();
        }
        notify();
      }
    }

    public void bar(Runnable printBar) throws InterruptedException {
      synchronized (this) {
        for (int i = 0; i < n; i++) {
          while (flag != 0) {
            wait();
          }
          // printBar.run() outputs "bar". Do not change or remove this line.
          printBar.run();

          flag = 1;
          notify();
        }
        notify();
      }
    }
  }

}

