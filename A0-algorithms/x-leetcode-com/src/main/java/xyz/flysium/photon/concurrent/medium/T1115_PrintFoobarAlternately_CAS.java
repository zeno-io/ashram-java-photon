package xyz.flysium.photon.concurrent.medium;

import xyz.flysium.photon.concurrent.TestSupport;

/**
 * 1115. 交替打印FooBar
 * <p>
 * https://leetcode-cn.com/problems/print-foobar-alternately/
 *
 * @author zeno
 */
public class T1115_PrintFoobarAlternately_CAS {

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
    private volatile long a1, a2, a3, a4, a5, a6, a7;
    // 1 -> foo, 2 -> bar
    private volatile long flag = 1;
    private volatile long b1, b2, b3, b4, b5, b6, b7;

    public FooBar(int n) {
      this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
      for (int i = 0; i < n; i++) {
        while (flag != 1) {
          //TimeUnit.NANOSECONDS.sleep(1);
          Thread.yield();
        }
        // printFoo.run() outputs "foo". Do not change or remove this line.
        printFoo.run();

        flag = 2;
      }
    }

    public void bar(Runnable printBar) throws InterruptedException {
      for (int i = 0; i < n; i++) {
        while (flag != 2) {
          //TimeUnit.NANOSECONDS.sleep(1);
          Thread.yield();
        }
        // printBar.run() outputs "bar". Do not change or remove this line.
        printBar.run();

        flag = 1;
      }
    }
  }

}

