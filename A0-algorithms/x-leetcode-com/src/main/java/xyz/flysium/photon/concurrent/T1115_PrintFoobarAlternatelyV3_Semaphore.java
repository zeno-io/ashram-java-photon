package xyz.flysium.photon.concurrent;

import java.util.concurrent.Semaphore;

/**
 * 1115. 交替打印FooBar
 * <p>
 * https://leetcode-cn.com/problems/print-foobar-alternately/
 *
 * @author zeno
 */
public class T1115_PrintFoobarAlternatelyV3_Semaphore {

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
    // foo first
    private final Semaphore foo = new Semaphore(1);
    private final Semaphore bar = new Semaphore(0);

    public FooBar(int n) {
      this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
      for (int i = 0; i < n; i++) {
        foo.acquire();
        // printFoo.run() outputs "foo". Do not change or remove this line.
        printFoo.run();
        bar.release(1);
      }
    }

    public void bar(Runnable printBar) throws InterruptedException {
      for (int i = 0; i < n; i++) {
        bar.acquire();
        // printBar.run() outputs "bar". Do not change or remove this line.
        printBar.run();
        foo.release(1);
      }
    }
  }

}

