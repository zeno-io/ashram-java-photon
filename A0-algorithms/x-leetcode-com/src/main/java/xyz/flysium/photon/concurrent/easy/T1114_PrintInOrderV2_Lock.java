package xyz.flysium.photon.concurrent.easy;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import xyz.flysium.photon.concurrent.TestSupport;

/**
 * 1114. 按序打印
 * <p>
 * https://leetcode-cn.com/problems/print-in-order
 *
 * @author zeno
 */
public class T1114_PrintInOrderV2_Lock {

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

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    // 1 -> first, 2 -> second, 3 -> third
    private volatile int flag = 1;

    public Foo() {
      flag = 1;
    }

    public void first(Runnable printFirst) throws InterruptedException {
      lock.lock();
      try {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();

        flag = 2;
        condition.signalAll();
      } finally {
        lock.unlock();
      }
    }

    public void second(Runnable printSecond) throws InterruptedException {
      lock.lock();
      try {
        while (flag != 2) {
          condition.await();
        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();

        flag = 3;
        condition.signalAll();
      } finally {
        lock.unlock();
      }
    }

    public void third(Runnable printThird) throws InterruptedException {
      lock.lock();
      try {
        while (flag != 3) {
          condition.await();
        }
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();

        condition.signalAll();
      } finally {
        lock.unlock();
      }
    }
  }

}

