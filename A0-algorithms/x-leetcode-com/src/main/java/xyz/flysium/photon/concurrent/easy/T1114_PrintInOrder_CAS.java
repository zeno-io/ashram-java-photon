package xyz.flysium.photon.concurrent.easy;

import xyz.flysium.photon.concurrent.TestSupport;

/**
 * 1114. 按序打印
 * <p>
 * https://leetcode-cn.com/problems/print-in-order
 *
 * @author zeno
 */
public class T1114_PrintInOrder_CAS {

  private static final int TIMES = 1000;

  public static void main(String[] args) throws InterruptedException {
    long l = TestSupport.testWithMyConsumer(TIMES, Foo::new, (foo) -> {
      foo.first(() -> System.out.print("first"));
    }, (foo) -> {
      foo.second(() -> System.out.print("second"));
    }, (foo) -> {
      foo.third(() -> System.out.print("third"));
    });
    System.out.println();
    System.out.println(TestSupport.toMillisString(l));
  }

  private static class Foo {

    private volatile long a1, a2, a3, a4, a5, a6, a7;
    // 1 -> first, 2 -> second, 3 -> third
    private volatile long flag = 1;
    private volatile long b1, b2, b3, b4, b5, b6, b7;

    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
      while (flag != 1) {
        //TimeUnit.NANOSECONDS.sleep(1);
        Thread.yield();
      }
      // printFirst.run() outputs "first". Do not change or remove this line.
      printFirst.run();

      flag = 2;
    }

    public void second(Runnable printSecond) throws InterruptedException {
      while (flag != 2) {
        //TimeUnit.NANOSECONDS.sleep(1);
        Thread.yield();
      }
      // printSecond.run() outputs "second". Do not change or remove this line.
      printSecond.run();

      flag = 3;
    }

    public void third(Runnable printThird) throws InterruptedException {
      while (flag != 3) {
        // TimeUnit.NANOSECONDS.sleep(1);
        Thread.yield();
      }
      // printThird.run() outputs "third". Do not change or remove this line.
      printThird.run();
    }
  }

}


