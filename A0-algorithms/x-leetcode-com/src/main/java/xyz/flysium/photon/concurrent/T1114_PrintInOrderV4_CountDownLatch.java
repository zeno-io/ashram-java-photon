package xyz.flysium.photon.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * 1114. 按序打印
 * <p>
 * https://leetcode-cn.com/problems/print-in-order
 *
 * @author zeno
 */
public class T1114_PrintInOrderV4_CountDownLatch {

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

    private final CountDownLatch countDownLatch2 = new CountDownLatch(1);
    private final CountDownLatch countDownLatch3 = new CountDownLatch(1);

    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
      // printFirst.run() outputs "first". Do not change or remove this line.
      printFirst.run();
      countDownLatch2.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
      countDownLatch2.await();
      // printSecond.run() outputs "second". Do not change or remove this line.
      printSecond.run();
      countDownLatch3.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
      countDownLatch3.await();
      // printThird.run() outputs "third". Do not change or remove this line.
      printThird.run();
    }
  }

}

