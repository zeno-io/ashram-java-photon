package xyz.flysium.photon.concurrent;

import java.util.concurrent.Semaphore;

/**
 * 1114. 按序打印
 * <p>
 * https://leetcode-cn.com/problems/print-in-order
 *
 * @author zeno
 */
public class T1114_PrintInOrderV3_Semaphore {

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

    private final Semaphore semaphore2 = new Semaphore(0);
    private final Semaphore semaphore3 = new Semaphore(0);

    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
      // printFirst.run() outputs "first". Do not change or remove this line.
      printFirst.run();
      semaphore2.release(1);
    }

    public void second(Runnable printSecond) throws InterruptedException {
      semaphore2.acquire();
      // printSecond.run() outputs "second". Do not change or remove this line.
      printSecond.run();
      semaphore3.release(1);
    }

    public void third(Runnable printThird) throws InterruptedException {
      semaphore3.acquire();
      // printThird.run() outputs "third". Do not change or remove this line.
      printThird.run();
    }
  }

}

