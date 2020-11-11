package xyz.flysium.photon.concurrent.medium;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;
import xyz.flysium.photon.concurrent.TestSupport;

/**
 * 1116. 打印零与奇偶数
 * <p>
 * https://leetcode-cn.com/problems/print-zero-even-odd/
 *
 * @author zeno
 */
public class T1116_PrintZeroAndOddV2_Lock {

  public static final int TIMES = 1000;

  public static void main(String[] args) throws InterruptedException {
    long l = TestSupport.testWithMyConsumerOneTime(false, () -> new ZeroEvenOdd(TIMES), (inst) -> {
      inst.zero(System.out::print);
    }, (inst) -> {
      inst.even(System.out::print);
    }, (inst) -> {
      inst.odd(System.out::print);
    });
    System.out.println();
    System.out.println(TestSupport.toMillisString(l));
  }

  private static class ZeroEvenOdd {

    private final int n;
    private final Lock lock = new ReentrantLock();
    private final Condition zero = lock.newCondition();
    private final Condition odd = lock.newCondition();
    private final Condition even = lock.newCondition();
    // 0 -> zero, 1 -> odd, 2 -> even
    private volatile int flag = 0;
    private int num = 0;

    public ZeroEvenOdd(int n) {
      this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
      lock.lock();
      try {
        for (int i = 0; i < n; i++) {
          while (flag != 0) {
            zero.await();
          }
          printNumber.accept(0);
          num++;
          // 奇数
          if ((num & (~num + 1)) == 1) {
            flag = 1;
            odd.signal();
          } else {
            flag = 2;
            even.signal();
          }
        }
      } finally {
        lock.unlock();
      }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
      lock.lock();
      try {
        for (int i = 0; i < (n >> 1); i++) {
          while (flag != 2) {
            even.await();
          }
          printNumber.accept(num);
          flag = 0;
          zero.signal();
        }
      } finally {
        lock.unlock();
      }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
      lock.lock();
      try {
        for (int i = 0; i < ((n + 1) >> 1); i++) {
          while (flag != 1) {
            odd.await();
          }
          printNumber.accept(num);
          flag = 0;
          zero.signal();
        }
      } finally {
        lock.unlock();
      }
    }
  }

}

